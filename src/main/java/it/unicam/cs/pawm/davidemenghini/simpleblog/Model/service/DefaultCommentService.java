package it.unicam.cs.pawm.davidemenghini.simpleblog.Model.service;

import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.Comment;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.LikeComment;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

@Transactional
public class DefaultCommentService implements CommentService{


    @Autowired
    private DefaultCommentCrudRepository commentRepo;

    @Autowired
    private DefaultLikeCommentRepository likeCommentRepo;

    @Autowired
    private DefaultDislikeCommentRepository dislikeCommentRepo;


    @Override
    public List<Comment> getCommentFromIdPost(int id) {
        return this.commentRepo.findCommentsByByidPost(id);
    }

    @Override
    public boolean isLikedToUser(int idUser) {
        LikeComment lc = this.likeCommentRepo.findByIdUser(idUser);
        return Objects.nonNull(lc);
    }

    @Override
    public boolean isDislikedToUser(int idUser) {
        LikeComment lc = this.dislikeCommentRepo.findByIdUser(idUser);
        return Objects.nonNull(lc);
    }
}
