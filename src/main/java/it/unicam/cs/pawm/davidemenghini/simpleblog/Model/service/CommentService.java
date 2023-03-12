package it.unicam.cs.pawm.davidemenghini.simpleblog.Model.service;

import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.Comment;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.repository.DefaultCommentCrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Questa interfaccia definisce il comportamento del servizio che si occupa del repository {@link DefaultCommentCrudRepository}.
 */
@Service
public interface CommentService {

    /**
     * Questo metodo restituisce una lista di commenti a partire dall'id del post a cui &egrave; riferito il commento.
     * @param id id del post del commento.
     * @return Lista di commenti il cui id corrisponde a quello del post.
     */

    List<Comment> getCommentFromIdPost(int id);


    /**
     * Questo metodo controlla se un utente ha aggiunto un "mi piace" a un commento.
     * @param idUser id dell'utente.
     * @param idComment id del commento da controllare.
     * @return True se il commento è piaciuto all'utente, false altrimenti.
     */
    boolean isLikedToUser(int idUser,int idComment);

    /**
     * Questo metodo controlla se un utente ha aggiunto un "non mi piace" a un commento.
     * @param idUser id dell'utente.
     * @param idComment id del commento da controllare.
     * @return True se il commento non è piaciuto all'utente, false altrimenti.
     */
    boolean isDislikedToUser(int idUser,int idComment);


    /**
     * Questo metodo aggiunge un "mi piace" a un commento.
     * @param idUser id dell'utente.
     * @param idComment id del commento.
     */
    void addLikeToUser(int idUser,int idComment);


    /**
     * Questo metodo aggiunge un "non mi piace" a un commento.
     * @param idUser id dell'utente.
     * @param idComment id del commento.
     */
    void addDislikeToUser(int idUser,int idComment);

    /**
     * Questo metodo rimuove un "mi piace" a un commento.
     * @param idUser id dell'utente.
     * @param idComment id del commento.
     */
    void removeLikeToUser(int idUser,int idComment);

    /**
     * Questo metodo rimuove un "non mi piace" a un commento.
     * @param idUser id dell'utente.
     * @param idComment id del commento.
     */
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
