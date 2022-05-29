package com.example.project2.service;

import com.example.project2.model.Comment;
import com.example.project2.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CommentService {
    
    private ArrayList<Comment> comment=new ArrayList<>();



    public ArrayList<Comment> getComment() {
        return comment;
    }

    public boolean addComment(Comment c) {
        return comment.add(c);
    }

    public boolean updateComment(Integer index, Comment c) {
        comment.set(index,c);
        return true;
    }

    public boolean deleteComment(int index) {
        comment.remove(index);
        return true;
    }

}
