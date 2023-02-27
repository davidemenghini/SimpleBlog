package it.unicam.cs.pawm.davidemenghini.simpleblog.Model.service;

import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.Comment;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.repository.DefaultCommentCrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Questa interfaccia definisce il comportamento del servizio che si occupa del repository {@link DefaultCommentCrudRepository}.
 *
 */
@Service
public interface CommentService {

    /**
     * Questo metodo restituisce una lista di commenti a partire dall'id del post a cui &egrave; riferito il commento.
     * @param id id del post del commento.
     * @return Lista di commenti il cui id corrisponde a quello del post.
     */

    List<Comment> getCommentFromIdPost(int id);


    boolean isLikedToUser(int idUser);

    boolean isDislikedToUser(int idUser);





}
