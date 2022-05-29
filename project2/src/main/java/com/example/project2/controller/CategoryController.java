package com.example.project2.controller;

import com.example.project2.model.Api;
import com.example.project2.model.Category;
import com.example.project2.model.User;
import com.example.project2.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequestMapping("api/v1/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController( CategoryService categoryService) {
        this.categoryService = categoryService;

    }

    @GetMapping
    public ResponseEntity<ArrayList<Category>> getCategory(){
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getCategory());

    }

    @PostMapping
    public ResponseEntity<Api> addCategory(@RequestBody @Valid Category category, Errors error){
        if (error.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api(error.getFieldError().getDefaultMessage(),400));
        }
        boolean isAccepted= categoryService.addCategory(category);
        if (!isAccepted) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api("Sorry but your request not Accepted", 400));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new Api(" Accepted request",200));

    }

    @PutMapping("/{index}")
    public ResponseEntity<Api> updateCategory(@PathVariable Integer index,@RequestBody @Valid Category category, Errors error){
        if (error.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api(error.getFieldError().getDefaultMessage(),400));
        }
        boolean isUserUpdate= categoryService.updateCategory(index,category);
        if (!isUserUpdate) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api("Sorry but your request not Update", 400));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new Api(" Update request",200));

    }

    @DeleteMapping("/delete/{index}")
    public ResponseEntity<Api> deleteCategory(@PathVariable Integer index){
        boolean isUserDelete=categoryService.deleteCategory(index);
        if (!isUserDelete) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api("Sorry but your request not Delete", 400));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new Api(" Delete request",200));

    }



}
