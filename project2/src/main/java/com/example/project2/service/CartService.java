package com.example.project2.service;


import com.example.project2.model.Cart;
import com.example.project2.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class CartService {



    private Map<Integer,Cart> userCarts = new HashMap<>();
    private ArrayList<Cart> cart = new ArrayList<>();


    public ArrayList<Cart> getCart() {

        return cart;
    }

    public boolean addCart(Cart c) {
        return cart.add(c);

    }

    public boolean updateCart(Integer index, Cart c) {
        cart.set(index,c);
        return true;
    }

    public boolean deleteCart(int index) {
        cart.remove(index);
        return true;
    }

    public Cart getCurrentCart(Integer userId){
        if(!this.userCarts.containsKey(userId)){
            return null;
        }
        return this.userCarts.get(userId);
    }

    public boolean addProductToCart(Integer userId, Product product)
    {
        return this.userCarts.get(userId).getProductlist().add(product);
    }
    public boolean removeProductFromCart(Integer userId, Product product){
        return this.userCarts.get(userId).getProductlist().remove(product);
    }
}
