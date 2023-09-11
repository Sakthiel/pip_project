package com.thoughtworks.sample.cart.view;

import com.thoughtworks.sample.Application;
import com.thoughtworks.sample.cart.CartService;
import com.thoughtworks.sample.cart.repository.CartRepository;
import com.thoughtworks.sample.cart.utility.EmailService;
import com.thoughtworks.sample.customer.repository.Customer;
import com.thoughtworks.sample.customer.repository.CustomerRepository;
import com.thoughtworks.sample.product.repository.Product;
import com.thoughtworks.sample.product.repository.ProductRepository;
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
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Application.class ,properties =  "spring.config.name=application")
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@WithMockUser
public class CartControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CartService cartService;
    @Autowired
    private EmailService emailService;

    @BeforeEach
    public void beforeEach(){
        productRepository.deleteAll();
        userRepository.deleteAll();
        cartRepository.deleteAll();
        customerRepository.deleteAll();
    }
    @AfterEach
    public void afterEach(){
        productRepository.deleteAll();
        userRepository.deleteAll();
        cartRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Test
    public void should_save_cart_details() throws Exception
    {

        Product product = new Product("Apple" , "Fruit" , BigDecimal.valueOf(100));

        User user = new User("Shop_Owner" , "Owner" , "ROLE_ADMIN");

        productRepository.save(product);

        userRepository.save(user);

        final String requestJson = "{ "+
            "\"productId\" : 1 ," +
            "\"userName\" : \"Shop_Owner\" ," +
            "\"quantity\" : 1 " +
    "}" ;
        mockMvc.perform(MockMvcRequestBuilders.put("/carts").
                        contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestJson).with(httpBasic("Shop_Owner", "Owner")))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\n" +
                        "    \"id\": 1,\n" +
                        "    \"product\": {\n" +
                        "        \"id\": 1,\n" +
                        "        \"productName\": \"Apple\",\n" +
                        "        \"category\": \"Fruit\",\n" +
                        "        \"unitPrice\": 100.00\n" +
                        "    },\n" +
                        "    \"user\": {\n" +
                        "        \"id\": 2,\n" +
                        "        \"username\": \"Shop_Owner\",\n" +
                        "        \"password\": \"Owner\"\n" +
                        "    },\n" +
                        "    \"quantity\": 1\n" +
                        "}"));
    }

    @Test
    public void should_update_cart_details() throws Exception{

        Product product = new Product("Apple" , "Fruit" , BigDecimal.valueOf(100));

        User user = new User("Shop_Owner" , "Owner", "ROLE_ADMIN");

        productRepository.save(product);
        userRepository.save(user);

        cartService.addToCart("Shop_Owner" , 1 , 2);
        final String requestJson = "{ "+
                "\"productId\" : 1 ," +
                "\"userName\" : \"Shop_Owner\" ," +
                "\"quantity\" : 1 " +
                "}" ;

        mockMvc.perform(MockMvcRequestBuilders.put("/carts").
                        contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestJson).with(httpBasic("Shop_Owner", "Owner")))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\n" +
                        "    \"id\": 1,\n" +
                        "    \"product\": {\n" +
                        "        \"id\": 1,\n" +
                        "        \"productName\": \"Apple\",\n" +
                        "        \"category\": \"Fruit\",\n" +
                        "        \"unitPrice\": 100.00\n" +
                        "    },\n" +
                        "    \"user\": {\n" +
                        "        \"id\": 2,\n" +
                        "        \"username\": \"Shop_Owner\",\n" +
                        "        \"password\": \"Owner\"\n" +
                        "    },\n" +
                        "    \"quantity\": 3\n" +
                        "}"));

    }

    @Test
    public void should_return_all_cart_items() throws Exception {
        Product product = new Product("Apple" , "Fruit" , BigDecimal.valueOf(100));

        User user = new User("Shop_Owner" , "Owner" , "ROLE_ADMIN");

        productRepository.save(product);
        userRepository.save(user);

        cartService.addToCart("Shop_Owner" , 1 , 2);

        mockMvc.perform(MockMvcRequestBuilders.get("/carts")
                        .with(httpBasic("Shop_Owner", "Owner")))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("["+"{\n" +
                        "    \"id\": 1,\n" +
                        "    \"product\": {\n" +
                        "        \"id\": 1,\n" +
                        "        \"productName\": \"Apple\",\n" +
                        "        \"category\": \"Fruit\",\n" +
                        "        \"unitPrice\": 100.00\n" +
                        "    },\n" +
                        "    \"user\": {\n" +
                        "        \"id\": 2,\n" +
                        "        \"username\": \"Shop_Owner\",\n" +
                        "        \"password\": \"Owner\"\n" +
                        "    },\n" +
                        "    \"quantity\": 2\n" +
                        "}" + "]"));
    }

    @Test
    public void should_delete_cart_item_by_id() throws Exception {
        Product product = new Product("Apple" , "Fruit" , BigDecimal.valueOf(100));
        User user = new User("Shop_Owner" , "Owner" , "ROLE_ADMIN");
        productRepository.save(product);
        userRepository.save(user);
        cartService.addToCart("Shop_Owner" , 1 , 2);

        mockMvc.perform(delete("/carts/1").with(httpBasic("Shop_Owner", "Owner")))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Deleted cartItem with id 1" ));

    }

    @Test
    public void should_send_mail_with_billPdf_to_user() throws Exception{
        Product product = new Product("Apple" , "Fruit" , BigDecimal.valueOf(100));
        User user = new User("Shop_Owner" , "Owner" , "ROLE_ADMIN");
        Customer customer = new Customer("shopOwner"  , "9629579646" , "spriyan2000@gmail.com" , user);
        productRepository.save(product);
        userRepository.save(user);
        customerRepository.save(customer);

        final String requestJson = "[\n" +
                "    {\n" +
                "        \"id\": 4,\n" +
                "        \"product\": {\n" +
                "            \"id\": 2,\n" +
                "            \"productName\": \"Tomato\",\n" +
                "            \"category\": \"Vegetable\",\n" +
                "            \"unitPrice\": 200.00\n" +
                "        },\n" +
                "        \"user\": {\n" +
                "            \"id\": 2,\n" +
                "            \"username\": \"Shop_Owner\",\n" +
                "            \"password\": \"Owner\",\n" +
                "            \"role\": \"ROLE_ADMIN\"\n" +
                "        },\n" +
                "        \"quantity\": 8\n" +
                "    }\n" +
                "]";
        mockMvc.perform(MockMvcRequestBuilders.post("/carts/sendMail").
                        contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestJson).with(httpBasic("Shop_Owner", "Owner")))
                .andExpect(status().isOk());
    }
}
