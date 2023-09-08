package com.thoughtworks.sample.customer.view;

import com.thoughtworks.sample.Application;
import com.thoughtworks.sample.customer.repository.CustomerRepository;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@WithMockUser
public class CustomerControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    public void beforeEach() {
        customerRepository.deleteAll();
        userRepository.deleteAll();
    }

    @AfterEach
    public void afterEach() {
        customerRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void should_create_customer_and_user() throws Exception {

        final String requestJson = "{" +
                "\"name\": \"TestCustomerName\"," +
                "\"phoneNumber\": \"1234567890\"," +
                "\"email\": \"email@example.com\"," +
                "\"user\": {" +
                "    \"username\": \"testUsername\"," +
                "    \"password\": \"Password@123\"" +
                "}" +
                "}";

        mockMvc.perform(post("/customer")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andReturn();

        assertThat(userRepository.findAll().size(), is(1));
        assertThat(customerRepository.findAll().size(), is(1));
    }

    @Test
    public void should_not_create_customer_when_user_already_exists() throws Exception {

        final String requestJson = "{" +
                "\"name\": \"TestCustomer Name\"," +
                "\"phoneNumber\": \"1234567890\"," +
                "\"email\": \"email@example.com\"," +
                "\"user\": {" +
                "    \"username\": \"testUsername\"," +
                "    \"password\": \"Password@123\"" +
                "}" +
                "}";

        mockMvc.perform(post("/customer")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(requestJson))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/customer")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(requestJson))
                .andExpect(status().isBadRequest());

        assertThat(userRepository.findAll().size(), is(1));
        assertThat(customerRepository.findAll().size(), is(1));
    }


}
