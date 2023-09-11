package com.thoughtworks.sample.products.view;

import com.thoughtworks.sample.Application;
import com.thoughtworks.sample.product.repository.Product;
import com.thoughtworks.sample.product.repository.ProductRepository;
import com.thoughtworks.sample.users.repository.User;
import com.thoughtworks.sample.users.repository.UserRepository;
import org.junit.jupiter.api.AfterAll;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Application.class , properties =  "spring.config.name=application-test")
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@WithMockUser
public class ProductControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void beforeEach(){
        productRepository.deleteAll();
        userRepository.deleteAll();
        userRepository.save(new User("Shop_Owner", "Owner" , "ROLE_ADMIN"));
        userRepository.save(new User("sakthi123" , "sakthi", "ROLE_CUSTOMER"));
    }
    @AfterEach
    public void afterEach(){
        productRepository.deleteAll();
        userRepository.deleteAll();
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
                .andExpect(status().isCreated());
    }

    @Test
        public void should_return_all_products() throws Exception{
        productRepository.save(new Product("Apple" , "Fruit" , BigDecimal.valueOf(100)));

        mockMvc.perform(get("/products").with(httpBasic("Shop_Owner", "Owner")))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        "[" +
                                "    {" +
                                "        \"id\": 3," +
                                "        \"productName\": \"Apple\"," +
                                "        \"category\": \"Fruit\"," +
                                "        \"unitPrice\": 100.00" +
                                "    }" +
                                "]"));

    }

    @Test
     public void should_delete_the_product_by_id() throws Exception{
        productRepository.save(new Product("Apple" , "Fruit" , BigDecimal.valueOf(100)));

        mockMvc.perform(delete("/products/3").with(httpBasic("Shop_Owner", "Owner")))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Deleted product with id 3"));
    }

    @Test
    public void should_update_the_product_by_id() throws Exception{
        productRepository.save(new Product("Apple" , "Fruit" , BigDecimal.valueOf(100)));

        final String requestJson = "{" +
                "\"productName\" : \"Banana\" ," +
                "\"category\" : \"Fruit\" , " +
                "\"unitPrice\" : 200 " +
                "}";

        mockMvc.perform(put("/products/1")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(requestJson).with(httpBasic("Shop_Owner", "Owner")))
                            .andExpect(status().isOk())
                            .andExpect(MockMvcResultMatchers.content().json(

                                            "    {\n" +
                                            "        \"id\": 1,\n" +
                                            "        \"productName\": \"Banana\",\n" +
                                            "        \"category\": \"Fruit\",\n" +
                                            "        \"unitPrice\": 200.00\n" +
                                            "    }\n"
                                            ));

    }

    @Test
    public void should_not_save_product_details_when_customer_logged_in() throws Exception
    {

        final String requestJson = "{" +
                "\"productName\" : \"Apple\" ," +
                "\"category\" : \"Fruit\" , " +
                "\"unitPrice\" : 200 " +
                "}";
        mockMvc.perform(MockMvcRequestBuilders.post("/products").
                        contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestJson).with(httpBasic("sakthi123", "sakthi")))
                .andExpect(status().isForbidden());
    }

    @Test
    public void should_not_delete_the_product_by_id_if_customer_logged_in() throws Exception{
        productRepository.save(new Product("Apple" , "Fruit" , BigDecimal.valueOf(100)));

        mockMvc.perform(delete("/products/1").with(httpBasic("sakthi123", "sakthi")))
                .andExpect(status().isForbidden());
    }

    @Test
    public void should_not_update_the_product_by_id_when_customer_logged_in() throws Exception{
        productRepository.save(new Product("Apple" , "Fruit" , BigDecimal.valueOf(100)));

        final String requestJson = "{" +
                "\"productName\" : \"Banana\" ," +
                "\"category\" : \"Fruit\" , " +
                "\"unitPrice\" : 200 " +
                "}";

        mockMvc.perform(put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestJson).with(httpBasic("sakthi123", "sakthi")))
                .andExpect(status().isForbidden());


    }

}
