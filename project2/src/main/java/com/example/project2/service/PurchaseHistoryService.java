package com.example.project2.service;

import com.example.project2.model.Cart;
import com.example.project2.model.Product;
import com.example.project2.model.PurchaseHistory;
import com.example.project2.model.User;
import org.springframework.stereotype.Service;

import javax.print.attribute.IntegerSyntax;
import java.util.ArrayList;

@Service
public class PurchaseHistoryService {
    
    private ArrayList<PurchaseHistory> purchasehistory=new ArrayList<>();


    public ArrayList<PurchaseHistory> getPurchaseHistory() {

        return purchasehistory;
    }

    public boolean addPurchaseHistory(PurchaseHistory P) {
        P.setId(this.purchasehistory.size() + 1);
        return purchasehistory.add(P);

    }

    public boolean updatePurchaseHistory(Integer index, PurchaseHistory P) {
        purchasehistory.set(index,P);
        return true;
    }

    public boolean deletePurchaseHistory(int index) {
        purchasehistory.remove(index);
        return true;
    }

    public boolean isBoughtBefore(Integer userId, Integer productId){
        for(int i = 0; i < this.purchasehistory.size(); i++){

            PurchaseHistory history = this.purchasehistory.get(i);
            if(history.getProductid() == productId && history.getUserid() == userId )
                return true;
        }
        return false;
    }

    public boolean addToHistory(Cart cart){
        for(int i = 0; i < cart.getProductlist().size(); i++) {
            Product p = cart.getProductlist().get(i);
            PurchaseHistory ph = new PurchaseHistory();
            ph.setId(this.purchasehistory.size() + 1);
            ph.setPrice(p.getPrice());
            ph.setUserid(cart.getUserid());
            ph.setProductid(p.getId());
            this.purchasehistory.add(ph);
        }
        return true;
    }
}
