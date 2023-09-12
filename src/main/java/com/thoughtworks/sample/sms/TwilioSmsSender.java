package com.thoughtworks.sample.sms;

import com.thoughtworks.sample.sms.model.SmsRequest;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TwilioSmsSender {

    public ResponseEntity<?> sendSms(SmsRequest smsRequest, String otp){
        String accountSid = "ACd52844fb795e6c1036d90eab580ea3a5" ;
        String authToken  = "f21fba5529c78de59a1db3c6e3eb94cc" ;
        Twilio.init(accountSid, authToken);
        Message message = Message
                .creator(
                        new PhoneNumber("+91" + smsRequest.getPhoneNumber()),
                        new PhoneNumber("+16123269182"),
                        "Your OTP from Daily Needs --- " + otp
                )
                .create();
        return ResponseEntity.ok("Message Sent Successfully");
    }
}
