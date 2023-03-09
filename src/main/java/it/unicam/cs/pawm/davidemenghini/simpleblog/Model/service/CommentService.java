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


    boolean isLikedToUser(int idUser,int idComment);

    boolean isDislikedToUser(int idUser,int idComment);


    void addLikeToUser(int idUser,int idComment);


    void addDislikeToUser(int idUser,int idComment);

    void removeLikeToUser(int idUser,int idComment);

    void removeDislikeToUser(int idUser,int idComment);


    /**
     * Questo metodo controlla se un id di un commento esiste.
     * @param id id da controllare.
     * @return true se l'id esiste gi&agrave;, false altrimenti.
     */
    boolean checkIfIdCommentExist(int id);

    /**
     * Questo metodo crea un nuovo commento nel modello di persistenza.
     * @param c il nuovo commento da aggiungere.
     */
    void createNewComment(Comment c);
}
