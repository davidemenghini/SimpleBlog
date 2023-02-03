package it.unicam.cs.pawm.davidemenghini.simpleblog.Model.security;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.DefaultUser;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Questa interfaccia serve per permettere ad un utente di effettuare il login ed il logout.
 */
@Component
public interface SessionHandlerUtil {

    /**
     * Questo metodo serve per controllare che l'username inserito esista.
     * @param username l'username da controllare.
     * @return True se l'username esiste, false altrimenti.
     */
    boolean checkUser(String username);

    /**
     * Questo metodo serve per controllare la password dopo aver esseguito l'hash sulla password unita alla secret e al salt.
     * @param SecretSaltpsw password unita alla secret e al salt.
     * @throws IllegalStateException Se non è stato chiamato il metodo {@link #saltAndSecretPsw}.
     * @return True se la password corrisponde, false altrimenti.
     */
    boolean checkPasswordAfterSecretAndSalt(String SecretSaltpsw) throws IllegalStateException;

    /**
     * Questo metodo serve per generare una password sicura. Ovvero alla password vengono aggiunti il "salt" e la "secret".
     * @param psw la password in chiaro.
     * @return La password sicura.
     */
    String saltAndSecretPsw(String psw);

    /**
     * permette di eseguire il login.
     */
    @Transactional
    DefaultUser login();

    /**
     * permette di eseguire il logout.
     */
    void logout(String username,String session_id);




}
