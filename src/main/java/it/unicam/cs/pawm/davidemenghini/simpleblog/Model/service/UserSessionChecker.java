package it.unicam.cs.pawm.davidemenghini.simpleblog.Model.service;

import jakarta.servlet.http.Cookie;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

/**
 * Questa funzione serve per controllare che un utente sia loggato nel server.
 */
@Transactional
@Service
@FunctionalInterface
public interface UserSessionChecker {


    /**
     * Questo metodo serve per controllare il session id e il token csrf di un utente che è loggato.
     * @param sessionId il cookie contenente l'id della sessione dell'utente.
     * @param csrf il cookie contenente il token csrf dell'utente.
     * @param idUser id dell'utente.
     * @return True se l'utente esiste e corrisponde, false altrimenti.
     */
    boolean checkSession(String sessionId, String csrf, int idUser);


}
