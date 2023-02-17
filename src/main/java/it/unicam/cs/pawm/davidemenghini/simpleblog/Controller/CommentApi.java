package it.unicam.cs.pawm.davidemenghini.simpleblog.Controller;

import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.Comment;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@CrossOrigin(origins = "http://localhost:3000/")
public class CommentApi {

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/api/public/comment/{id}")
    @ResponseBody
    public ResponseEntity<List<Comment>> getCommentFromId(@PathVariable String id){
        return new ResponseEntity<>(this.commentService.getCommentFromIdPost(Integer.parseInt(id)),HttpStatus.OK);
    }

}
