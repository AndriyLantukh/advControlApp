package com.al.advControlApp.model;

import lombok.*;

@Data
@NoArgsConstructor
@ToString
@Getter
@Setter
public class LoginUser {

    private String email;
    private String password;

}
