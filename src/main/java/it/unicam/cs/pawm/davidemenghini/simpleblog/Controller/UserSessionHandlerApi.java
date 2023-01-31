package it.unicam.cs.pawm.davidemenghini.simpleblog.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserSessionHandlerApi {


    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/api/public/login")
    public void handleLogin(@RequestBody String userAndPass){

    }

}
