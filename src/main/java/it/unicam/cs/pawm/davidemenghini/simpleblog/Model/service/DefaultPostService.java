package it.unicam.cs.pawm.davidemenghini.simpleblog.Model.service;

import it.unicam.cs.pawm.davidemenghini.simpleblog.Controller.PostApi;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.DislikePost;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.LikeComment;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.LikePost;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.Post;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.repository.DefaultDislikePostRepository;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.repository.DefaultLikePostRepository;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.repository.DefaultPostCrudRepository;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@NoArgsConstructor
public class DefaultPostService implements PostService{


    @Autowired
    private DefaultPostCrudRepository postRepo;

    @Autowired
    private DefaultLikePostRepository likePostRepo;


    @Autowired
    private DefaultDislikePostRepository dislikePostRepo;

    private static final Logger logger = LoggerFactory.getLogger(DefaultPostService.class);

    @Override
    public Post removePost(Post post) {
        this.postRepo.delete(post);
        return post;
    }

    @Override
    public Post updatePost(Post post) {
        return this.postRepo.save(post);
    }

    @Override
    public void createPost(Post post) {
        int id = this.createNewPostId();
        post.setId(id);
        this.postRepo.save(post);
    }

    private int createNewPostId() {
        Random random=new Random();
        int id;
        do{
            id = random.nextInt(30000);
        }while(this.postRepo.findById(id).isPresent());
        return id;
    }

    @Override
    public Post readPost(int idPost) {
        Optional<Post> postOpt = this.postRepo.findById(idPost);
        return postOpt.orElse(null);
    }

    @Override
    public List<Post> getRandomPosts(int randomPost) {
        this.postRepo.readRandomPost(randomPost).forEach(x->logger.info(x.toString()));
        return this.postRepo.readRandomPost(randomPost);
    }

    @Override
    public boolean isLikedToUser(int idUser,int idPost) {
        List<LikePost> lc = this.likePostRepo.findByIdUser(idUser, idPost);
        logger.info("isDislikedToUser: "+ (Objects.nonNull(lc) && lc.size()>0)+" idPost: "+idPost);
        return Objects.nonNull(lc) && lc.size()>0;
    }

    @Override
    public boolean isDislikedToUser(int idUser,int idPost) {
        DislikePost dc = this.dislikePostRepo.findByIdUser(idUser,idPost);
        logger.info("isDislikedToUser: "+ (Objects.nonNull(dc))+" idPost: "+idPost+" idUser: "+idUser);
        return Objects.nonNull(dc);
    }

    @Override
    public void addLikeToPost(int idu, int idPost) {
        Random random=new Random();
        int id;
        do{
            id=random.nextInt(30000);
        }while (!this.checkIfIdLikeExists(id,idu,idPost));
        Optional<Post> newPostOpt = this.postRepo.findById(idPost);
        if(newPostOpt.isPresent()){
            Post newPost = newPostOpt.get();
            newPost.setLikeNumber(newPost.getLikeNumber()+1);
            LikePost lp = this.createLikePost(id,idPost,idu);
            this.likePostRepo.save(lp);
            this.postRepo.save(newPost);
        }

    }

    @Override
    public void addDislikeToPost(int idu, int idPost) {
        Random random=new Random();
        int id;
        do{
            id=random.nextInt(30000);
        }while (Objects.nonNull(this.dislikePostRepo.existByIdAndIdPostAndIdUser(id,idu,idPost)));
        Optional<Post> newPostOpt = this.postRepo.findById(idPost);
        if(newPostOpt.isPresent()){
            Post newPost = newPostOpt.get();
            newPost.setDislikeNumber(newPost.getDislikeNumber()+1);
            DislikePost lp = this.createDislikePost(id,idPost,idu);
            this.dislikePostRepo.save(lp);
            this.postRepo.save(newPost);
        }
    }

    private DislikePost createDislikePost(int id, int idPost, int idu) {
        DislikePost lp = new DislikePost();
        lp.setId(id);
        lp.setIdPost(idPost);
        lp.setIdUser(idu);
        return lp;
    }

    @Override
    public void removeLikeToPost(int idu, int idPost) {
        List<LikePost> lp = this.likePostRepo.findByIdUser(idu,idPost);
        if(Objects.nonNull(lp) && lp.size()>0){
            this.likePostRepo.delete(lp.get(0));
            Optional<Post> p = this.postRepo.findById(idPost);
            if(p.isPresent()){
                Post newPost = p.get();
                newPost.setLikeNumber(newPost.getLikeNumber()-1);
                this.postRepo.save(newPost);
            }
        }
    }

    @Override
    public void removeDislikeToPost(int idu, int idPost) {
        DislikePost lp = this.dislikePostRepo.findByIdUser(idu,idPost);
        if(Objects.nonNull(lp)){
            this.dislikePostRepo.delete(lp);
            Optional<Post> p = this.postRepo.findById(idPost);
            if(p.isPresent()){
                Post newPost = p.get();
                newPost.setDislikeNumber(newPost.getDislikeNumber()-1);
                this.postRepo.save(newPost);
            }
        }
    }

    private boolean checkIfIdLikeExists(int id,int idPost,int idUser) {
        return Objects.isNull(this.likePostRepo.existByIdAndIdPostAndIdUser(id,idPost,idUser));
    }

    private LikePost createLikePost(int id,int idPost,int idUser){
        LikePost lp = new LikePost();
        lp.setId(id);
        lp.setIdPost(idPost);
        lp.setIdUser(idUser);
        return lp;
    }


}
