package it.unicam.cs.pawm.davidemenghini.simpleblog.Controller;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.Post;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.service.CookieCreator;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.service.PostService;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.service.UserSessionChecker;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@Order(Ordered.HIGHEST_PRECEDENCE)
public class PostApi{

    private Post p;

    private final int RANDOM_POST = 2;

    private static final Logger logger = LoggerFactory.getLogger(PostApi.class);

    private final CookieCreator cookieCreator = CookieCreator::cookie;

    @Autowired
    private UserSessionChecker userSessionChecker;

    @Autowired
    private PostService postService;


    private final Function<Cookie[], Map<String,String>> extractCookies = (cookies) -> Arrays.stream(cookies)
                .filter(x->x.getName().equals("session_id")|| x.getName().equals("csrf_token"))
                .collect(Collectors.toMap(Cookie::getName, Cookie::getValue));


    @RequestMapping(value = "/api/public/post/{id}", method = POST)
    @ResponseBody
    public String post(@PathVariable String id, @RequestBody String asd){
        logger.info("dentro al post "+asd);
        Integer i;
        try{
            i = Integer.getInteger(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "asddsa";
    }


    @PostMapping(value = "/api/public/post/random/")
    @ResponseBody
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<List<String>> getRandomPosts(){
        List<Post> rp = this.postService.getRandomPosts(this.RANDOM_POST);
        //logger.info(rp.toString());
        return new ResponseEntity<>(this.fromObjectsToStringList(rp),HttpStatus.OK);
    }


    @PostMapping(value = "/api/private/post/like/{idPost}/")
    @ResponseBody
    @CrossOrigin(origins = "http://localhost:3000", methods = POST, allowCredentials = "true")
    public ResponseEntity<String> isPostLikedToUser(HttpServletRequest request, @PathVariable int idPost,@RequestBody String idUser){
        Map<String,String> cookie = this.extractCookies.apply(request.getCookies());
        logger.info("cookie info: "+cookie.toString());
        int idu = this.extractIdUserFromString(idUser);
        if(!this.userSessionChecker.checkSession(cookie.get("session_id"),cookie.get("csrf_token"), idu)){
            return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
        }else{
            //response.setHeader(HttpHeaders.SET_COOKIE,this.cookieCreator.createCookie(new String[]{"csrf_token",this.userSessionChecker.generateNewToken(idu)},true).toString());
            return this.postService.isLikedToUser(idu,idPost) ? new ResponseEntity<>("yes",HttpStatus.OK) : new ResponseEntity<>("no",HttpStatus.OK);
        }
    }

    private int extractIdUserFromString(String idUser) {
        Type type = new TypeToken<Map<String, String>>(){}.getType();
        Map<String,String> m = new Gson().fromJson(idUser, type);
        return Integer.parseInt(m.get("idUser"));
    }

    @PostMapping(value = "/api/private/post/dislike/{idPost}/")
    @ResponseBody
    @CrossOrigin(origins = "http://localhost:3000", methods = POST, allowCredentials = "true")
    public ResponseEntity<String> isPostDislikedToUser(@PathVariable int idPost,HttpServletRequest request,@RequestBody String idUser){
        Map<String,String> cookie = this.extractCookies.apply(request.getCookies());
        logger.info("cookie info: "+cookie.toString());
        int idu = this.extractIdUserFromString(idUser);
        if(!this.userSessionChecker.checkSession(cookie.get("session_id"),cookie.get("csrf_token"), idu)){
            return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
        }else{
            //response.setHeader(HttpHeaders.SET_COOKIE,this.cookieCreator.createCookie(new String[]{"csrf_token",this.userSessionChecker.generateNewToken(idu)},true).toString());
            return this.postService.isDislikedToUser(idu,idPost) ? new ResponseEntity<>("yes",HttpStatus.OK) : new ResponseEntity<>("no",HttpStatus.OK);
        }


    }


        private List<String> fromObjectsToStringList(List<Post> list){
        Gson gson = new Gson();
        List<String> stringLists = new ArrayList<>();
        for(Post p : list){
            String json = gson.toJson(p);
            stringLists.add(json);
        }
        logger.info("fromObjectsToStringList: "+ stringLists);
        return stringLists;
    }


    @PostMapping(value = "api/private/post/like/add/{idPost}/")
    @CrossOrigin(origins = "http://localhost:3000", methods = POST, allowCredentials = "true")
    @ResponseBody
    public ResponseEntity<Void> addLikeToPost(HttpServletRequest request, @PathVariable int idPost,@RequestBody String idUser){
        Map<String,String> cookie = this.extractCookies.apply(request.getCookies());
        int idu = this.extractIdUserFromString(idUser);
        if(!this.userSessionChecker.checkSession(cookie.get("session_id"),cookie.get("csrf_token"), idu)){
            return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
        }else {
            if (!this.postService.isLikedToUser(idu, idPost) && !this.postService.isDislikedToUser(idu, idPost)){
                this.postService.addLikeToPost(idu,idPost);
                return new ResponseEntity<>(HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

        }
    }



    @PostMapping(value = "api/private/post/dislike/add/{idPost}/")
    @CrossOrigin(origins = "http://localhost:3000", methods = POST, allowCredentials = "true")
    @ResponseBody
    public ResponseEntity<Void> addDislikeToPost(HttpServletRequest request, @PathVariable int idPost,@RequestBody String idUser){
        Map<String,String> cookie = this.extractCookies.apply(request.getCookies());
        int idu = this.extractIdUserFromString(idUser);
        if(!this.userSessionChecker.checkSession(cookie.get("session_id"),cookie.get("csrf_token"), idu)){
            return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
        }else {
            if (!this.postService.isLikedToUser(idu, idPost) && !this.postService.isDislikedToUser(idu, idPost)){
                this.postService.addDislikeToPost(idu,idPost);
                return new ResponseEntity<>(HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

        }
    }

    @PostMapping(value = "api/private/post/like/remove/{idPost}/")
    @CrossOrigin(origins = "http://localhost:3000", methods = POST, allowCredentials = "true")
    @ResponseBody
    public ResponseEntity<Void> removeLikeFromPost(HttpServletRequest request, @PathVariable int idPost,@RequestBody String idUser){
        Map<String,String> cookie = this.extractCookies.apply(request.getCookies());
        int idu = this.extractIdUserFromString(idUser);
        if(!this.userSessionChecker.checkSession(cookie.get("session_id"),cookie.get("csrf_token"), idu)){
            return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
        }else {
            if(this.postService.isLikedToUser(idu, idPost)){
                this.postService.removeLikeToPost(idu,idPost);
                return new ResponseEntity<>(HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

        }
    }


    @PostMapping(value = "api/private/post/dislike/remove/{idPost}/")
    @CrossOrigin(origins = "http://localhost:3000", methods = POST, allowCredentials = "true")
    @ResponseBody
    public ResponseEntity<Void> removeDislikeFromPost(HttpServletRequest request, @PathVariable int idPost,@RequestBody String idUser){
        Map<String,String> cookie = this.extractCookies.apply(request.getCookies());
        int idu = this.extractIdUserFromString(idUser);
        if(!this.userSessionChecker.checkSession(cookie.get("session_id"),cookie.get("csrf_token"), idu)){
            return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
        }else {
            if(this.postService.isDislikedToUser(idu, idPost)){
                this.postService.removeDislikeToPost(idu,idPost);
                return new ResponseEntity<>(HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
    }


    @ResponseBody
    @PostMapping(value = "api/private/post/add/")
    @CrossOrigin(origins = "http://localhost:3000", methods = POST, allowCredentials = "true")
    public ResponseEntity<Void> addPost(HttpServletRequest request, @RequestBody Map<String, Post> body){
        Map<String,String> cookie = this.extractCookies.apply(request.getCookies());
        logger.info(cookie.toString());
        logger.info("post: "+body.toString());
        if(!this.userSessionChecker.checkSession(cookie.get("session_id"),cookie.get("csrf_token"), body.get("post").getId_author())){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }else{
            this.postService.createPost(body.get("post"));
            return new ResponseEntity<>(HttpStatus.OK);
        }

    }


    @PostMapping(value = "api/public/post/search/")
    @CrossOrigin(origins = "http://localhost:3000", methods = POST)
    @ResponseBody
    public ResponseEntity<List<String>> searchPostByTitleAndText(@RequestBody String value){
        return new ResponseEntity<>(this.fromObjectsToStringList(this.postService.searchByTitleAndText(this.extractValueFromStringvalue(value))),HttpStatus.OK);
    }

    private String extractValueFromStringvalue(String value) {
        Type type = new TypeToken<Map<String, String>>(){}.getType();
        Map<String,String> m = new Gson().fromJson(value, type);
        return m.get("value");
    }


}
