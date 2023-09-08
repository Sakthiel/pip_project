package com.thoughtworks.sample.customer.repository;

import com.thoughtworks.sample.users.repository.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

public class CustomerTest {

    private Validator validator;
    private final User user = mock(User.class);

    @BeforeEach
    public void beforeEach() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void should_not_allow_customer_name_to_be_blank() {

        final Customer customer = new Customer("", "9099234568", "test@gmail.com",user);

        final Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        assertThat(violations.iterator().next().getMessage(), is("Customer name must have minimum 1 and maximum of 200 characters"));
    }

    @Test
    public void should_allow_phone_number_with_only_10_digits() {
        final Customer customer = new Customer("Customer 1", "99932", "test@gmail.com",user);

        final Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        assertThat(violations.iterator().next().getMessage(), is("Phone number must have exactly 10 digits"));
    }

    @Test
    public void should_not_allow_blank_phone_number() {
        final Customer customer = new Customer("Customer 1", "","test@gmail.com",user);

        final Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        assertThat(violations.iterator().next().getMessage(), is("Phone number must have exactly 10 digits"));
    }

    @Test
    public void should_not_allow_invalid_email_id() {
        final Customer customer = new Customer("Customer 1", "9087656522","testgmail.com",user);

        final Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        assertThat(violations.iterator().next().getMessage(), is("Invalid Email Id"));
    }
}
