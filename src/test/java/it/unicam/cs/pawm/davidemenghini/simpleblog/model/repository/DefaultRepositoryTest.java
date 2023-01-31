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
import java.util.Arrays;
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
        assertEquals(expectedUser,actualUser);
    }

    @Test
    public void getCommentByIdpTest(){
        List<Comment> expected = this.getExpectedCommentFromPostOne();
        List<Comment> actual = this.commentRepo.findCommentsByByidPost(1);
        System.out.println(new String(actual.get(0).getTextComment(),StandardCharsets.UTF_8));
        System.out.println(new String(expected.get(0).getTextComment(),StandardCharsets.UTF_8));
        System.out.println(new String(actual.get(1).getTextComment(),StandardCharsets.UTF_8));
        System.out.println(new String(expected.get(1).getTextComment(),StandardCharsets.UTF_8));
        assertEquals(expected,actual);
    }



    private Post defaultSetterPost(Post expectedPost) {
        expectedPost.setId(1);
        byte[] data_text = "Test".getBytes();
        expectedPost.setData_text(data_text);
        expectedPost.setData_img("null".getBytes());
        expectedPost.setId_author(1);
        byte[] title_text = "ciao questa è una prova.".getBytes(StandardCharsets.UTF_8);
        expectedPost.setTitle_text(title_text);
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
        user.setPsw("prova1");
        user.setUsername("utente1");
        return user;
    }



}
