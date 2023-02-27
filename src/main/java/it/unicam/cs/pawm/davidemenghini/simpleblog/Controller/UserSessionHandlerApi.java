package it.unicam.cs.pawm.davidemenghini.simpleblog.Controller;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.DefaultUser;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.service.DefaultUserCheckerChecker;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.service.SessionHandlerUtil;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.service.UserService;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.service.UserSessionChecker;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;

@Controller
@CrossOrigin(allowCredentials = "true",exposedHeaders = "Set-Cookie, csrf_token", origins = "http://localhost:3000")
public class UserSessionHandlerApi {


    @Autowired
    private SessionHandlerUtil sessionHandlerUtil;

    @Autowired
    private UserService userService;

    private final UserSessionChecker userSessionChecker = new DefaultUserCheckerChecker();

    private final BiFunction<String[],Boolean,ResponseCookie> createCookie = (cookie,isHttpOnly)->{
        if(isHttpOnly){
            return ResponseCookie.from(cookie[0],cookie[1])
                    .httpOnly(isHttpOnly)
                    .secure(true)
                    .sameSite("None")
                    .domain(".localhost")
                    .path("/api/")

                    .maxAge(3600)
                    .build();
        }else{
            return ResponseCookie.from(cookie[0],cookie[1])
                    .build();
        }
    };


    private static final Logger logger = LoggerFactory.getLogger(UserSessionHandlerApi.class);


    private final BiFunction<String,String, DefaultUser> loginFunction = (username, password)->{
        if(this.sessionHandlerUtil.checkUser(username)){
            if(this.sessionHandlerUtil.checkPasswordAfterSecretAndSalt(this.sessionHandlerUtil.saltAndSecretPsw(password))){
                return this.sessionHandlerUtil.login();
            }
        }
        return null;
    };

    @RequestMapping(value = "/api/public/login/", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> handleLogin(@RequestBody String userAndPass, HttpServletResponse httpServletResponse){
        Map<String,String> jsonValues = this.getJsonValuesFromString(userAndPass);
        DefaultUser u = this.loginFunction.apply(jsonValues.get("username"), jsonValues.get("psw"));
        if(Objects.isNull(u)){
            logger.info("errore nel login...");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String[] a = new String[2];
        a[0] = "session_id";
        a[1] = u.getSession_id();
        httpServletResponse.setHeader(HttpHeaders.SET_COOKIE,this.createCookie.apply(a,true).toString());
        String jsonUser = this.fromObjToJson(u);
        //headers.add("csrf_token",this.sessionHandlerUtil.getCsrfToken(u.getId()));
        return new ResponseEntity<>(jsonUser, HttpStatus.OK);
    }

    private String fromObjToJson(DefaultUser u) {
        JsonObject json = new JsonObject();
        json.addProperty("id",u.getId());
        logger.info(json.toString());
        return json.toString();
    }


    @RequestMapping(value = "/api/public/csrf/", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> getCsrfToken(@RequestBody String userAndPass, HttpServletResponse httpServletResponse){
        logger.info("retrieving csrf token");
        Map<String,String> jsonValues = this.getJsonValuesFromString(userAndPass);
        DefaultUser u = this.loginFunction.apply(jsonValues.get("username"), jsonValues.get("psw"));
        if(Objects.isNull(u)){
            logger.info("errore nel login...");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String[] a = new String[2];
        a[0] = "csrf_token";
        a[1] = this.sessionHandlerUtil.getCsrfToken(u.getId());
        HttpHeaders headers = new HttpHeaders();
        httpServletResponse.setHeader(HttpHeaders.SET_COOKIE,this.createCookie.apply(a,true).toString());
        return new ResponseEntity<>("ok",headers,HttpStatus.OK);
    }

    @RequestMapping(value="/api/private/logout/", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> handleLogout(@CookieValue(name = "session_id") String session_id, @CookieValue(name = "csrf_token") String csrf_token, @RequestBody String idAndUser){
        logger.info("handling logout...");
        Map<String,String> jsonValues = this.getJsonValuesFromIdAndUser(idAndUser);
        if( Objects.nonNull(jsonValues) && this.userSessionChecker.checkSession(session_id,csrf_token, Integer.parseInt(jsonValues.get("id")))){
            this.sessionHandlerUtil.logout(jsonValues.get("username"),session_id);
            return new ResponseEntity<>("ok",HttpStatus.OK);
        }else{
            return new ResponseEntity<>("error",HttpStatus.FORBIDDEN);
        }
    }

    private Map<String, String> getJsonValuesFromIdAndUser(String idAndUser) {
        Type type = new TypeToken<Map<String, String>>(){}.getType();
        Map<String,String> m = new Gson().fromJson(idAndUser, type);
        m.forEach((x,y)->logger.info("x: "+x+" y: "+y));
        logger.info("m.toString(): "+m.toString());
        Map<String,String> ret = new HashMap<>();
        ret.put("username", m.get("user"));
        ret.put("id", m.get("id"));
        System.out.println(ret.toString());
        return null;
    }

    private Map<String, String> getJsonValuesFromString(String userAndPass) {
        logger.info("login value: "+userAndPass);
        Type type = new TypeToken<Map<String, String>>(){}.getType();
        Map<String,String> m = new Gson().fromJson(userAndPass, type);
        m.forEach((x,y)->logger.info("x: "+x+" y: "+y));
        logger.info("m.toString(): "+m.toString());
        //Map values = new Gson().fromJson(m.get("userAndPass"),Map.class);
        Map<String,String> ret = new HashMap<>();
        ret.put("username", m.get("user"));
        ret.put("psw", m.get("psw"));
        System.out.println(ret.toString());
        return ret;
    }

    private Map<String, String> getJsonUserAndIdValues(String idAndUser) {
        logger.info("logout value: "+idAndUser);
        Type type = new TypeToken<Map<String, String>>(){}.getType();
        Map<String,String> m = new Gson().fromJson(idAndUser, type);
        m.forEach((x,y)->logger.info("x: "+x+" y: "+y));
        logger.info("m.toString(): "+m.toString());
        //Map values = new Gson().fromJson(m.get("userAndPass"),Map.class);
        Map<String,String> ret = new HashMap<>();
        ret.put("username", m.get("user"));
        ret.put("psw", m.get("psw"));
        System.out.println(ret.toString());
        return ret;
    }

    @RequestMapping(value="/api/public/user/{idUser}/")
    public ResponseEntity<String> getUserFromId(@PathVariable int idUser){
        DefaultUser user = this.userService.getUserFromId(idUser);
        return new ResponseEntity<>(this.userService.transformUserToJson(user),HttpStatus.OK);
    }

}
