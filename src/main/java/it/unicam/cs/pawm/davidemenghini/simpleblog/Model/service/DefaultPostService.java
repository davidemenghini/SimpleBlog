package it.unicam.cs.pawm.davidemenghini.simpleblog.Model.service;

import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.LikeComment;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.Post;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.repository.DefaultDislikePostRepository;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.repository.DefaultLikePostRepository;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.repository.DefaultPostCrudRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@NoArgsConstructor
public class DefaultPostService implements PostService{


    @Autowired
    private DefaultPostCrudRepository postRepo;

    @Autowired
    private DefaultLikePostRepository likePostRepo;


    @Autowired
    private DefaultDislikePostRepository dislikePostRepo;

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
        this.postRepo.save(post);
    }

    @Override
    public Post readPost(int idPost) {
        Optional<Post> postOpt = this.postRepo.findById(idPost);
        return postOpt.orElse(null);
    }

    @Override
    public List<Post> getRandomPosts(int randomPost) {
        return this.postRepo.readRandomPost(randomPost);
    }

    @Override
    public boolean isLikedToUser(int idUser) {
        LikeComment lc = this.likePostRepo.findByIdUser(idUser);
        return Objects.nonNull(lc);
    }

    @Override
    public boolean isDislikedToUser(int idUser) {
        LikeComment lc = this.dislikePostRepo.findByIdUser(idUser);
        return Objects.nonNull(lc);
    }


}
