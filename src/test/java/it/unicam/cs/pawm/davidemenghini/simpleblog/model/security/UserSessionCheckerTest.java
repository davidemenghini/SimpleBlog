package it.unicam.cs.pawm.davidemenghini.simpleblog.model.security;

import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.DefaultUser;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.UserCsrfToken;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.repository.DefaultTokenRepository;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.repository.DefaultUserCrudRepository;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.service.UserSessionChecker;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.service.SessionHandlerUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserSessionCheckerTest {

    @Autowired
    private DefaultUserCrudRepository userRepo;

    @Autowired
    private DefaultTokenRepository tokenRepo;

    @Autowired
    private SessionHandlerUtil sessionHandlerUtil;

    @Autowired
    private UserSessionChecker session;


    public void makeLogin(){
        if(sessionHandlerUtil.checkUser("utente2")){
            String psw = sessionHandlerUtil.saltAndSecretPsw("prova2");
            if(sessionHandlerUtil.checkPasswordAfterSecretAndSalt(psw)){
                sessionHandlerUtil.login();
            }
        }
    }




    @Test
    public void checkUserSessionTest(){
        this.makeLogin();
        Optional<UserCsrfToken> csrfTokenOpt = this.tokenRepo.findById(2);
        assertTrue(csrfTokenOpt.isPresent());
        String csrf = csrfTokenOpt.get().getToken();
        Optional<DefaultUser> userOpt = this.userRepo.findById(2);
        assertTrue(userOpt.isPresent());
        DefaultUser user = userOpt.get();
        //assertTrue(session.checkSession(user.getSession_id(),csrf,2));
        //assertFalse(session.checkSession("","",1));
        sessionHandlerUtil.checkUser("utente2");
        sessionHandlerUtil.logout("utente2",user.getSession_id());
        //assertFalse(session.checkSession(user.getSession_id(),csrf,2));
    }



}
