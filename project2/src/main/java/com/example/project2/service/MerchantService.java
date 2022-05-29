package com.example.project2.service;

import com.example.project2.model.Merchant;
import com.example.project2.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MerchantService {
    
    private ArrayList<Merchant> merchant=new ArrayList<>();


    public ArrayList<Merchant> getMerchant() {
        return merchant;
    }

    public boolean addMerchant(Merchant m) {
        return merchant.add(m);
    }

    public boolean updateMerchant(Integer index, Merchant m) {
        merchant.set(index,m);
        return true;
    }

    public boolean deleteMerchant(int index) {
        merchant.remove(index);
        return true;
    }
}
