package com.thoughtworks.sample.sms.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class SmsRequest {


    @JsonProperty
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Phone number must have exactly 10 digits")
    @NotBlank(message = "Phone number must be provided")
    private String phoneNumber;

    public SmsRequest(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public SmsRequest() {
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
