package com.example.project2.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.*;

@AllArgsConstructor @Data
public class MerchantStock {
    //id , username , password , email , role , balance.
    @NotEmpty(message = "id is required")
    @Size(min = 3,max = 3)
    @Digits(integer = 3,fraction = 0)
    private Integer id;

    @NotEmpty(message = "productid is required")
    @Size(min = 3,max = 3)
    @Digits(integer = 3,fraction = 0)
    private Integer productid;

    @NotEmpty(message = "password is required")
    @Size(min = 3,max = 3)
    @Digits(integer = 3,fraction = 0)
    private Integer merchantid;

    @NotNull(message = "balance is required")
    @Size(min = 1)
    @Digits(integer = 10,fraction = 0)
    private Integer stock;


}
