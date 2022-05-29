package com.example.project2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

@AllArgsConstructor @Data
public class Comment {

    @NotEmpty(message = "id is required")
    @Size(min = 3,max = 3)
    @Digits(integer = 3,fraction = 0)
    private Integer id;

    @NotEmpty(message = "userid is required")
    @Size(min = 5,max = 5)
    @Digits(integer = 5,fraction = 0)
    private String userid;

    @NotEmpty(message = "message is required")
    @Size(min = 6,max = 6)
    private String message;

    @NotEmpty(message = "rate is required")
    @Range(min = 1,max = 5)
    @Digits(integer = 1,fraction = 0)
    private Integer rate;
}
