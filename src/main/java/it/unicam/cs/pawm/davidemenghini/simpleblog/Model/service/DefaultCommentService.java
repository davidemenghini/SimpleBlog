package it.unicam.cs.pawm.davidemenghini.simpleblog.Model.service;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.*;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.repository.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Transactional
public class DefaultCommentService implements CommentService{


    @Autowired
    private DefaultCommentCrudRepository commentRepo;

    @Autowired
    private DefaultLikeCommentRepository likeCommentRepo;

    @Autowired
    private DefaultDislikeCommentRepository dislikeCommentRepo;


    private static final Logger logger = LoggerFactory.getLogger(DefaultCommentService.class);

    @Override
    public List<Comment> getCommentFromIdPost(int id) {
        return this.commentRepo.findCommentsByByidPost(id);
    }

    @Override
    public boolean isLikedToUser(int idUser,int idComment) {
        LikeComment lc = this.likeCommentRepo.findByIdUser(idUser,idComment);
        logger.info("likeComment.isNull? "+ Objects.nonNull(lc)+", idu: "+idUser);
        return Objects.nonNull(lc);
    }

    @Override
    public boolean isDislikedToUser(int idUser,int idComment) {
        DislikeComment lc = this.dislikeCommentRepo.findByIdUser(idUser,idComment);
        logger.info("dislikeComment.isNull? "+ Objects.nonNull(lc)+", idu: "+idUser);
        return Objects.nonNull(lc);
    }

    @Override
    public void addLikeToUser(int idUser, int idComment) {
        Random random=new Random();
        int id;
        do{
            id=random.nextInt(30000);
        }while (!this.checkIfIdLikeExists(id,idUser,idComment));
        Optional<Comment> commentOpt = this.commentRepo.findById(idComment);
        if(commentOpt.isPresent()){
            Comment comment = commentOpt.get();
            comment.setLike_number(comment.getLike_number()+1);
            LikeComment newLikeComment = generateNewLike(id,idUser,idComment);
            this.commentRepo.save(comment);
            this.likeCommentRepo.save(newLikeComment);
        }
    }

    @Override
    public void addDislikeToUser(int idUser, int idComment) {
        Random random=new Random();
        int id;
        do{
            id=random.nextInt(30000);
        }while (!this.checkIfIdLikeExists(id,idUser,idComment));
        Optional<Comment> commentOpt = this.commentRepo.findById(idComment);
        if(commentOpt.isPresent()){
            Comment comment = commentOpt.get();
            comment.setDislike_number(comment.getDislike_number()+1);
            DislikeComment newDislikeComment = generateNewDislike(id,idUser,idComment);
            this.commentRepo.save(comment);
            this.dislikeCommentRepo.save(newDislikeComment);
        }
    }

    @Override
    public void removeLikeToUser(int idUser, int idComment) {
        LikeComment lc = this.likeCommentRepo.findByIdUser(idUser,idComment);
        if(Objects.nonNull(lc)){
            this.likeCommentRepo.delete(lc);
            Optional<Comment> p = this.commentRepo.findById(idComment);
            if(p.isPresent()){
                Comment newComment = p.get();
                int newLikeNumber = Math.max(newComment.getLike_number() - 1, 0);
                newComment.setLike_number(newLikeNumber);
                this.commentRepo.save(newComment);
            }
        }

    }

    @Override
    public void removeDislikeToUser(int idUser, int idComment) {
        DislikeComment lc = this.dislikeCommentRepo.findByIdUser(idUser,idComment);
        if(Objects.nonNull(lc)){
            this.dislikeCommentRepo.delete(lc);
            Optional<Comment> p = this.commentRepo.findById(idComment);
            if(p.isPresent()){
                Comment newComment = p.get();
                int newDislikeNumber = Math.max(newComment.getDislike_number() - 1, 0);
                newComment.setDislike_number(newDislikeNumber);
                this.commentRepo.save(newComment);
            }
        }
    }

    @Override
    public boolean checkIfIdCommentExist(int id) {
        return !Objects.equals(this.commentRepo.findById(id),Optional.empty());
    }

    @Override
    public void createNewComment(Comment c) {
        if (Objects.nonNull(c)){
            this.commentRepo.save(c);
        }
    }


    private boolean checkIfIdLikeExists(int id,int idPost,int idUser) {
        return Objects.isNull(this.likeCommentRepo.existByIdAndIdPostAndIdUser(id,idPost,idUser));
    }

    private LikeComment generateNewLike(int id, int idUser,int idComment){
        LikeComment lc = new LikeComment();
        lc.setIdComment(idComment);
        lc.setId(id);
        lc.setIdUser(idUser);
        return lc;

    }

    private DislikeComment generateNewDislike(int id,int idUser,int idComment){
        DislikeComment lc = new DislikeComment();
        lc.setIdComment(idComment);
        lc.setId(id);
        lc.setIdUser(idUser);
        return lc;
    }




}
