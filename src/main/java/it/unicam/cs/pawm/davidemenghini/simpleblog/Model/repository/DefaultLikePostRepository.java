package it.unicam.cs.pawm.davidemenghini.simpleblog.Model.repository;

import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.LikeComment;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.LikePost;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DefaultLikePostRepository extends CrudRepository<LikePost,Integer> {

    @Query(nativeQuery = true,value = "SELECT * from simple_blog.like_post as lc WHERE lc.id_user=?1")
    LikeComment findByIdUser(int idUser);
}
