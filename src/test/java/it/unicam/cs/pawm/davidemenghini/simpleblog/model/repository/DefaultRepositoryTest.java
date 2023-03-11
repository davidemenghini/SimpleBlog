package it.unicam.cs.pawm.davidemenghini.simpleblog.model.repository;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.Comment;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.DefaultUser;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.repository.DefaultCommentCrudRepository;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.repository.DefaultPostCrudRepository;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.Post;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.repository.DefaultUserCrudRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DefaultRepositoryTest {


    @Autowired
    private DefaultPostCrudRepository postRepo;

    @Autowired
    private DefaultUserCrudRepository userRepo;

    @Autowired
    private DefaultCommentCrudRepository commentRepo;


    @Test
    public void getPostByIdTest(){
        Post p = postRepo.findById(1).orElse(null);
        assertNotNull(p);
        Post expectedPost = new Post();
        Post p1 = this.defaultSetterPost(expectedPost);
        assertEquals(p1,p);
    }

    @Test
    public void getUserByUsernameTest(){
        DefaultUser expectedUser = this.getExpectedUser();
        DefaultUser actualUser = this.userRepo.findDefaultUserByUsername("utente1");
        assertNotNull(actualUser);
        //controllo l'id
        assertEquals(expectedUser.getId(),actualUser.getId());
        //controllo l'username
        assertEquals(expectedUser.getUsername(),actualUser.getUsername());
    }

    @Test
    public void getRandomPostsTest(){
        List<Post> actual = this.postRepo.readRandomPost(1);
        assertEquals(1,actual.size());
        actual = this.postRepo.readRandomPost(2);
        assertEquals(2,actual.size());
    }

    @Test
    public void getCommentByIdpTest(){
        List<Comment> expected = this.getExpectedCommentFromPostOne();
        List<Comment> actual = this.commentRepo.findCommentsByByidPost(1);
        assertEquals(expected,actual);
    }



    private Post defaultSetterPost(Post expectedPost) {
        expectedPost.setId(1);
        byte[] data_text = "Test".getBytes();
        expectedPost.setDataText(data_text);
        byte[] data_img = new byte[0];
        expectedPost.setData_img(data_img);
        expectedPost.setId_author(1);
        byte[] title_text = "ciao questa è una prova.".getBytes(StandardCharsets.UTF_8);
        expectedPost.setTitleText(title_text);
        expectedPost.setLikeNumber(1);
        expectedPost.setDislikeNumber(1);
        return expectedPost;
    }

    private List<Comment> getExpectedCommentFromPostOne(){
        Comment c1 = new Comment();
        c1.setId(1);
        c1.setTextComment("commento1".getBytes());
        c1.setLike_number(1);
        c1.setDislike_number(1);
        c1.setIdAuthor(1);
        c1.setIdPost(1);
        Comment c2 = new Comment();
        c2.setId(2);
        c2.setTextComment("commento2".getBytes());
        c2.setLike_number(0);
        c2.setDislike_number(0);
        c2.setIdAuthor(1);
        c2.setIdPost(1);
        return List.of(c1,c2);
    }

    private DefaultUser getExpectedUser(){
        DefaultUser user = new DefaultUser();
        user.setRole_user("user");
        user.setEnabled(0);
        user.setId(1);
        user.setData_img("null".getBytes());
        user.setPsw("683ca0111aee25516ac67f7f3d8d2717f6c075940ec2ceaa8f4d251f491d9adf");
        user.setUsername("utente1");
        user.setSalt("pippo");
        user.setSession_id(null);
        user.setExpiration_time(null);
        return user;
    }



}
