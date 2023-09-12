package com.thoughtworks.sample.sms.view;

import com.thoughtworks.sample.sms.SmsService;
import com.thoughtworks.sample.sms.model.SmsRequest;
import com.thoughtworks.sample.sms.model.VerifyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/sendSms")
public class SmsController {

    SmsService smsService;
    @Autowired
    public SmsController(SmsService smsService) {
        this.smsService = smsService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> sendMessage(@Valid @RequestBody SmsRequest smsRequest) {
        return smsService.sendMessage(smsRequest);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyOtp(@RequestBody VerifyRequest verifyRequest){
        return smsService.verify(verifyRequest);
    }
}
