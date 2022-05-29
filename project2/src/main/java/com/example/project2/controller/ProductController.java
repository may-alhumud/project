package com.example.project2.controller;

import com.example.project2.model.Api;
import com.example.project2.model.Cart;
import com.example.project2.model.Comment;
import com.example.project2.model.Product;



import com.example.project2.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/product")
public class ProductController {
    private final ProductService productService;

    public ProductController( ProductService productService) {
        this.productService = productService;

    }

    @GetMapping
    public ResponseEntity<ArrayList<Product>> getProduct(){
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProduct());

    }

    @PostMapping
    public ResponseEntity<Api> addProduct(@RequestBody @Valid Product p, Errors error){
        if (error.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api(error.getFieldError().getDefaultMessage(),400));
        }
        boolean isAccepted=productService.addProduct(p);
        if (!isAccepted) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api("Sorry but your request not Accepted", 400));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new Api(" Accepted request",200));

    }

    @PutMapping("/{index}")
    public ResponseEntity<Api> updateProduct(@PathVariable Integer index,@RequestBody @Valid Product p, Errors error){
        if (error.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api(error.getFieldError().getDefaultMessage(),400));
        }
        boolean isUpdate=productService.updateProduct(index,p);
        if (!isUpdate) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api("Sorry but your request not Accepted", 400));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new Api(" Accepted request",200));

    }

    @DeleteMapping("/delete/{index}")
    public ResponseEntity<Api> deleteProduct(@PathVariable Integer index){
        boolean isDelete=productService.deleteProduct(index);
        if (!isDelete) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api("Sorry but your request not Accepted", 400));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new Api(" Accepted request",200));

    }


    @GetMapping("/rated5")
    public ResponseEntity<List<Product>> getRatedFiveProducts(){
        ArrayList<Product> products = this.productService.getRatedFive();

        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping("/{productId}/comments")
    public ResponseEntity<List<Comment>> getProductComments(@PathVariable Integer productId){
        Product product = this.productService.findProduct(productId);

        if(product==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<>());
        }
        return ResponseEntity.status(HttpStatus.OK).body(product.getCommentlist());
    }








}
