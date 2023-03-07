package it.unicam.cs.pawm.davidemenghini.simpleblog.Model.service;

import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.DefaultUser;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.UserCsrfToken;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.repository.DefaultTokenRepository;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.repository.DefaultUserCrudRepository;
import jakarta.servlet.http.Cookie;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.UUID;


@NoArgsConstructor
public class DefaultUserChecker implements UserSessionChecker {

    @Autowired
    private DefaultUserCrudRepository userRepo;

    @Autowired
    private DefaultTokenRepository tokenRepo;


    private static final Logger logger = LoggerFactory.getLogger(DefaultUserChecker.class);
    @Override
    public boolean checkSession(String sessionId, String csrf, int idUser) {
        Optional<DefaultUser> userOpt = this.userRepo.findById(idUser);
        Optional<UserCsrfToken> userCsrfToken = this.tokenRepo.findById(idUser);
        logger.info(userOpt.isPresent()+", "+userCsrfToken.isPresent());
        if((userOpt.isPresent()) && (userCsrfToken.isPresent())){
            DefaultUser user = userOpt.get();
            String csrfToken = userCsrfToken.get().getToken();
            logger.info("expected: "+user.getSession_id()+" actual: "+sessionId +" expected: "+csrfToken +" csrf:"+csrf);
            return (user.getSession_id().equals(sessionId))&&(csrfToken.equals(csrf));
        }
        else return false;
    }

    @Override
    public String generateNewToken(int idUser) {
        String csrfToken = this.generateToken();
        this.saveCsrfToken(csrfToken,idUser);
        return csrfToken;
    }

    private void saveCsrfToken(String csrfToken, int idUser) {
        UserCsrfToken token = new UserCsrfToken();
        token.setId(idUser);
        token.setToken(csrfToken);
        logger.info("L'utente "+token.getId()+" ha il token "+ token.getToken());
        this.tokenRepo.save(token);
    }

    private String generateToken() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
