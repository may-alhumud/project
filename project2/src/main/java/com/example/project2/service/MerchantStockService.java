package com.example.project2.service;

import com.example.project2.model.Cart;
import com.example.project2.model.MerchantStock;
import com.example.project2.model.Product;
import com.example.project2.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MerchantStockService {
    
    private ArrayList<MerchantStock> merchantstock =new ArrayList<>();


    public ArrayList<MerchantStock> getMerchantStock() {
        return merchantstock;
    }

    public boolean addMerchantStock(MerchantStock m) {
        return merchantstock.add(m);
    }

    public boolean updateMerchantStock(Integer index, MerchantStock m) {

        merchantstock.set(index,m);
        return true;
    }

    public boolean deleteMerchantStock(int index) {
        merchantstock.remove(index);
        return true;
    }
    public boolean addStock(Integer merchantId, Integer productId, Integer stock){
        for(int i = 0; i < this.merchantstock.size(); i++){
            MerchantStock ms = this.merchantstock.get(i);
            if(ms.getMerchantid() == merchantId && ms.getProductid() == productId )
            {
                ms.setStock(ms.getStock() + stock);
                return true;
            }
        }
        return false;
    }
    public boolean removeStock(Integer merchantId, Integer productId, Integer stock){
        for(int i = 0; i < this.merchantstock.size(); i++){
            MerchantStock ms = this.merchantstock.get(i);
            if(ms.getMerchantid() == merchantId && ms.getProductid() == productId )
            {
                ms.setStock(ms.getStock() - stock);
                return true;
            }
        }
        return false;
    }
    public Integer getStockOfProduct(Integer productId){
        for(int i = 0; i < this.merchantstock.size(); i++){
            if(this.merchantstock.get(i).getProductid() == productId){
                return this.merchantstock.get(i).getStock();
            }
        }
        return 0;
    }
    public boolean isStockAvailable(Cart cart){
        for(int i = 0; i< cart.getProductlist().size(); i++){
            Product p = cart.getProductlist().get(i);
            if(getStockOfProduct(p.getId()) <= 0)
                return false;
        }
        return true;
    }

    public boolean reduceStockByOne(Integer merchantId, Cart cart){
        for(int i = 0; i< cart.getProductlist().size(); i++){
            Product p = cart.getProductlist().get(i);
            removeStock(merchantId,p.getId(),1);
        }
        return true;
    }


}
