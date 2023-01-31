package it.unicam.cs.pawm.davidemenghini.simpleblog.Model.repository;

import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.Comment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Questa interfaccia definisce un repository CRUD per l'entità {@link Comment}.
 */
@Repository
public interface DefaultCommentCrudRepository extends CrudRepository<Comment,Integer> {

    @Query(value = "select * from simple_blog.comment as c where c.idp=?1", nativeQuery = true)
    List<Comment> findCommentsByByidPost(Integer idPost);

}
