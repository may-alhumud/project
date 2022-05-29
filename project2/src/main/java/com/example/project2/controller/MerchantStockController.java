package com.example.project2.controller;

import com.example.project2.model.Api;
import com.example.project2.model.MerchantStock;
import com.example.project2.model.Product;
import com.example.project2.model.User;
import com.example.project2.service.MerchantStockService;
import com.example.project2.service.ProductService;
import com.example.project2.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequestMapping("api/v1/merchantstock")
public class MerchantStockController {
    private final MerchantStockService merchantStockService;

    private final UserService userService;
    private final ProductService productService;

    public MerchantStockController(MerchantStockService merchantStockService, UserService userService, ProductService productService) {
        this.merchantStockService = merchantStockService;
        this.userService = userService;
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<ArrayList<MerchantStock>> getMerchantStock(){
        return ResponseEntity.status(HttpStatus.OK).body(merchantStockService.getMerchantStock());

    }

    @PostMapping
    public ResponseEntity<Api> addMerchantStock(@RequestBody @Valid MerchantStock merchantstock, Errors error){
        if (error.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api(error.getFieldError().getDefaultMessage(),400));
        }
        boolean isAccepted=merchantStockService.addMerchantStock(merchantstock);
        if (!isAccepted) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api("Sorry but your request not Accepted", 400));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new Api(" Accepted request",200));

    }

    @PutMapping("/{index}")
    public ResponseEntity<Api> updateMerchantStock(@PathVariable Integer index,@RequestBody @Valid MerchantStock merchantstock, Errors error){
        if (error.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api(error.getFieldError().getDefaultMessage(),400));
        }
        boolean isUpdate=merchantStockService.updateMerchantStock(index,merchantstock);
        if (!isUpdate) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api("Sorry but your request not Update", 400));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new Api(" Update request",200));

    }

    @DeleteMapping("/delete/{index}")
    public ResponseEntity<Api> deleteMerchantStock(@PathVariable Integer index){
        boolean isDelete=merchantStockService.deleteMerchantStock(index);
        if (!isDelete) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api("Sorry but your request not Delete", 400));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new Api(" Delete request",200));

    }

    @GetMapping("/{merchantId}/{userId}/add/{productId}/{stock}")
    public ResponseEntity<Api> addStock(@PathVariable Integer merchantId,
                                        @PathVariable Integer userId,
                                        @PathVariable Integer productId,
                                        @PathVariable Integer stock) {

        Product product = this.productService.findProduct(productId);

        if(product==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api("Sorry product is not found", 400));
        }
        User user = this.userService.findUser(userId);
        if(user==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api("Sorry user is not found", 400));
        }
        boolean added = this.merchantStockService.addStock(merchantId,productId,stock);
        if(!added){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api("Sorry can not add stock now!", 400));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new Api("Stock added successfully!",200));
    }


}
