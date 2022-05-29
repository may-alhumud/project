package com.example.project2.service;

import com.example.project2.model.Product;
import com.example.project2.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    
    private ArrayList<Product> product=new ArrayList<>();


    public ArrayList<Product> getProduct() {

        return product;
    }
    public ArrayList<Product> getRatedFive(){
        ArrayList<Product> rated5 = new ArrayList<>();
        for(int i = 0; i< this.product.size();i++){
            Product p = this.product.get(i);
            if(p.isRatedFive())
            {
                rated5.add(p);
            }
        }
        return rated5;
    }
    public Product findProduct(Integer id){
        for(int i = 0; i < product.size(); i++)
        {
            if(product.get(i).getId() == id)
                return product.get(i);
        }

        return null;
    }
    public boolean addProduct(Product p) {
        return product.add(p);

    }

    public boolean updateProduct(Integer index, Product p) {
        product.set(index,p);
        return true;
    }

    public boolean deleteProduct(int index) {
        product.remove(index);
        return true;
    }
}
