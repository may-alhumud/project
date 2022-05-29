package com.example.project2.service;

import com.example.project2.model.Category;
import com.example.project2.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CategoryService {
    
    private ArrayList<Category> category=new ArrayList<>();


    public ArrayList<Category> getCategory() {
        return category;
    }

    public boolean addCategory(Category c) {
        return category.add(c);
    }

    public boolean updateCategory(Integer index, Category c) {
        category.set(index,c);
        return true;
    }

    public boolean deleteCategory(int index) {
        category.remove(index);
        return true;
    }
}
