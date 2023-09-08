package com.thoughtworks.sample.customer.repository;

import com.thoughtworks.sample.users.repository.User;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 1, max = 200, message = "Customer name must have minimum 1 and maximum of 200 characters")
    @Column(nullable = false)
    private String name;
    @Column(name = "phone_number" , nullable = false)
    @Size(min = 10, max = 10, message = "Phone number must have exactly 10 digits")
    private String phoneNumber;
    @Column(nullable = false)
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid Email Id")
    private String email;

    @Valid
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Customer() {
    }

    public Customer(String name, String phoneNumber, String email, User user) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
