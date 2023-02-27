package it.unicam.cs.pawm.davidemenghini.simpleblog.Controller;
import com.google.gson.Gson;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.Post;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.service.PostService;
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

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@CrossOrigin(origins = "http://localhost:3000/")
public class PostApi{

    private Post p;

    private final int RANDOM_POST = 2;

    private static final Logger logger = LoggerFactory.getLogger(PostApi.class);

    @Autowired
    private UserSessionChecker userSessionChecker;

    @Autowired
    private PostService postService;


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

    @RequestMapping(value = "/api/private/post/add/{idPost}", method = POST)
    @ResponseBody
    public ResponseEntity<Post> AddComment(@CookieValue("session_id") String session_id,@CookieValue("csrf_cookie") String csrf_cookie, @RequestBody Post post){
        if(!this.userSessionChecker.checkSession(session_id,csrf_cookie, p.getId_author())){
            return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
        }
        this.postService.createPost(post);
        return new ResponseEntity<>(post,HttpStatus.OK);
    }


    @PostMapping(value = "/api/public/post/random/")
    @ResponseBody
    public ResponseEntity<List<String>> getRandomPosts(){
        List<Post> rp = this.postService.getRandomPosts(this.RANDOM_POST);
        //logger.info(rp.toString());
        return new ResponseEntity<>(this.fromObjectsToStringList(rp),HttpStatus.OK);

    }


    @PostMapping(value = "/api/private/post/like/{idUser}")
    @ResponseBody
    public ResponseEntity<String> isPostLikedToUser(@CookieValue("session_id") String session_id,@CookieValue("csrf_cookie") String csrf_cookie,@PathVariable int idUser){
        if(!this.userSessionChecker.checkSession(session_id,csrf_cookie, p.getId_author())){
            return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
        }
        return this.postService.isLikedToUser(idUser) ? new ResponseEntity<>("yes",HttpStatus.OK) : new ResponseEntity<>("no",HttpStatus.OK);
    }

    @PostMapping(value = "/api/private/post/dislike/{idUser}")
    @ResponseBody
    public ResponseEntity<String> isPostDislikedToUser(@CookieValue("session_id") String session_id,@CookieValue("csrf_cookie") String csrf_cookie,@PathVariable int idUser){
        if(!this.userSessionChecker.checkSession(session_id,csrf_cookie, p.getId_author())){
            return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
        }
        return this.postService.isDislikedToUser(idUser) ? new ResponseEntity<>("yes",HttpStatus.OK) : new ResponseEntity<>("no",HttpStatus.OK);
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





}
