package it.unicam.cs.pawm.davidemenghini.simpleblog.Model.service;

import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.Comment;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.repository.DefaultCommentCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DefaultCommentService implements CommentService{


    @Autowired
    private DefaultCommentCrudRepository commentRepo;


    @Override
    public List<Comment> getCommentFromIdPost(int id) {
        return this.commentRepo.findCommentsByByidPost(id);
    }
}
