package it.unicam.cs.pawm.davidemenghini.simpleblog.Model.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.DefaultUser;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.repository.DefaultUserCrudRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

@Transactional
public class DefaultUserService implements UserService{

    @Autowired
    private DefaultUserCrudRepository userRepo;

    @Override
    public DefaultUser getUserFromId(int idUser) {
        return this.userRepo.getReferenceById(idUser);
    }

    @Override
    public String transformUserToJson(DefaultUser user) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username",user.getUsername());
        return jsonObject.toString();
    }

    @Override
    public DefaultUser getUserFromUsername(String username) {
        return this.userRepo.findDefaultUserByUsername(username);
    }
}
