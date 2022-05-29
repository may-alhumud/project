package com.example.project2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PurchaseHistory {

    @NotEmpty(message = "id is required")
    @Size(min = 3,max = 3)
    @Digits(integer = 3,fraction = 0)
    private Integer id;

    @NotEmpty(message = "userid is required")
    @Size(min =3,max = 3)
    @Digits(integer = 3,fraction = 0)
    private Integer userid;

    @NotEmpty(message = "productid is required")
    @Size(min = 3,max = 3)
    @Digits(integer = 3,fraction = 0)
    private Integer productid;

    @NotNull(message = "price is required")
    @Positive
    @Digits(integer = 10,fraction = 5)
    private Integer price;


}
