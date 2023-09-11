package com.thoughtworks.sample.handlers.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class SuccessResponse {
    @JsonProperty
    @ApiModelProperty(name = "message", value = "success", required = true, position = 1)
    private final String message;
    @JsonProperty
    @ApiModelProperty(name = "details", value = "details of the successful response", required = true, position = 2)
    private final List<String> details;

    public SuccessResponse(String message, List<String> details) {
        this.message = message;
        this.details = details;
    }
}
