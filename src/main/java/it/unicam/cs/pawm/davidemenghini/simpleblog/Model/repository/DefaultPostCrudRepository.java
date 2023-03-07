package it.unicam.cs.pawm.davidemenghini.simpleblog.Model.repository;

import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.Post;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Questa interfaccia definisce un repository di tipo CRUD (Create,Read,Update,Delete) e "paging e sorting" per l'entità {@link Post}.
 */
@Repository
public interface DefaultPostCrudRepository extends CrudRepository<Post,Integer>, PagingAndSortingRepository<Post,Integer> {


    @Query(value = "select * from simple_blog.post order by RAND() limit ?1",nativeQuery = true)
    List<Post> readRandomPost(int randomPost);



    //void saveNewEntity(Post newPost);
}
