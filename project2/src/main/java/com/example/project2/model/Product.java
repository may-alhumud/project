package com.example.project2.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.ArrayList;

 @Data
public class Product {
    @NotEmpty(message = "id is required")
    @Size(min = 3,max = 3)
    @Digits(integer = 3,fraction = 0)
    private Integer id;
    @NotEmpty(message = "username is required")
    @Size(min = 3,max = 3)
    private String name;
    @NotEmpty(message = "password is required")
    @Size(min = 3,max = 3)
    @Digits(integer = 3,fraction = 0)
    private String categoryid ;
    @NotNull(message = "price is required")
    @Positive
    @Digits(integer = 10,fraction = 5)
    private Integer price;
    private ArrayList<Comment> commentlist;

    public Product(){

    }
    public Product(Integer id, String name, String categoryid, Integer price) {
        this.id = id;
        this.name = name;
        this.categoryid = categoryid;
        this.price = price;
        commentlist =new ArrayList<>();
    }

    public boolean isRatedFive(){
        //all comments have to be rated 5 otherwise whole rating is not 5
        for(int i = 0; i < this.getCommentlist().size(); i++)
        {
            if(this.getCommentlist().get(i).getRate() < 5)
                return false;
        }
        return true;
    }
}
