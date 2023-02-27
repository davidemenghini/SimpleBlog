package it.unicam.cs.pawm.davidemenghini.simpleblog.Model.service;

import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.Post;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Questa &egrave; un'interfaccia di servizio che serve per comunicare con il repository dell'oggetto "{@link Post}".
 * Permette di
 */
@Service
@Transactional
public interface PostService {


    Post removePost(Post post);

    Post updatePost(Post post);

    void createPost(Post post);

    Post readPost(int idPost);

    List<Post> getRandomPosts(int randomPost);


    boolean isLikedToUser(int idUser);

    boolean isDislikedToUser(int idUser);
}
