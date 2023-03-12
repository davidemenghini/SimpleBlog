package it.unicam.cs.pawm.davidemenghini.simpleblog.Model.service;

import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.DefaultUser;
import org.springframework.stereotype.Service;


/**
 * Questa interfaccia serve per recuperare un utente a partire dall'id o dall'username.
 */
@Service
public interface UserService {

    /**
     * Questo metodo restituisce l'utente a partire dall'id.
     * @param idUser id dell'utente da cercare.
     * @return L'utente il cui id corrsiponda con quello inserito.
     */
    DefaultUser getUserFromId(int idUser);

    /**
     * Questo metodo trasforma un utente in formato JSON.
     * @param user l'utente da trasformare.
     * @return una stringa contentente un JSON.
     */
    String transformUserToJson(DefaultUser user);


    /**
     * Questo metodo restituisce l'utente a partire dall'username.
     * @param username username
     * @return L'utente desiderato.
     */
    DefaultUser getUserFromUsername(String username);



}
