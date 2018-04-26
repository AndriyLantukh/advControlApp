package com.al.advControlApp.controller;

import com.al.advControlApp.auth.JwtTokenUtil;
import com.al.advControlApp.model.AuthToken;
import com.al.advControlApp.model.LoginUser;
import com.al.advControlApp.model.User;
import com.al.advControlApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @PostMapping(value = "/token/generate-token")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> register(@RequestBody LoginUser loginUser) throws AuthenticationException {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getEmail(),
                        loginUser.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final List<User> usersList =  userRepository.findByEmail(loginUser.getEmail());
        if(usersList.size() != 1) {
            return ResponseEntity.noContent().build();
        }

        final String token = jwtTokenUtil.generateToken(usersList.get(0));
        return ResponseEntity.ok(new AuthToken(token));
    }

}
