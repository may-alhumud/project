package com.example.project2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.ArrayList;

@Data

@AllArgsConstructor
public class Cart {
    @NotEmpty(message = "id is required")
    @Digits(integer =  3,fraction = 0)
    private Integer id;
    @NotEmpty(message = "username is required")
    @Size(min = 5)
    private Integer userid;
    private ArrayList<Product> productlist;

    public Cart(){
        this.productlist = new ArrayList<>();
    }

    public Cart(Integer id, Integer userid) {
        this.id = id;
        this.userid = userid;
        productlist =new ArrayList<>();
    }

    public Integer getTotalCart(){
        Integer total  = 0;
        for(int i = 0; i < this.productlist.size(); i++)
            total += this.getProductlist().get(i).getPrice();
        return total;
    }

}
