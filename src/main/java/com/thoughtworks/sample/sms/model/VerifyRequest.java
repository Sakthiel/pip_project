package com.thoughtworks.sample.sms.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class VerifyRequest {
    @JsonProperty
    @Pattern(regexp = "(^$|[0-9]{6})", message = "OTP must have exactly 10 digits")
    @NotBlank(message = "OTP must be provided")
    private String otp;

    public VerifyRequest() {
    }

    public VerifyRequest(String otp) {
        this.otp = otp;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
