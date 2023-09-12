package com.thoughtworks.sample.sms;

import com.thoughtworks.sample.customer.CustomerServiceTest;
import com.thoughtworks.sample.customer.repository.Customer;
import com.thoughtworks.sample.customer.repository.CustomerRepository;
import com.thoughtworks.sample.sms.model.SmsRequest;
import com.thoughtworks.sample.sms.model.VerifyRequest;
import com.thoughtworks.sample.sms.repository.OtpInformation;
import com.thoughtworks.sample.sms.repository.OtpRepository;
import com.thoughtworks.sample.users.repository.User;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;


public class SmsServiceTest {

    TwilioSmsSender smsSender;

    OtpRepository otpRepository;


    CustomerRepository customerRepository;

    SmsService smsService ;

    SmsRequest smsRequest;
    User user;
    Customer customer;

    OtpInformation otpInformation;

    @BeforeEach
    public void beforeEach(){
        smsSender = mock(TwilioSmsSender.class);

        otpRepository = mock(OtpRepository.class);

        customerRepository = mock(CustomerRepository.class);

        smsService = new SmsService(smsSender,otpRepository,customerRepository);

        smsRequest = new SmsRequest("1234567890");

        user = new User("testUser" , "User123");

        customer = new Customer("testUser" , "1234567890" , "testUser@gmail.com" , user );



    }
    @Test
    public void should_be_able_to_send_sms(){
        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer);

        when(customerRepository.getCustomerByPhoneNumber(smsRequest.getPhoneNumber())).thenReturn(customerList);
        ResponseEntity<?> response = smsService.sendMessage(smsRequest);

        assertThat(response.getBody() , is(equalTo("Message Sent Successfully")));

    }

    @Test
    public void should_not_be_able_to_send_sms_if_phoneNumber_is_not_found(){
        List<Customer> customerList = new ArrayList<>();

        when(customerRepository.getCustomerByPhoneNumber(smsRequest.getPhoneNumber())).thenReturn(customerList);
        ResponseEntity<?> response = smsService.sendMessage(smsRequest);

        assertThat(response.getBody() , is(equalTo("Given Phone number is not correct")));

    }

    @Test
    public void should_be_able_to_verify_otp(){
        String otp = "123456";
        VerifyRequest verifyRequest = new VerifyRequest("123456");
        otpInformation = new OtpInformation("1234567890" , "123456");
        List<OtpInformation> otpList = new ArrayList<>();
        otpList.add(otpInformation);
        when(otpRepository.getByOtp(otp)).thenReturn(otpList);

        ResponseEntity<?> response = smsService.verify(verifyRequest);

        verify(otpRepository).delete(otpInformation);
        assertThat(response.getBody() , is(equalTo("Verified Successfully")));

    }


}
