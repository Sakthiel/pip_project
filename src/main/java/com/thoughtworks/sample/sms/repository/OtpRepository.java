package com.thoughtworks.sample.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OtpRepository extends JpaRepository<OtpInformation , Long> {
    @Query(value = "select * from otpinfo where otp = ?1" , nativeQuery = true)
    List<OtpInformation> getByOtp(String otpInRequest);
}
