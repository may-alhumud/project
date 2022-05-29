package com.example.project2.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.*;

@AllArgsConstructor @Data
public class User {
    //id , username , password , email , role , balance.
    @NotEmpty(message = "id is required")
    @Size(min = 3,max = 3)
    @Digits(integer = 3,fraction = 5)
    private Integer id;

    @NotEmpty(message = "username is required")
    @Size(min = 5,max = 5)
    private String username;

    @NotEmpty(message = "password is required")
    @Size(min = 6,max = 6)
    @Digits(integer = 6,fraction = 0)
    private String password;

    @NotEmpty(message = "email is required")
    @Email
    private String email;

    @NotEmpty(message = "role is required")
    @Pattern(regexp = "(Admin|Customer)")
    private String role;

    @NotNull(message = "balance is required")
    @Positive
    @Digits(integer = 10,fraction = 5)
    private Integer balance;


}
