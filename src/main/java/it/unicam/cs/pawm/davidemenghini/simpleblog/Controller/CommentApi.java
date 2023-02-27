package it.unicam.cs.pawm.davidemenghini.simpleblog.Controller;

import com.google.gson.Gson;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.Comment;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.service.CommentService;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.service.UserSessionChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@CrossOrigin(origins = "http://localhost:3000/")
public class CommentApi {

    private static final Logger logger = LoggerFactory.getLogger(CommentApi.class);
    @Autowired
    private CommentService commentService;


    @Autowired
    private UserSessionChecker userSessionChecker;

    @RequestMapping(value = "/api/public/comment/{id}/")
    @ResponseBody
    public ResponseEntity<List<String>> getCommentFromId(@PathVariable String id){
        List<String> jsonList = this.fromObjectsToStringList(this.commentService.getCommentFromIdPost(Integer.parseInt(id)));
        return new ResponseEntity<>(jsonList,HttpStatus.OK);
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



    @PostMapping(value = "/api/private/comment/like/{idUser}")
    @ResponseBody
    public ResponseEntity<String> isPostLikedToUser(@CookieValue("session_id") String session_id,@CookieValue("csrf_cookie") String csrf_cookie,@PathVariable int idUser){
        if(!this.userSessionChecker.checkSession(session_id,csrf_cookie, p.getId_author())){
            return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
        }
        return this.commentService.isLikedToUser(idUser) ? new ResponseEntity<>("yes",HttpStatus.OK) : new ResponseEntity<>("no",HttpStatus.OK);
    }

    @PostMapping(value = "/api/private/comment/dislike/{idUser}")
    @ResponseBody
    public ResponseEntity<String> isPostDislikedToUser(@CookieValue("session_id") String session_id,@CookieValue("csrf_cookie") String csrf_cookie,@PathVariable int idUser){
        if(!this.userSessionChecker.checkSession(session_id,csrf_cookie, p.getId_author())){
            return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
        }
        return this.commentService.isDislikedToUser(idUser) ? new ResponseEntity<>("yes",HttpStatus.OK) : new ResponseEntity<>("no",HttpStatus.OK);
    }

}
