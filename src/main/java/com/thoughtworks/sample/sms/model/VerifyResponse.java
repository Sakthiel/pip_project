package com.thoughtworks.sample.sms.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VerifyResponse {
    @JsonProperty
    private String token;
    @JsonProperty
    private String role;
    public VerifyResponse() {
    }



    public VerifyResponse(String token, String role) {
        this.token = token;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
