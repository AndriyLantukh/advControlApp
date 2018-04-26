package com.al.advControlApp.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class MyCustomEncoder extends BCryptPasswordEncoder{

    public boolean matches(CharSequence rawPassword, String encodedPassword) {
       return rawPassword.equals(encodedPassword);
    }

}
