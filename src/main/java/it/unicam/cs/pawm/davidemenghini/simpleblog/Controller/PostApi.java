package it.unicam.cs.pawm.davidemenghini.simpleblog.Controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Configuration.SecurityConfig;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.Post;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.repository.DefaultPostCrudRepository;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.repository.DefaultUserCrudRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class PostApi{

    private Post p;

    private final int RANDOM_POST = 2;

    private static final Logger logger = LoggerFactory.getLogger(PostApi.class);

    @Autowired
    private DefaultPostCrudRepository postRepo;

    @Autowired
    private DefaultUserCrudRepository userRepo;


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
    public ResponseEntity<String> AddComment(@CookieValue("session_id") String session_id, @RequestBody Post post){
        int idUser = Objects.requireNonNull(this.userRepo.findById(post.getId_author()).orElse(null)).getId();
        if (idUser != Integer.getInteger(session_id)){
            return new ResponseEntity<>("Unauthorized",HttpStatus.UNAUTHORIZED);
        }
        this.p = post;
        this.postRepo.save(p);
        return new ResponseEntity<>("",HttpStatus.OK);
    }


    @PostMapping(value = "/api/public/post/random/")
    @ResponseBody
    public List<Post> getRandomComment(){
        return this.getRandomPosts();
    }


    private List<Post> getRandomPosts(){
        List<Post> l = new ArrayList<>();
        Pageable firstPageWithTwoElements = PageRequest.of(0, this.RANDOM_POST);
        this.postRepo.findAll(firstPageWithTwoElements).forEach(l::add);
        l.forEach(System.out::println);
        return l;
    }



}
