package com.thoughtworks.sample.customer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerDetailResponse {

    @JsonProperty
    private final String name;



    @JsonProperty
    private final String username;

    @JsonProperty
    private final String email;

    @JsonProperty
    private final String phoneNumber;
    public CustomerDetailResponse(String name, String username, String email, String phoneNumber) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }


    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }


}
