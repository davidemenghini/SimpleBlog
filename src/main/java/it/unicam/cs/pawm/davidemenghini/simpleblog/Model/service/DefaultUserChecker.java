package it.unicam.cs.pawm.davidemenghini.simpleblog.Model.service;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.DefaultUser;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.UserCsrfToken;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.repository.DefaultTokenRepository;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.repository.DefaultUserCrudRepository;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;



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

}
