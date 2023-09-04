package com.thoughtworks.sample.products.view;

import com.thoughtworks.sample.Application;
import com.thoughtworks.sample.product.repository.ProductRepository;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@WithMockUser
public class ProductControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    public void beforeEach(){
        productRepository.deleteAll();
    }
@Test
    public void should_save_product_details() throws Exception
    {
        final String requestJson = "{" +
                "\"productName\" : \"Apple\" ," +
                "\"category\" : \"Fruit\" , " +
                "\"unitPrice\" : 200 " +
                "}";
        mockMvc.perform(MockMvcRequestBuilders.post("/products").
                contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestJson).with(httpBasic("Shop_Owner", "Owner")))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json("{" +
                        "\"id\" : 1 ," +
                        "\"productName\" : \"Apple\" ," +
                        "\"category\" : \"Fruit\" , " +
                        "\"unitPrice\" : 200 " +
                        "}"));
    }

}
