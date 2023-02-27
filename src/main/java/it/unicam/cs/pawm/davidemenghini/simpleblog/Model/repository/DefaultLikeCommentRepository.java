package it.unicam.cs.pawm.davidemenghini.simpleblog.Model.repository;

import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.LikeComment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DefaultLikeCommentRepository extends CrudRepository<LikeComment,Integer> {


    @Query(nativeQuery = true,value = "SELECT * from simple_blog.like_comment as lc WHERE lc.id_user=?1")
    LikeComment findByIdUser(int idUser);
}
