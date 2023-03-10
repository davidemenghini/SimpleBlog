package it.unicam.cs.pawm.davidemenghini.simpleblog.Controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.Comment;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.service.CommentService;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.service.CookieCreator;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.service.UserSessionChecker;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@CrossOrigin(origins = "http://localhost:3000/",allowedHeaders = "*")
public class CommentApi {

    private static final Logger logger = LoggerFactory.getLogger(CommentApi.class);
    @Autowired
    private CommentService commentService;


    @Autowired
    private UserSessionChecker userSessionChecker;

    private final CookieCreator cookieCreator = CookieCreator::cookie;



    private final Function<Cookie[], Map<String,String>> extractCookies = (cookies) -> Arrays.stream(cookies)
            .filter(x->x.getName().equals("session_id"))
            .collect(Collectors.toMap(Cookie::getName, Cookie::getValue));

    @RequestMapping(value = "/api/public/comment/{id}/")
    @ResponseBody
    public ResponseEntity<List<String>> getCommentFromId(@PathVariable String id){
        List<String> jsonList = this.fromObjectsToStringList(this.commentService.getCommentFromIdPost(Integer.parseInt(id)));
        return new ResponseEntity<>(jsonList,HttpStatus.OK);
    }

    @PostMapping(value = "/api/private/comment/add/")
    @ResponseBody
    @CrossOrigin(origins = "http://localhost:3000", methods = POST, allowCredentials = "true",allowedHeaders = "*")
    public ResponseEntity<Void> createComment(HttpServletRequest request, @RequestBody Map<String,Comment> body){
        Map<String,String> cookie = this.extractCookies.apply(request.getCookies());
        logger.info("body: "+body.get("comment"));
        if(!this.userSessionChecker.checkSession(cookie.get("session_id"),request.getHeader("csrf_token"), body.get("comment").getIdAuthor())){
            return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
        }else{
            this.commentService.createNewComment(body.get("comment"));
            return new ResponseEntity<>(null,HttpStatus.OK);
        }
    }

    private Comment extractCommentFromJson(String body) {
        Comment m = new Gson().fromJson(body, Comment.class);
        Comment c = new Comment();
        c.setId(this.generateIdComment());
        c.setLike_number(0);
        c.setDislike_number(0);
        c.setIdPost(m.getIdPost());
        c.setTextComment(m.getTextComment());
        return c;
    }

    private int generateIdComment(){
        Random random=new Random();
        int id;
        do{
            id=random.nextInt(30000);
        }while (this.checkIfIdCommentExists(id));
        return id;
    }

    private boolean checkIfIdCommentExists(int id) {
        return this.commentService.checkIfIdCommentExist(id);
    }


    private List<String> fromObjectsToStringList(List<Comment> list){
        Gson gson = new Gson();
        List<String> stringLists = new ArrayList<>();
        for(Comment c : list){
            String json = gson.toJson(c);
            stringLists.add(json);
        }
        logger.info("json comment: "+ stringLists);
        return stringLists;
    }



    @PostMapping(value = "/api/private/comment/like/{idComment}/")
    @ResponseBody
    @CrossOrigin(origins = "http://localhost:3000", methods = POST, allowCredentials = "true",allowedHeaders = "*")
    public ResponseEntity<String> isCommentLikedToUser(HttpServletRequest request,@PathVariable int idComment,@RequestBody String idUser){
        Map<String,String> cookie = this.extractCookies.apply(request.getCookies());
        int idu = this.extractIdUserFromString(idUser);
        if(!this.userSessionChecker.checkSession(cookie.get("session_id"),request.getHeader("csrf_token"), idu)){
            return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
        }else{
            return this.commentService.isLikedToUser(idu,idComment) ?
                    new ResponseEntity<>("yes",HttpStatus.OK) :
                    new ResponseEntity<>("no",HttpStatus.OK);
        }

    }

    @PostMapping(value = "/api/private/comment/dislike/{idComment}/")
    @ResponseBody
    @CrossOrigin(origins = "http://localhost:3000", methods = POST, allowCredentials = "true",allowedHeaders = "*")
    public ResponseEntity<String> isCommentDislikedToUser(HttpServletRequest request, @PathVariable int idComment,@RequestBody String idUser){
        Map<String,String> cookie = this.extractCookies.apply(request.getCookies());
        int idu = this.extractIdUserFromString(idUser);
        if(!this.userSessionChecker.checkSession(cookie.get("session_id"),request.getHeader("csrf_token"), idu)){
            return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
        }else {
            return this.commentService.isDislikedToUser(idu,idComment) ?
                    new ResponseEntity<>("yes", HttpStatus.OK) :
                    new ResponseEntity<>("no", HttpStatus.OK);
        }
    }




