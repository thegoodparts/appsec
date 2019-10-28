package internal.appsec.validation.injection.sql.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import internal.appsec.validation.injection.sql.model.User;
import internal.appsec.validation.injection.sql.service.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    User getUser(String username) {
        return userService.getUser(username)
                .orElseThrow(() -> new RuntimeException("User " + username + " not found"));
    }

}
