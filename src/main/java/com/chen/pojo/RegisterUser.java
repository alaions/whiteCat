package com.chen.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUser {

    private String username;
    private String password;
    private String againPassword;
    private String email;
    private String code;

}
