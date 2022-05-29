package com.example.project2.controller;

import com.example.project2.model.Api;
import com.example.project2.model.Cart;
import com.example.project2.model.Product;
import com.example.project2.model.User;
import com.example.project2.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequestMapping("api/v1/cart")
public class CartController {
    private final CartService cartService;
    private final ProductService productService;
    private final UserService userService;
    private final MerchantStockService merchantStockService;
    private final PurchaseHistoryService purchaseHistoryService;

    public CartController(CartService cartService, ProductService productService, UserService userService, MerchantStockService merchantStockService, PurchaseHistoryService purchaseHistoryService) {
        this.cartService = cartService;
        this.productService = productService;
        this.userService = userService;
        this.merchantStockService = merchantStockService;
        this.purchaseHistoryService = purchaseHistoryService;
    }

    @GetMapping
    public ResponseEntity<ArrayList<Cart>> getCart(){
        return ResponseEntity.status(HttpStatus.OK).body(cartService.getCart());

    }
    @PostMapping
    public ResponseEntity<Api> addCart(@RequestBody @Valid Cart cart, Errors error){
        if (error.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api(error.getFieldError().getDefaultMessage(),400));
        }
        boolean isAcceptedAdd= cartService.addCart(cart);
        if (!isAcceptedAdd) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api("Sorry but your request not Accepted", 400));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new Api(" Accepted request",200));

    }

    @PostMapping("/{merchantId}")
    public ResponseEntity<Api> purchaseByCart(@PathVariable Integer merchantId, @RequestBody @Valid Cart cart, Errors error){
        if (error.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api(error.getFieldError().getDefaultMessage(),400));
        }
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

    @PutMapping("/{index}")
    public ResponseEntity<Api> updateCart(@PathVariable Integer index,@RequestBody @Valid Cart cart, Errors error){
        if (error.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api(error.getFieldError().getDefaultMessage(),400));
        }
        boolean isUserUpdate= cartService.updateCart(index,cart);
        if (!isUserUpdate) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api("Sorry but your request not Update", 400));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new Api(" Update request",200));

    }

    @DeleteMapping("/delete/{index}")
    public ResponseEntity<Api> deleteCart(@PathVariable Integer index){
        boolean isUserDelete= cartService.deleteCart(index);
        if (!isUserDelete) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api("Sorry but your request not Delete", 400));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new Api(" Delete request",200));

    }

    @GetMapping("/add/{userId}/{productId}")
    public ResponseEntity<Api> addToCart(@PathVariable Integer userId, @PathVariable Integer productId)
    {
        Product product = this.productService.findProduct(productId);

        if(product==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api("Sorry product is not found", 400));
        }
        User user = this.userService.findUser(userId);
        if(user==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api("Sorry user is not found", 400));
        }
        boolean added = this.cartService.addProductToCart(userId,product);
        if(!added){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api("Sorry Error adding to cart", 400));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new Api("Product is added to cart",200));
    }
    @GetMapping("/remove/{userId}/{productId}")
    public ResponseEntity<Api> removeFromCart(@PathVariable Integer userId, @PathVariable Integer productId)
    {
        Product product = this.productService.findProduct(productId);

        if(product==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api("Sorry product is not found", 400));
        }
        User user = this.userService.findUser(userId);
        if(user==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api("Sorry user is not found", 400));
        }
        boolean removed = this.cartService.removeProductFromCart(userId,product);
        if(!removed){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Api("Sorry Error removing from cart", 400));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new Api("Product is added to cart",200));
    }


}
