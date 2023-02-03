package it.unicam.cs.pawm.davidemenghini.simpleblog.Model.security;

import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.DefaultUser;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.repository.DefaultUserCrudRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
public class DefaultLoginHandler implements SessionHandlerUtil{

    private boolean hasBeenSaltAndSecret = false;

    private DefaultUser user;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private DefaultUserCrudRepository userRepo;

    private String securePsw;

    private static final Logger logger = LoggerFactory.getLogger(DefaultLoginHandler.class);


    @Override
    public boolean checkUser(String username) {
        this.user = this.userRepo.findDefaultUserByUsername(username);
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
        this.hasBeenSaltAndSecret = true;
        return this.securePsw;
    }

    @Override
    @Transactional
    public DefaultUser login() {
        UUID uuid = UUID.randomUUID();
        String sessionId = uuid.toString();
        this.userRepo.setEnableAndSession_idForUser(sessionId,1,this.user.getId());
        this.user.setEnabled(1);
        this.user.setSession_id(sessionId);
        logger.info("L'utente "+this.user.getId()+" è online("+this.user.getEnabled()+")...");
        return this.user;
    }

    @Override
    @Transactional
    public void logout(String username,String session_id) {
        DefaultUser user = this.userRepo.findDefaultUserByUsername(username);
        if(user.getSession_id().equals(session_id) && user.getEnabled()==1){
            this.userRepo.setEnableAndSession_idForUser(null,0,user.getId());
        }
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
}
