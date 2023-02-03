package it.unicam.cs.pawm.davidemenghini.simpleblog.model.security;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.DefaultUser;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.repository.DefaultUserCrudRepository;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.security.SessionHandlerUtil;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.Isolated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginHandlerTest {




    @Autowired
    private SessionHandlerUtil sessionHandlerUtil;


    @Autowired
    private DefaultUserCrudRepository userRepo;


    @Test
    @Order(2)
    public void launchIllegalStateException(){
        DefaultUser expectedUser = this.getExpectedUser();
        this.sessionHandlerUtil.checkUser(expectedUser.getUsername());
        Exception exception = assertThrows(IllegalStateException.class,() ->this.sessionHandlerUtil.checkPasswordAfterSecretAndSalt(""));
    }

    @Test
    @Order(1)
    public void checkUsernameTest(){
        assertTrue(this.checkUser());
    }

    @Test
    public void saltAndSecretPasswordTest(){
        this.checkUser();
        String hashedPsw = this.sessionHandlerUtil.saltAndSecretPsw("prova1");
        assertEquals("683ca0111aee25516ac67f7f3d8d2717f6c075940ec2ceaa8f4d251f491d9adf",hashedPsw);
    }

    @Test
    @Transactional
    public void loginTest(){
        this.checkUser();
        String psw = this.sessionHandlerUtil.saltAndSecretPsw("prova1");
        this.sessionHandlerUtil.checkPasswordAfterSecretAndSalt(psw);
        DefaultUser user = this.sessionHandlerUtil.login();
        assertNotNull(user);
        assertEquals(1,user.getEnabled());
        this.userRepo.setEnableAndSession_idForUser(null,0,1);
    }


    @Test
    public void logoutTest(){
        DefaultUser user = this.makeLogin();
        this.sessionHandlerUtil.logout(user.getUsername(),user.getSession_id());
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


    private DefaultUser makeLogin(){
        this.checkUser();
        String psw = this.sessionHandlerUtil.saltAndSecretPsw("prova1");
        this.sessionHandlerUtil.checkPasswordAfterSecretAndSalt(psw);
        this.sessionHandlerUtil.login();
        return this.userRepo.findById(1).orElse(null);
    }




}
