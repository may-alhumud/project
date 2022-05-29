package com.example.project2.controller;

import com.example.project2.model.Api;
import com.example.project2.model.PurchaseHistory;
import com.example.project2.model.User;
import com.example.project2.service.PurchaseHistoryService;
import com.example.project2.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequestMapping("api/v1/purchasehistory")
public class PurchaseHistoryController {
    private final PurchaseHistoryService purchaseHistoryService;

    public PurchaseHistoryController( PurchaseHistoryService purchaseHistoryService) {
        this.purchaseHistoryService = purchaseHistoryService;

    }

    @GetMapping
    public ResponseEntity<ArrayList<PurchaseHistory>> getPurchaseHistory(){
        return ResponseEntity.status(HttpStatus.OK).body(purchaseHistoryService.getPurchaseHistory());

    }

    @PostMapping
    public ResponseEntity<Api> addPurchaseHistory(@RequestBody @Valid PurchaseHistory purchasehistory, Errors error){
        if (error.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api(error.getFieldError().getDefaultMessage(),400));
        }
        boolean isAccepted=purchaseHistoryService.addPurchaseHistory(purchasehistory);
        if (!isAccepted) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api("Sorry but your request not Accepted", 400));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new Api(" Accepte request",200));

    }

    @PutMapping("/{index}")
    public ResponseEntity<Api> updatePurchaseHistory(@PathVariable Integer index,@RequestBody @Valid PurchaseHistory purchasehistory, Errors error){
        if (error.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api(error.getFieldError().getDefaultMessage(),400));
        }
        boolean isUpdate=purchaseHistoryService.updatePurchaseHistory(index,purchasehistory);
        if (!isUpdate) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api("Sorry but your request not Accepted", 400));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new Api(" Accepte request",200));

    }

    @DeleteMapping("/delete/{index}")
    public ResponseEntity<Api> deletePurchaseHistory(@PathVariable Integer index){
        boolean isDelete=purchaseHistoryService.deletePurchaseHistory(index);
        if (!isDelete) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api("Sorry but your request not Accepted", 400));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new Api(" Accepte request",200));

    }



}
