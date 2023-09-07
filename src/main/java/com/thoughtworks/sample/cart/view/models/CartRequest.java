package com.thoughtworks.sample.cart.view.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

public class CartRequest {
    @JsonProperty
    @ApiModelProperty( required = true)
    private Integer productId;
    @JsonProperty
    @ApiModelProperty( required = true)
    private String userName;
    @JsonProperty
    @ApiModelProperty( required = true)
    private Integer quantity;

    public CartRequest() {
    }

    public CartRequest(Integer productId, String userName, Integer quantity) {
        this.productId = productId;
        this.userName = userName;
        this.quantity = quantity;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
