package com.example.project2.controller;

import com.example.project2.model.Api;
import com.example.project2.model.Merchant;
import com.example.project2.model.User;
import com.example.project2.service.MerchantService;
import com.example.project2.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequestMapping("api/v1/merchant")
public class MerchantController {
    private final MerchantService merchantService;

    public MerchantController( MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @GetMapping
    public ResponseEntity<ArrayList<Merchant>> getMerchant(){
        return ResponseEntity.status(HttpStatus.OK).body(merchantService.getMerchant());

    }

    @PostMapping
    public ResponseEntity<Api> addMerchant(@RequestBody @Valid Merchant merchant, Errors error){
        if (error.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api(error.getFieldError().getDefaultMessage(),400));
        }
        boolean isAccepted=merchantService.addMerchant(merchant);
        if (!isAccepted) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api("Sorry but your request not Accepted", 400));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new Api(" Accepted request",200));

    }

    @PutMapping("/{index}")
    public ResponseEntity<Api> updateMerchant(@PathVariable Integer index,@RequestBody @Valid Merchant merchant, Errors error){
        if (error.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api(error.getFieldError().getDefaultMessage(),400));
        }
        boolean isUpdate=merchantService.updateMerchant(index,merchant);
        if (!isUpdate) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api("Sorry but your request not Update", 400));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new Api(" Update request",200));

    }

    @DeleteMapping("/delete/{index}")
    public ResponseEntity<Api> deleteMerchant(@PathVariable Integer index){
        boolean isDelete=merchantService.deleteMerchant(index);
        if (!isDelete) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api("Sorry but your request not Delete", 400));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new Api(" Delete request",200));

    }



}
