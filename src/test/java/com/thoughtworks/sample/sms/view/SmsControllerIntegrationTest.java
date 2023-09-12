package com.thoughtworks.sample.sms.view;

import com.thoughtworks.sample.Application;
import com.thoughtworks.sample.customer.repository.Customer;
import com.thoughtworks.sample.customer.repository.CustomerRepository;
import com.thoughtworks.sample.sms.SmsServiceTest;
import com.thoughtworks.sample.sms.repository.OtpInformation;
import com.thoughtworks.sample.sms.repository.OtpRepository;
import com.thoughtworks.sample.users.repository.User;
import com.thoughtworks.sample.users.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Application.class , properties =  "spring.config.name=application")
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@WithMockUser
public class SmsControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    OtpRepository otpRepository;

    User user;

    Customer customer;

    @BeforeEach
    public void beforeEach() {
        customerRepository.deleteAll();
        userRepository.deleteAll();
        otpRepository.deleteAll();
    }

    @AfterEach
    public void afterEach() {
        customerRepository.deleteAll();
        userRepository.deleteAll();
        otpRepository.deleteAll();
    }


    @Test
    public void should_send_sms_to_user() throws Exception {
        user = new User("testUser" , "User123" , "ROLE_CUSTOMER");
        customer = new Customer("testUser" , "9629579646" , "testUser@gmail.com" , user );
        userRepository.save(user);
        customerRepository.save(customer);

        final String requestJson = "{\n" +
                "    \"phoneNumber\" : \"9629579646\"\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/sendSms").
                        contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestJson))
                .andExpect(status().isOk());
    }
    @Test
    public void should_not_send_sms_to_user_if_phoneNumber_is_incorrect() throws Exception {
        user = new User("testUser" , "User123" , "ROLE_CUSTOMER");
        customer = new Customer("testUser" , "9629579646" , "testUser@gmail.com" , user );
        userRepository.save(user);
        customerRepository.save(customer);

        final String requestJson = "{\n" +
                "    \"phoneNumber\" : \"9629579233\"\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/sendSms").
                        contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_verify_otp() throws Exception {
        OtpInformation otpInformation = new OtpInformation("1234567890" , "123456");
        otpRepository.save(otpInformation);

        final String requestJson = "{\n" +
                "    \"otp\" : \"123456\"\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/sendSms/verify").
                        contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestJson))
                .andExpect(status().isOk());

    }

    @Test
    public void should_return_badRequest_when_otp_is_wrong() throws Exception {
        OtpInformation otpInformation = new OtpInformation("1234567890" , "123456");
        otpRepository.save(otpInformation);

        final String requestJson = "{\n" +
                "    \"otp\" : \"123423\"\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/sendSms/verify").
                        contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestJson))
                .andExpect(status().isBadRequest());

    }

}
