package it.unicam.cs.pawm.davidemenghini.simpleblog.model.security;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.DefaultUser;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.UserCsrfToken;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.repository.DefaultTokenRepository;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.repository.DefaultUserCrudRepository;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.service.SessionHandlerUtil;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginHandlerTest {




    @Autowired
    private SessionHandlerUtil sessionHandlerUtil;


    @Autowired
    private DefaultTokenRepository tokenRepo;

    @Autowired
    private DefaultUserCrudRepository userRepo;


    @Test
    @Order(2)
    public void launchIllegalStateException(){
        DefaultUser expectedUser = this.getExpectedUser();
        //this.sessionHandlerUtil.checkUser(expectedUser.getUsername());
        Exception exception = assertThrows(IllegalStateException.class,() ->this.sessionHandlerUtil.checkPasswordAfterSecretAndSalt(""));
    }

    @Test
    @Order(1)
    public void checkUsernameTest(){
        assertTrue(this.checkUser());
    }

    @Test
    @Order(3)
    public void saltAndSecretPasswordTest(){
        //this.checkUser();
        String hashedPsw = this.sessionHandlerUtil.saltAndSecretPsw("prova1");
        assertEquals("683ca0111aee25516ac67f7f3d8d2717f6c075940ec2ceaa8f4d251f491d9adf",hashedPsw);
    }

    @Test
    @Order(4)
    public void loginTest(){
        //controllo tabella default_user
        DefaultUser user = this.sessionHandlerUtil.login();
        assertNotNull(user);
        assertEquals(1,user.getEnabled());
    }

    @Test
    @Order(5)
    public void csfrTokenTest(){
        String userCsrfToken = this.sessionHandlerUtil.getCsrfToken(1);
        assertNotNull(userCsrfToken);
        UserCsrfToken t = this.tokenRepo.findById(1).orElse(null);
        assertNotNull(t);
        UserCsrfToken expectedTokenObj = new UserCsrfToken();
        expectedTokenObj.setToken(userCsrfToken);
        expectedTokenObj.setId(1);
        assertEquals(expectedTokenObj,t);
    }

    @Test
    @Order(6)
    public void logoutTest(){
        //DefaultUser user = this.makeLogin();
        DefaultUser user = this.userRepo.findDefaultUserByUsername("utente1");
        this.sessionHandlerUtil.logout("utente1",user.getSession_id());
        DefaultUser actualUser = this.userRepo.findDefaultUserByUsername("utente1");
        assertNull(actualUser.getSession_id());
        assertEquals(0,actualUser.getEnabled());
    }


    private DefaultUser getExpectedUser() {
        DefaultUser user = new DefaultUser();
        user.setRole_user("user");
        user.setEnabled(1);
        user.setId(1);
        user.setData_img("null".getBytes());
        user.setPsw("683ca0111aee25516ac67f7f3d8d2717f6c075940ec2ceaa8f4d251f491d9adf");
        user.setUsername("utente1");
        user.setSalt("pippo");
        user.setSession_id(null);
        return user;
    }

    private boolean checkUser(){
        DefaultUser expectedUser = this.getExpectedUser();
        return this.sessionHandlerUtil.checkUser(expectedUser.getUsername());
    }






}
