package it.unicam.cs.pawm.davidemenghini.simpleblog.Model.repository;

import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.UserCsrfToken;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DefaultTokenRepository extends CrudRepository<UserCsrfToken,Integer> {

    @Query(nativeQuery = true,value = "insert into simple_blog.my_user_csrf_token values (idu=?1, token=?2)")
    @Modifying
    void insertToken(Integer idu, String token);
}
