package com.thoughtworks.sample.customer;

import com.thoughtworks.sample.customer.model.CustomerRequest;
import com.thoughtworks.sample.customer.model.UserCredentials;
import com.thoughtworks.sample.customer.repository.Customer;
import com.thoughtworks.sample.customer.repository.CustomerRepository;
import com.thoughtworks.sample.users.UserPrincipalService;
import com.thoughtworks.sample.users.repository.User;
import com.thoughtworks.sample.users.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserPrincipalService userPrincipalService;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void should_create_customer_when_valid_details_are_sent() {
        UserCredentials userCredentials = new UserCredentials("testUser", "testPassword");
        CustomerRequest customerRequest = new CustomerRequest("Sakthi" , "1234567890" , "sakthi@gmail.com" , userCredentials);
        User user = new User("testUser", "testPassword");

        Customer expectedCustomer = new Customer("Sakthi", "1234567890", "sakthi@gmail.com", user);

        when(customerRepository.save(any(Customer.class))).thenReturn(expectedCustomer);


        customerService.create(customerRequest);

        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    public void should_get_customer_details_by_username(){
        User user = new User("testUser", "testPassword");
        user.setId(2L);
        Customer testCustomer = new Customer("Sakthi", "9933221100", "sakthi@gmail.com", user);
        when(userPrincipalService.findUserByUsername(user.getUsername())).thenReturn(user);

        when(customerRepository.getDetailsByUserid(user.getId())).thenReturn(List.of(testCustomer));

        Customer customer = customerService.getDetailsByUsername(user.getUsername());

        assertEquals(testCustomer,customer);
    }


}
