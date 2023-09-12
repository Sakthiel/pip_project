package com.thoughtworks.sample.sms;

import com.thoughtworks.sample.customer.repository.Customer;
import com.thoughtworks.sample.customer.repository.CustomerRepository;
import com.thoughtworks.sample.sms.model.SmsRequest;
import com.thoughtworks.sample.sms.model.VerifyRequest;
import com.thoughtworks.sample.sms.repository.OtpInformation;
import com.thoughtworks.sample.sms.repository.OtpRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class SmsService {
    TwilioSmsSender smsSender;
    OtpRepository otpRepository;

    CustomerRepository customerRepository;
    @Autowired
    public SmsService(TwilioSmsSender smsSender, OtpRepository otpRepository, CustomerRepository customerRepository) {
        this.smsSender = smsSender;
        this.otpRepository = otpRepository;
        this.customerRepository = customerRepository;
    }

    public ResponseEntity<?> sendMessage(SmsRequest smsRequest){
        String otp = generateOtp();
        String phoneNumber = smsRequest.getPhoneNumber();

        List<Customer> customerList = customerRepository.getCustomerByPhoneNumber(phoneNumber);
        if(customerList.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Given Phone number is not correct");
        }
        try {

            smsSender.sendSms(smsRequest , otp);
        }
        catch(Exception e){
            e.printStackTrace();
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        List <OtpInformation> otpList = otpRepository.getByPhoneNumber(smsRequest.getPhoneNumber());
        if(!otpList.isEmpty()){
            updateOtp(otpList , otp);
            return ResponseEntity.ok("Message Sent Successfully");
        }
        OtpInformation otpInformation = new OtpInformation(smsRequest.getPhoneNumber() , otp);
        otpRepository.save(otpInformation);
        return ResponseEntity.ok("Message Sent Successfully");
    }

    private void updateOtp(List<OtpInformation> otpList, String otp) {
        OtpInformation otpInformation = otpList.get(0);
        otpInformation.setOtp(otp);
        otpRepository.save(otpInformation);
    }

    public String generateOtp(){
        Random random = new Random() ;
        StringBuilder otpBuilder = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int digit = random.nextInt(10); // Generate a random digit (0-9)
            otpBuilder.append(digit);
        }
        return otpBuilder.toString();
    }

    public ResponseEntity<?> verify(VerifyRequest verifyRequest) {
            String otpInRequest = verifyRequest.getOtp();

           List<OtpInformation> otpList = otpRepository.getByOtp(otpInRequest);
           if(otpList.isEmpty()){
               return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Given Otp is wrong");
           }
          OtpInformation otp = otpList.get(0) ;
           if(!otp.getOtp().equals(otpInRequest)){
               return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Given Otp is wrong");
           }
           otpRepository.delete(otp);
           return ResponseEntity.status(HttpStatus.OK).body("Verified Successfully");
    }
}
