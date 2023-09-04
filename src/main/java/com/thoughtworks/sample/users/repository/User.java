package com.thoughtworks.sample.users.repository;

import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Table(name = "usertable")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column(name = "user_name" , nullable = false)
    @Pattern(regexp = ("[a-zA-Z$_][a-zA-Z0-9$_]*"), message = "Please enter valid username")
    private String username;
    @NotNull
    @Pattern(regexp = ("^.*[a-zA-Z0-9]+.*$"), message = "Please enter valid password")
    private String password;

    public User() {
    }

    public User(@NotNull String username, @NotNull String password) {
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NotNull String username) {
        this.username = username;
    }

    @NotNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NotNull String password) {
        this.password = password;
    }
}
