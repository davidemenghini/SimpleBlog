package it.unicam.cs.pawm.davidemenghini.simpleblog.Model.security;

/**
 * Questa interfaccia serve per permettere ad un utente di effettuare il login ed il logout.
 */
public interface SessionHandler {

    boolean checkUser(String username);

    boolean checkPassword(String SecretSaltpsw);

    String saltAndSecretPsw(String psw);
}
