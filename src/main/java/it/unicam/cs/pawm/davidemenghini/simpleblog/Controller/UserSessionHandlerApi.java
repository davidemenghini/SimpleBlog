package it.unicam.cs.pawm.davidemenghini.simpleblog.Controller;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.DefaultUser;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.service.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class UserSessionHandlerApi {


    @Autowired
    private SessionHandlerUtil sessionHandlerUtil;

    @Autowired
    private UserService userService;


    @Autowired
    private UserSessionChecker userSessionChecker;

    private final CookieCreator cookieCreator = CookieCreator::cookie;


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
    @CrossOrigin(origins = "http://localhost:3000", methods = POST, allowCredentials = "true",exposedHeaders = "Set-Cookie")
    public ResponseEntity<String> handleLogin(@RequestBody String userAndPass, HttpServletResponse httpServletResponse){
        Map<String,String> jsonValues = this.getJsonValuesFromString(userAndPass);
        DefaultUser u = this.loginFunction.apply(jsonValues.get("username"), jsonValues.get("psw"));
        if(Objects.isNull(u)){
            logger.info("errore nel login...");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        httpServletResponse.setHeader(HttpHeaders.SET_COOKIE,this.cookieCreator.createCookie(new String[]{"session_id",u.getSession_id()},true).toString());
        String jsonUser = this.fromObjToJson(u);
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
    @CrossOrigin(allowCredentials = "true",exposedHeaders = "Set-Cookie, csrf_token", origins = "http://localhost:3000")
    public ResponseEntity<String> getCsrfToken(HttpServletRequest request, @RequestBody String userAndPass, HttpServletResponse httpServletResponse){
        logger.info("retrieving csrf token");
        Cookie session_id = Arrays.stream(request.getCookies())
                .filter(x->x.getName().equals("session_id"))
                .findFirst().orElse(null);
        Map<String,String> jsonValues = this.getJsonValuesFromString(userAndPass);
        DefaultUser u = this.userService.getUserFromUsername(jsonValues.get("username"));
        if(Objects.isNull(u) || Objects.isNull(session_id)){
            logger.error("errore nel recupero dell'username...");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else if (u.getSession_id().equals(session_id.getValue())) {
            logger.info("getCsrf: "+this.sessionHandlerUtil.getCsrfToken(u.getId()));
            httpServletResponse.setHeader("csrf_token",this.sessionHandlerUtil.getCsrfToken(u.getId()));
            return new ResponseEntity<>("ok",HttpStatus.OK);
        }
        return null;
    }

    @RequestMapping(value="/api/private/logout/", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin(allowCredentials = "true",exposedHeaders = "*", origins = "http://localhost:3000")
    public ResponseEntity<String> handleLogout(HttpServletRequest request, @RequestBody String idAndUser){
        logger.info("handling logout...");
        Map<String,String> jsonValues = this.getJsonValuesFromIdAndUser(idAndUser);
        Map<String,String> cookies = Arrays.stream(request.getCookies())
                .filter(x->x.getName().equals("session_id"))
                .collect(Collectors.toMap(Cookie::getName, Cookie::getValue));
        if(this.userSessionChecker.checkSession(cookies.get("session_id"),request.getHeader("csrf_token"), Integer.parseInt(jsonValues.get("id")))){
            this.sessionHandlerUtil.logout(jsonValues.get("username"),cookies.get("session_id"));
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
        return ret;
    }

    private Map<String, String> getJsonValuesFromString(String userAndPass) {
        Type type = new TypeToken<Map<String, String>>(){}.getType();
        Map<String,String> m = new Gson().fromJson(userAndPass, type);
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
        Map<String,String> ret = new HashMap<>();
        ret.put("username", m.get("user"));
        ret.put("psw", m.get("psw"));
        return ret;
    }

    @GetMapping(value="/api/public/user/post/{idUser}/")
    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "csrf_token, content-type",methods = GET)
    @ResponseBody
    public ResponseEntity<String> getUserFromId(@PathVariable int idUser){
        DefaultUser user = this.userService.getUserFromId(idUser);
        return new ResponseEntity<>(this.userService.transformUserToJson(user),HttpStatus.OK);
    }

    @GetMapping(value="/api/public/user/comment/{idUser}/")
    @ResponseBody
    @CrossOrigin(origins = "http://localhost:3000",allowedHeaders = "*", methods = GET)
    public ResponseEntity<String> getUsernameFromId(@PathVariable int idUser){
        DefaultUser user = this.userService.getUserFromId(idUser);
        return new ResponseEntity<>(this.userService.transformUserToJson(user),HttpStatus.OK);
    }

}
