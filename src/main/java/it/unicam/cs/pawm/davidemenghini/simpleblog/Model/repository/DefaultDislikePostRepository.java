package it.unicam.cs.pawm.davidemenghini.simpleblog.Model.repository;

import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.DislikePost;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DefaultDislikePostRepository extends CrudRepository<DislikePost,Integer> {


    @Query(nativeQuery = true,value = "SELECT * from simple_blog.dislike_post as lc WHERE lc.id_user=?1 and lc.id_post=?2 LIMIT 1")
    DislikePost findByIdUser(int idUser, int idPost);


    @Query(nativeQuery = true, value ="SELECT * from simple_blog.dislike_post as dp WHERE dp.id=?1 AND dp.id_post=?2 and dp.id_user=?3")
    DislikePost existByIdAndIdPostAndIdUser(int id,int idPost,int idUser);


}
