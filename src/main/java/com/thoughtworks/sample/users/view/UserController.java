package com.thoughtworks.sample.users.view;
import com.thoughtworks.sample.users.UserPrincipalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.thoughtworks.sample.users.repository.User;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private UserPrincipalService userPrincipalService;

    @GetMapping("/login")
    Map<String, Object> login(Principal principal) {
        String username = principal.getName();
        User user = userPrincipalService.findUserByUsername(username);

        Map<String, Object> userDetails = new HashMap<>();

        userDetails.put("username", username);
        return userDetails;
    }

}
