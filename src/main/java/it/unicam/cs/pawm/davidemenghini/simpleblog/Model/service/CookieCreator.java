package it.unicam.cs.pawm.davidemenghini.simpleblog.Model.service;

import org.springframework.http.ResponseCookie;

@FunctionalInterface
public interface CookieCreator {



    ResponseCookie createCookie(String [] cookieContainer, boolean isHttpOnly);


    static ResponseCookie cookie(String [] cookieContainer, boolean isHttpOnly){
        if(isHttpOnly){
            return ResponseCookie.from(cookieContainer[0],cookieContainer[1])
                    .httpOnly(isHttpOnly)
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
