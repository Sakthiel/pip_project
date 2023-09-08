package com.thoughtworks.sample.customer.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class UserCredentialTest {
    private Validator validator;

    @BeforeEach
    public void beforeEach() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void should_allow_user_to_be_created_with_valid_credentials() {

        final UserCredentials user = new UserCredentials("testUser", "Password@123");

        final Set<ConstraintViolation<UserCredentials>> violations = validator.validate(user);

        assertThat(violations.size(),is(0) );
    }

    @Test
    public void should_not_allow_user_name_to_be_blank() {

        final UserCredentials user = new UserCredentials("", "testPassword@123");

        final Set<ConstraintViolation<UserCredentials>> violations = validator.validate(user);

        assertThat(violations.iterator().next().getMessage(), is("Please enter valid username"));
    }

    @Test
    public void should_not_allow_blank_password() {
        final UserCredentials user = new UserCredentials("testUsername", "");

        final Set<ConstraintViolation<UserCredentials>> violations = validator.validate(user);

        assertThat(violations.iterator().next().getMessage(), is("Password should have at least 1 lowercase, 1 uppercase, 1 digit and 1 special character. It should be of minimum of 8 and maximum of 64 characters"));
    }
}
