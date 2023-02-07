package it.unicam.cs.pawm.davidemenghini.simpleblog.Controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.DefaultUser;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.UserCsrfToken;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.service.DefaultLoginHandler;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.service.SessionHandlerUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;

@RestController
public class UserSessionHandlerApi {


    @Autowired
    private SessionHandlerUtil sessionHandlerUtil;


    private final BiFunction<String,String, DefaultUser> loginFunction = (username, password)->{
        if(this.sessionHandlerUtil.checkUser(username)){
            if(this.sessionHandlerUtil.checkPasswordAfterSecretAndSalt(
                    this.sessionHandlerUtil.saltAndSecretPsw(password))){
                return this.sessionHandlerUtil.login();
            }
        }
        return null;
    };

    @PostMapping("/api/public/login")
    public ResponseEntity<DefaultUser> handleLogin(@RequestBody String userAndPass, HttpServletResponse httpServletResponse){
        Map<String,String> jsonValues = this.getJsonValuesFromString(userAndPass);
        DefaultUser u = this.loginFunction.apply(jsonValues.get("username"), jsonValues.get("psw"));
        if(Objects.isNull(u)) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        String token = this.sessionHandlerUtil.getCsrfToken(u.getId());
        httpServletResponse.addCookie(new Cookie("csrf_cookie",token));
        httpServletResponse.addCookie(new Cookie("session_id",u.getSession_id()));
        return new ResponseEntity<>(u, HttpStatus.OK);
    }

    @PostMapping("api/private/logout")
    public ResponseEntity<Void> handleLogout(@RequestBody String idAndUser){
        return null;
    }

    private Map<String, String> getJsonValuesFromString(String userAndPass) {
        Map<String, String> m = new HashMap<>(2);
        JsonObject jsonObject = new Gson().fromJson(userAndPass, JsonObject.class);
        m.put("username",jsonObject.get("user").getAsString());
        m.put("psw",jsonObject.get("psw").getAsString());
        return m;
    }

}
