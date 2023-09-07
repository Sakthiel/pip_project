package com.thoughtworks.sample.cart.repository;

import com.thoughtworks.sample.product.repository.Product;
import com.thoughtworks.sample.users.repository.User;

import javax.persistence.*;

@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Integer quantity;

    public Cart() {
    }

    public Cart(Product product, User user, Integer quantity) {
        this.product = product;
        this.user = user;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
