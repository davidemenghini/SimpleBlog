package it.unicam.cs.pawm.davidemenghini.simpleblog.Model.service;

import org.springframework.http.ResponseCookie;

/**
 * Questa interfaccia si occupa di creare i cookie che verrano inviati al client.
 */
@FunctionalInterface
public interface CookieCreator {


    /**
     * Questo metodo permette di creare i cookie.
     * @param cookieContainer Un array di stringhe che deve contenere due valori.
     * @param isHttpOnly se il cookie deve essere accessibile solo tramite http/https o no.
     * @return un cookie da aggiungere a un pacchetto http.
     */
    ResponseCookie createCookie(String [] cookieContainer, boolean isHttpOnly);


    /**
     * Creatore di cookie di default.
     * @param cookieContainer Array di stringhe che deve contenere due valori. Il primo valore &egrave; il nome, il secondo &egrave; il valore del cookie.
     * @param isHttpOnly se il cookie deve essere accessibile solo tramite http/https o no.
     * @return un cookie da aggiungere a un pacchetto http.
     */
    static ResponseCookie cookie(String [] cookieContainer, boolean isHttpOnly){
        if(isHttpOnly){
            return ResponseCookie.from(cookieContainer[0],cookieContainer[1])
                    .httpOnly(true)
                    .secure(true)
                    .sameSite("None")
                    .domain("localhost")
                    .path("/api/")
                    .maxAge(3600)
                    .build();
        }else{
            return ResponseCookie.from(cookieContainer[0],cookieContainer[1])
                    .build();
        }
    }
}
