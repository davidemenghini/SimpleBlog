package it.unicam.cs.pawm.davidemenghini.simpleblog.Model.service;

import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.DefaultUser;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.UserCsrfToken;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.repository.DefaultTokenRepository;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.repository.DefaultUserCrudRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Transactional
public class DefaultLoginHandler implements SessionHandlerUtil{

    private boolean hasBeenSaltAndSecret = false;

    private DefaultUser user;

    @Autowired
    private DefaultUserCrudRepository userRepo;


    @Autowired
    private DefaultTokenRepository csrfRepo;

    private String securePsw;

    private static final Logger logger = LoggerFactory.getLogger(DefaultLoginHandler.class);
    private UserCsrfToken csrfToken;


    @Override
    public boolean checkUser(String username) {
        this.user = this.userRepo.findDefaultUserByUsername(username);
        logger.info("user check: "+!Objects.isNull(this.user)+" username: "+username);
        return !Objects.isNull(this.user);
    }

    @Override
    public boolean checkPasswordAfterSecretAndSalt(String SecretSaltpsw) throws IllegalStateException {
        logger.info("hasBeenSaltAndSecret: "+this.hasBeenSaltAndSecret+", this.user: "+this.user.toString());
        if (!this.hasBeenSaltAndSecret){
            throw new IllegalStateException("la password non è sicura.");
        }
        return Objects.equals(this.user.getPsw(), SecretSaltpsw);
    }

    @Override
    public String saltAndSecretPsw(String psw) {
        this.securePsw = this.generateHashedPsw(psw);
        logger.info("DefaultLoginHandler s&sp: "+this.securePsw);
        this.hasBeenSaltAndSecret = true;
        return this.securePsw;
    }

    @Override
    public DefaultUser login() {
        UUID uuid = UUID.randomUUID();
        String sessionId = uuid.toString();
        LocalDateTime now = LocalDateTime.now();
        this.userRepo.setEnableAndSession_idForUser(sessionId,1,now,this.user.getId());
        String csrfToken = this.generateToken();
        int idu = this.user.getId();
        this.saveCsrfToken(csrfToken,idu);
        this.user.setEnabled(1);
        this.user.setSession_id(sessionId);
        this.user.setExpiration_time(now);
        logger.info("L'utente "+this.user.getId()+" è online("+this.user.getEnabled()+")...");
        return this.user;
    }

    @Override
    public void logout(String username,String session_id) {
        DefaultUser user = this.userRepo.findDefaultUserByUsername(username);
        logger.info("All csrf tokens:");
        this.csrfRepo.findAll().forEach(x -> logger.info(x.toString()));
        if (user.getSession_id().equals(session_id) && user.getEnabled() == 1) {
            this.userRepo.setEnableAndSession_idForUser(null, 0, null,user.getId());
            this.deleteCsrfToken(user.getId());
        }
    }


    @Override
    public String getCsrfToken(int idu) {
        Optional<UserCsrfToken> opt = this.csrfRepo.findById(idu);
        if (opt.isPresent()){
            return opt.get().getToken();
        }else{
            return "";
        }
    }

    @Override
    public DefaultUser getUser() {
        return this.user;
    }


    private String getSecret(){
        return "ASdoGJF432dsa497H56KPOX";
    }


    private String generateHashedPsw(String psw){
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            return DefaultLoginHandler.encodeHex(messageDigest.digest((this.getSecret()+user.getSalt()+psw).getBytes()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


    private static String encodeHex(byte[] digest) {
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    private String generateToken(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    private void saveCsrfToken(String csrfToken, int idu){
        UserCsrfToken token = new UserCsrfToken();
        token.setId(idu);
        token.setToken(csrfToken);
        logger.info("L'utente "+token.getId()+" ha il token "+ token.getToken());
        this.csrfRepo.save(token);
        this.csrfToken = token;

    }

    private void deleteCsrfToken(int idu){
        this.csrfRepo.deleteById(idu);
    }

    public UserCsrfToken getCsrfToken() {
        return csrfToken;
    }
}
