package com.thoughtworks.sample.sms.repository;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "otpinfo")
public class OtpInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone_number" , nullable = false)
    @Size(min = 10, max = 10, message = "Phone number must have exactly 10 digits")
    private String phoneNumber;
    @Column(name = "otp" , nullable = false)
    private String otp;

    public OtpInformation() {
    }

    public OtpInformation(String phoneNumber, String otp) {
        this.phoneNumber = phoneNumber;
        this.otp = otp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
