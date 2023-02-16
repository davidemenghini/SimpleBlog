package it.unicam.cs.pawm.davidemenghini.simpleblog.Controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.DefaultUser;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.service.SessionHandlerUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;

@Controller
@CrossOrigin(origins = "http://localhost:3000/")
public class UserSessionHandlerApi {


    @Autowired
    private SessionHandlerUtil sessionHandlerUtil;


    private static final Logger logger = LoggerFactory.getLogger(UserSessionHandlerApi.class);


    private final BiFunction<String,String, DefaultUser> loginFunction = (username, password)->{
        if(this.sessionHandlerUtil.checkUser(username)){
            if(this.sessionHandlerUtil.checkPasswordAfterSecretAndSalt(
                    this.sessionHandlerUtil.saltAndSecretPsw(password))){
                return this.sessionHandlerUtil.login();
            }
        }
        return null;
    };

    @RequestMapping(value = "/api/public/login/", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<DefaultUser> handleLogin(@RequestBody String userAndPass, HttpServletResponse httpServletResponse){
        Map<String,String> jsonValues = this.getJsonValuesFromString(userAndPass);
        DefaultUser u = this.loginFunction.apply(jsonValues.get("username"), jsonValues.get("psw"));
        if(Objects.isNull(u)){
            logger.info("errore nel login...");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String token = this.sessionHandlerUtil.getCsrfToken(u.getId());
        Cookie csrf = new Cookie("csrf_cookie",token);
        csrf.setHttpOnly(true);
        httpServletResponse.addCookie(csrf);
        Cookie session_id = new Cookie("session_id",u.getSession_id());
        session_id.setHttpOnly(true);
        httpServletResponse.addCookie(session_id);
        return new ResponseEntity<>(u, HttpStatus.OK);
    }

    @PostMapping("api/private/logout")
    public ResponseEntity<Void> handleLogout(@RequestBody String idAndUser){
        return null;
    }

    private Map<String, String> getJsonValuesFromString(String userAndPass) {
        logger.info("login value: "+userAndPass);
        Map<String, String> m;
        m = new Gson().fromJson(userAndPass, Map.class);
        m.forEach((x,y)->logger.info("key.value: "+x+" "+y));
        logger.info("loginObj:"+m.toString());

        //m.put("username",jsonObject.get("user").toString());
        //m.put("psw",jsonObject.get("psw").toString());
        return m;
    }

}
