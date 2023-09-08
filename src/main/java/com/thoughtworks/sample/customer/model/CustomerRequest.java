package com.thoughtworks.sample.customer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class CustomerRequest {
    @JsonProperty
    @NotBlank(message = "Customer name must be provided")
    @ApiModelProperty(name = "customer name", value = "Name of customer", required = true, example = "Customer name", position = 1)
    private String name;

    @JsonProperty
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Phone number must have exactly 10 digits")
    @NotBlank(message = "Phone number must be provided")
    @ApiModelProperty(name = "phone number", value = "Phone number of the customer", required = true, example = "9933221100", position = 2)
    private String phoneNumber;

    @JsonProperty
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid Email Id")
    @ApiModelProperty(name = "email id", value = "Email Id of the customer", required = true, example = "exampleUser@gmail.com", position = 3)
    private String email;

    @JsonProperty("user")
    @ApiModelProperty(name = "user credentials", value = "Credentials of the customer", required = true, position = 4)
    private UserCredentials userCredentials;

    public CustomerRequest(String name, String phoneNumber, String email, UserCredentials userCredentials) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.userCredentials = userCredentials;
    }

    public CustomerRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserCredentials getUserCredentials() {
        return userCredentials;
    }

    public void setUserCredentials(UserCredentials userCredentials) {
        this.userCredentials = userCredentials;
    }


}
