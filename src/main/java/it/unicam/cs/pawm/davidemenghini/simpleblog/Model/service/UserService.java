package it.unicam.cs.pawm.davidemenghini.simpleblog.Model.service;

import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.DefaultUser;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    DefaultUser getUserFromId(int idUser);

    String transformUserToJson(DefaultUser user);


    DefaultUser getUserFromUsername(String username);



}
