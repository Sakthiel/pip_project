package com.thoughtworks.sample.customer.view;

import com.thoughtworks.sample.customer.CustomerService;
import com.thoughtworks.sample.customer.model.CustomerDetailResponse;
import com.thoughtworks.sample.customer.model.CustomerRequest;
import com.thoughtworks.sample.customer.repository.Customer;
import com.thoughtworks.sample.handlers.model.ErrorResponse;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/customer")
public class CustomerController {


    private CustomerService customerService;
@Autowired

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created a customer successfully"),
            @ApiResponse(code = 400, message = "Server cannot process request due to client error", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Something failed in the server", response = ErrorResponse.class)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Customer createCustomer(@Valid @RequestBody CustomerRequest customerRequest) throws IllegalArgumentException{
        return customerService.create(customerRequest);
    }


    @GetMapping("/profile/{username}")
    @ResponseStatus(value = HttpStatus.OK)
    public CustomerDetailResponse getCustomerDetails(@PathVariable("username") String username){
        Customer customer = customerService.getDetailsByUsername(username);

        return new CustomerDetailResponse(customer.getName(),customer.getUser().getUsername(),customer.getEmail(), customer.getPhoneNumber());
    }
}
