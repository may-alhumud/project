package com.example.project2.controller;

import com.example.project2.model.Api;
import com.example.project2.model.Cart;
import com.example.project2.model.Product;
import com.example.project2.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/direct")
public class DirectPurchaseController {

    private final UserService userService;
    private final ProductService productService;

    private final MerchantStockService merchantStockService;
    private final PurchaseHistoryService purchaseHistoryService;

    public DirectPurchaseController(UserService userService, ProductService productService, MerchantStockService merchantStockService, PurchaseHistoryService purchaseHistoryService) {
        this.userService = userService;
        this.productService = productService;
        this.merchantStockService = merchantStockService;
        this.purchaseHistoryService = purchaseHistoryService;
    }

    @GetMapping("/buy/{userId}/{merchantId}/{productId}")
    public ResponseEntity<Api> purchaseByProduct(@PathVariable Integer userId,@PathVariable Integer merchantId, @PathVariable Integer productId){

        //Should be moved to a service class better.
        Product p = this.productService.findProduct(productId);
        Cart cart = new Cart();
        ArrayList<Product> products = new ArrayList<>();
        products.add(p);
        cart.setProductlist(products);
        cart.setUserid(userId);

        boolean canBuy = this.userService.isAbleToBuy(cart.getUserid(),cart);
        if(!canBuy){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api("Sorry no balance available", 400));
        }
        boolean availableStock = this.merchantStockService.isStockAvailable(cart);
        if(!availableStock){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api("Sorry no stock available", 400));
        }

        this.merchantStockService.reduceStockByOne(merchantId, cart);
        this.userService.reduceBalance(cart.getUserid(), cart.getTotalCart());
        this.purchaseHistoryService.addToHistory(cart);

        return ResponseEntity.status(HttpStatus.OK).body(new Api(" Accepted request",200));

    }
}
