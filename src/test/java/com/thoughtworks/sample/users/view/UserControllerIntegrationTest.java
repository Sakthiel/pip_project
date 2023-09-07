package com.thoughtworks.sample.users.view;

import com.thoughtworks.sample.Application;
import com.thoughtworks.sample.users.repository.User;
import com.thoughtworks.sample.users.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Application.class , properties =  "spring.config.name=application-test")
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void before() {
        userRepository.deleteAll();
    }

    @Test
    public void shouldLoginSuccessfully() throws Exception {
        userRepository.save(new User("testUser", "owner123"));

        mockMvc.perform(get("/login")
                        .with(httpBasic("testUser", "owner123")))
                        .andExpect(status().isOk());
    }

    @Test
    public void shouldThrowErrorMessageForInvalidCredentials() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isUnauthorized());
    }

}
