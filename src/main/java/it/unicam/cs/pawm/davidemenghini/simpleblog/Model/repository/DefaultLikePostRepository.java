package it.unicam.cs.pawm.davidemenghini.simpleblog.Model.repository;

import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.LikeComment;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.LikePost;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DefaultLikePostRepository extends CrudRepository<LikePost,Integer> {

    @Query(nativeQuery = true,value = "SELECT * from simple_blog.like_post as lc WHERE lc.id_user=?1 and lc.id_post=?2")
    List<LikePost> findByIdUser(int idUser, int idPost);

    @Query(nativeQuery = true,value = "SELECT * from simple_blog.like_post as lc WHERE lc.id=?1 AND lc.id_post=?2 and lc.id_user=?3")
    LikePost existByIdAndIdPostAndIdUser(int id,int idPost,int idUser);


}
