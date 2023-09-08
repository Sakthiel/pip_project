package com.thoughtworks.sample.customer;

import com.thoughtworks.sample.customer.model.CustomerRequest;
import com.thoughtworks.sample.customer.repository.Customer;
import com.thoughtworks.sample.customer.repository.CustomerRepository;
import com.thoughtworks.sample.users.UserPrincipalService;
import com.thoughtworks.sample.users.repository.User;
import com.thoughtworks.sample.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {


    UserRepository userRepository;

    CustomerRepository customerRepository;

    UserPrincipalService userPrincipalService;
    @Autowired
    public CustomerService(UserRepository userRepository, CustomerRepository customerRepository, UserPrincipalService userPrincipalService) {
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.userPrincipalService = userPrincipalService;
    }



    public Customer create(CustomerRequest customerRequest) {
        String username = customerRequest.getUserCredentials().getUsername();
        String password = customerRequest.getUserCredentials().getPassword();
        if (userRepository.findByUsernameIgnoreCase(username).isPresent()) {
            throw new IllegalArgumentException("This username already exists");
        }
        String role = "ROLE_CUSTOMER";

        User user = new User(username , password , role);
        userRepository.save(user);

        Customer customer = new Customer(
                customerRequest.getName(),
                customerRequest.getPhoneNumber(),
                customerRequest.getEmail(),
                user
        );
        return customerRepository.save(customer);
    }

    public Customer getDetailsByUsername(String username) {

        User user = userPrincipalService.findUserByUsername(username);

        Long user_id = user.getId();

        List<Customer> customerList = customerRepository.getDetailsByUserid(user_id);

        return customerList.get(0);
    }
}
