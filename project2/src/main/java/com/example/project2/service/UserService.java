package com.example.project2.service;

import com.example.project2.model.Cart;
import com.example.project2.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {
    
    private ArrayList<User> users=new ArrayList<>();


    public ArrayList<User> getUsers() {
        return users;
    }

    public boolean addUser(User user) {
        return users.add(user);

    }
    public User findUser(Integer id){
        for(int i = 0; i < users.size(); i++){
            if(users.get(i).getId() == id)
                return users.get(i);
        }
        return null;
    }
    public boolean updateUser(Integer index, User user) {
        users.set(index,user);
        return true;
    }

    public boolean deleteUser(int index) {
        users.remove(index);
        return true;
    }

    public boolean isAbleToBuy(Integer userId, Cart cart){
        User user = this.findUser(userId);
        return user.getBalance() >= cart.getTotalCart();
    }

    public boolean reduceBalance(Integer userId, Integer balance){
        User user = this.findUser(userId);
        user.setBalance(user.getBalance() - balance);
        return true;
    }
}
