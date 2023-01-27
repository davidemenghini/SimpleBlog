package it.unicam.cs.pawm.davidemenghini.simpleblog.model.repository;


import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.DefaultPostRepository;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.CrudRepository;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DefaultRepositoryTest {


    @Autowired
    private DefaultPostRepository repo;


    @Test
    public void getPostByIdTest(){
        Post p = repo.findById(1).orElse(null);
        assertNotNull(p);
        Post expectedPost = new Post();
        Post p1 = this.defaultSetterPost(expectedPost);
        System.out.println(new String(p.getTitle_text(),StandardCharsets.UTF_8).length());
        System.out.println(new String(p1.getTitle_text(),StandardCharsets.UTF_8).length());
        assertEquals(p1,p);
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
}
