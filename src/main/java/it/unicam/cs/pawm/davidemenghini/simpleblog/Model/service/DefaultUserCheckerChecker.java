package it.unicam.cs.pawm.davidemenghini.simpleblog.Model.service;

import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.DefaultUser;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.UserCsrfToken;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.repository.DefaultTokenRepository;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.repository.DefaultUserCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;


public class DefaultUserCheckerChecker implements UserSessionChecker {

    @Autowired
    private DefaultUserCrudRepository userRepo;

    @Autowired
    private DefaultTokenRepository tokenRepo;

    @Override
    public boolean checkSession(String sessionId, String csrf,int idUser) {
        Optional<DefaultUser> userOpt = this.userRepo.findById(idUser);
        Optional<UserCsrfToken> userCsrfToken = this.tokenRepo.findById(idUser);
        if((userOpt.isPresent()) && (userCsrfToken.isPresent())){
            DefaultUser user = userOpt.get();
            String csrfToken = userCsrfToken.get().getToken();
            return (user.getSession_id().equals(sessionId))&&(csrfToken.equals(csrf));
        }
        else return false;
    }
}
