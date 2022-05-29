package com.example.project2.controller;

import com.example.project2.model.Api;
import com.example.project2.model.Comment;
import com.example.project2.model.Product;
import com.example.project2.model.User;
import com.example.project2.service.CommentService;
import com.example.project2.service.ProductService;
import com.example.project2.service.PurchaseHistoryService;
import com.example.project2.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequestMapping("api/v1/comment")
public class CommentController {
    private final CommentService commentService;
    private final PurchaseHistoryService purchaseHistoryService;

    public CommentController(CommentService commentService,PurchaseHistoryService purchaseHistoryService) {
        this.commentService = commentService;

        this.purchaseHistoryService = purchaseHistoryService;
    }

    @GetMapping
    public ResponseEntity<ArrayList<Comment>> getComment(){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getComment());

    }

    @PostMapping
    public ResponseEntity<Api> addComment(@RequestBody @Valid Comment comment, Errors error){
        if (error.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api(error.getFieldError().getDefaultMessage(),400));
        }
        boolean isAccepted=commentService.addComment(comment);
        if (!isAccepted) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api("Sorry but your request not Accepted", 400));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new Api(" Accepted request",200));

    }

    @PutMapping("/{index}")
    public ResponseEntity<Api> updateComment(@PathVariable Integer index,@RequestBody @Valid Comment comment, Errors error){
        if (error.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api(error.getFieldError().getDefaultMessage(),400));
        }
        boolean isUpdate= commentService.updateComment(index, comment);
        if (!isUpdate) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api("Sorry but your request not Update", 400));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new Api(" Update request",200));

    }

    @DeleteMapping("/delete/{index}")
    public ResponseEntity<Api> deleteComment(@PathVariable Integer index){
        boolean isUserDelete= commentService.deleteComment(index);
        if (!isUserDelete) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api("Sorry but your request not Delete", 400));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new Api(" Delete request",200));

    }

    @PostMapping("/{productId}/{userId}")
    public ResponseEntity<Api> addProductComment(@PathVariable Integer productId,
                                                 @PathVariable Integer userId,
                                                 @RequestBody @Valid Comment comment,
                                                 Errors error){

        if (error.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api(error.getFieldError().getDefaultMessage(),400));
        }

        boolean isBefore = purchaseHistoryService.isBoughtBefore(userId,productId);
        if(!isBefore){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api("Sorry you did not buy before", 400));
        }
        boolean added = this.commentService.addComment(comment);
        if(!added){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api("Sorry comment can not be added", 400));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new Api(" Comment added successfully",200));
    }



}
