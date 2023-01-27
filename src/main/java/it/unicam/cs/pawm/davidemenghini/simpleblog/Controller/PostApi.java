package it.unicam.cs.pawm.davidemenghini.simpleblog.Controller;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Configuration.SecurityConfig;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class PostApi{

    private Post p;

    private static final Logger logger = LoggerFactory.getLogger(PostApi.class);




    @RequestMapping(value = "/api/public/post/{id}", method = POST)
    @ResponseBody
    public String post(@PathVariable String id, @RequestBody String asd){
        logger.info("dentro al post "+asd);
        System.out.println("dsaasdasdadsdas");
        Integer i;
        try{
            i = Integer.getInteger(id);
        }catch (Exception e){
            System.out.println("noooo..");
        }
        return "asddsa";
    }



}
