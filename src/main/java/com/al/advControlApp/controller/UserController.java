package com.al.advControlApp.controller;

import com.al.advControlApp.auth.JwtTokenUtil;
import com.al.advControlApp.model.User;
import com.al.advControlApp.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private static Logger LOGGER = Logger.getLogger(UserController.class);
    private static final String userPasswordReplacement = "********";

    @Autowired
    UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping("/users")
    public List<User> getAllUsersDependsOnRole(HttpServletRequest req) {
        String userRoleFromToken = jwtTokenUtil.getClimeToken(req, "userRole");
        List<User> users = userService.getAllUsersDependsOnRole(userRoleFromToken);
        users.forEach(user -> user.setPassword(userPasswordReplacement));
        return users;
    }

    @GetMapping("/users/{id}")
    public User getUserByIdDependsOnRole(@PathVariable("id") long id, HttpServletRequest req) {
        String userRoleFromToken = jwtTokenUtil.getClimeToken(req, "userRole");
        User user = userService.getUsersByIdDependsOnRole(id, userRoleFromToken);
        if (user != null) {
            user.setPassword(userPasswordReplacement);
        }
        return user;
    }

    @PostMapping(path = "/users", consumes = "application/json", produces = "application/json")
    public User saveUser(@RequestBody User user, HttpServletRequest req) {
        String userRoleFromToken = jwtTokenUtil.getClimeToken(req, "userRole");
        User savedUser = userService.saveUserDependsOnRole(user, userRoleFromToken);
        if (savedUser != null) {
            savedUser.setPassword(userPasswordReplacement);
        }
        return savedUser;
    }

    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable("id") long id, HttpServletRequest req) {
        String userRoleFromToken = jwtTokenUtil.getClimeToken(req, "userRole");
        userService.deleteUserDependsOnRole(id, userRoleFromToken);
    }

    @PutMapping(path = "/users", consumes = "application/json", produces = "application/json")
    public User updateUserById(@RequestBody User user, HttpServletRequest req) {
        String userRoleFromToken = jwtTokenUtil.getClimeToken(req, "userRole");
        User updatedUser = userService.updateUserDependsOnRole(user, userRoleFromToken);
        if (updatedUser != null) {
            updatedUser.setPassword(userPasswordReplacement);
        }
        return updatedUser;
    }

}
