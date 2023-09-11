package com.thoughtworks.sample.cart.utility;

import com.thoughtworks.sample.customer.repository.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class EmailService {
    private final JavaMailSender emailSender;
    @Autowired
    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendMailWithBillPdf(Customer customer){
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message , true , "UTF-8");
            helper.setPriority(1);
            helper.setSubject("Bill Information");
            helper.setFrom("spriyan2000@gmail.com");
            helper.setTo(customer.getEmail());
            helper.setText("Your Invoice from Daily Needs");
            //Attachment
            FileSystemResource pdf = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/file1.pdf"));
            helper.addAttachment(pdf.getFilename() , pdf);
            emailSender.send(message);

        }
        catch(Exception exception){
            System.out.println(exception.getMessage());
            throw new RuntimeException(exception.getMessage());

        }
    }
}
