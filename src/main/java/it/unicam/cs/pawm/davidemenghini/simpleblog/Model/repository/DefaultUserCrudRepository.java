package it.unicam.cs.pawm.davidemenghini.simpleblog.Model.repository;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.DefaultUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Questa interfaccia permette di collegarsi al repository dell'utente.
 * Permette di eseguire operazioni CRUD per l'entità {@link DefaultUser}.
 */
@Repository
public interface DefaultUserCrudRepository extends CrudRepository<DefaultUser,Integer>, JpaRepository<DefaultUser,Integer> {


    DefaultUser findDefaultUserByUsername(String username);


    /**
     * Questo metodo serve per aggiornare lo stato online od offline nel repository.
     * @param session_id Il session id della sessione. In caso di logout {&egrave;} null.
     * @param enable 1 se l'utente ha effettuato il login, 0 se ha effettuato il logout.
     * @param id id dell'utente.
     * @return Il nuovo stato dell'utente.
     */
    @Modifying
    @Query(value = "update DefaultUser as u set u.session_id=?1, u.enabled=?2 where u.id=?3")
    int setEnableAndSession_idForUser(String session_id, int enable,int id);





}