    private int extractIdUserFromString(String idUser) {
        Type type = new TypeToken<Map<String, String>>(){}.getType();
        Map<String,String> m = new Gson().fromJson(idUser, type);
        return Integer.parseInt(m.get("idUser"));
    }

    @PostMapping(value = "api/private/comment/like/add/{idComment}/")
    @CrossOrigin(origins = "http://localhost:3000", methods = POST, allowCredentials = "true",allowedHeaders = "*")
    @ResponseBody
    public ResponseEntity<Boolean> addLikeComment(HttpServletRequest request, @PathVariable int idComment,@RequestBody String idUser){
        Map<String,String> cookie = this.extractCookies.apply(request.getCookies());
        int idu = this.extractIdUserFromString(idUser);
        if(!this.userSessionChecker.checkSession(cookie.get("session_id"),request.getHeader("csrf_token"), idu)){
            return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);
        }else {
            if(!this.commentService.isLikedToUser(idu,idComment) && !this.commentService.isDislikedToUser(idu,idComment)){
                this.commentService.addLikeToUser(idu,idComment);
                return new ResponseEntity<>(true,HttpStatus.OK);
            }else{
                return new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
            }
        }
    }


    @PostMapping(value = "api/private/comment/dislike/add/{idComment}/")
    @CrossOrigin(origins = "http://localhost:3000", methods = POST, allowCredentials = "true",allowedHeaders = "*")
    @ResponseBody
    public ResponseEntity<Boolean> addDislikeComment(HttpServletRequest request, @PathVariable int idComment, @RequestBody String idUser){
        Map<String,String> cookie = this.extractCookies.apply(request.getCookies());
        int idu = this.extractIdUserFromString(idUser);
        if(!this.userSessionChecker.checkSession(cookie.get("session_id"),request.getHeader("csrf_token"), idu)){
            return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);
        }else {
            if(!this.commentService.isLikedToUser(idu,idComment) && !this.commentService.isDislikedToUser(idu,idComment)){
                this.commentService.addDislikeToUser(idu,idComment);
                return new ResponseEntity<>(true,HttpStatus.OK);
            }else{
                return new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
            }
        }
    }


    @PostMapping(value = "api/private/comment/dislike/remove/{idComment}/")
    @CrossOrigin(origins = "http://localhost:3000", methods = POST, allowCredentials = "true",allowedHeaders = "*")
    @ResponseBody
    public ResponseEntity<Boolean> removeDislikeComment(HttpServletRequest request, @PathVariable int idComment, @RequestBody String idUser){
        Map<String,String> cookie = this.extractCookies.apply(request.getCookies());
        int idu = this.extractIdUserFromString(idUser);
        if(!this.userSessionChecker.checkSession(cookie.get("session_id"),request.getHeader("csrf_token"), idu)){
            return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);
        }else {
            if(!this.commentService.isLikedToUser(idu,idComment) && this.commentService.isDislikedToUser(idu,idComment)){
                this.commentService.removeDislikeToUser(idu,idComment);
                return new ResponseEntity<>(true,HttpStatus.OK);
            }else{
                return new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
            }
        }
    }

    @PostMapping(value = "api/private/comment/like/remove/{idComment}/")
    @CrossOrigin(origins = "http://localhost:3000", methods = POST, allowCredentials = "true",allowedHeaders = "*")
    @ResponseBody
    public ResponseEntity<Boolean> removeLikeComment(HttpServletRequest request, @PathVariable int idComment,@RequestBody String idUser){
        Map<String,String> cookie = this.extractCookies.apply(request.getCookies());
        int idu = this.extractIdUserFromString(idUser);
        if(!this.userSessionChecker.checkSession(cookie.get("session_id"),request.getHeader("csrf_token"), idu)){
            return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);
        }else {
            if(this.commentService.isLikedToUser(idu,idComment) && !this.commentService.isDislikedToUser(idu,idComment)){
                this.commentService.removeLikeToUser(idu,idComment);
                return new ResponseEntity<>(true,HttpStatus.OK);
            }else{
                return new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
            }
        }
    }




}
