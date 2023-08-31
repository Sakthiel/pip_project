package com.thoughtworks.sample.version.view;

import com.thoughtworks.sample.version.repository.Version;
import com.thoughtworks.sample.version.repository.VersionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import com.thoughtworks.sample.Application;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@WithMockUser
public class VersionControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private VersionRepository versionRepository;
@Test
    public void should_return_version() throws Exception {
        versionRepository.deleteAll();
        versionRepository.save(new Version(1,"v2"));

        mockMvc.perform(get("/version"))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "{'CurrentVersion': 'v2'" + "}"));
        versionRepository.deleteAll();
    }
}
