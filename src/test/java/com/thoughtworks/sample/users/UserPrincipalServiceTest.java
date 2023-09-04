package com.thoughtworks.sample.users;

import com.thoughtworks.sample.users.repository.User;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.thoughtworks.sample.users.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class UserPrincipalServiceTest {
    private UserRepository userRepository;
    private UserPrincipalService userPrincipalService;

    @BeforeEach
    public void setUp() {
        userRepository = mock(UserRepository.class);
        userPrincipalService = new UserPrincipalService(userRepository);
    }

    @Test
    public void should_load_user_by_username() {
        User mockUser = new User();
        mockUser.setUsername("testUser");

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(mockUser));

        UserDetails userDetails = userPrincipalService.loadUserByUsername("testUser");

        assertNotNull(userDetails);
        assertEquals(mockUser.getUsername(), userDetails.getUsername());
    }

    @Test
    public void should_throw_username_not_found_exception() {
        when(userRepository.findByUsername("invalidUser")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            userPrincipalService.loadUserByUsername("invalidUser");
        });
    }
}
