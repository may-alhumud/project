package com.example.project2.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.*;

@AllArgsConstructor @Data
public class Merchant {
    //id , username , password , email , role , balance.
    @NotEmpty(message = "id is required")
    @Size(min = 3,max = 3)
    @Digits(integer = 3,fraction = 0)
    private Integer id;

    @NotEmpty(message = "name is required")
    @Size(min = 5,max = 3)
    private String name;



}
