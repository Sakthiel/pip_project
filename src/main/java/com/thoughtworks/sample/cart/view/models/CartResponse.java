package com.thoughtworks.sample.cart.view.models;

import com.thoughtworks.sample.product.repository.Product;

import javax.validation.constraints.DecimalMin;

public class CartResponse {

    private Product product;
    @DecimalMin(value = "1" , inclusive = true , message = "quantity should not be less than {value}")
    private Integer quantity;

    private Integer totalCartItems;

    public CartResponse(Product product, Integer quantity, Integer totalCartItems) {
        this.product = product;
        this.quantity = quantity;
        this.totalCartItems = totalCartItems;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getTotalCartItems() {
        return totalCartItems;
    }

    public void setTotalCartItems(Integer totalCartItems) {
        this.totalCartItems = totalCartItems;
    }
}
