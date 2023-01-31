package it.unicam.cs.pawm.davidemenghini.simpleblog.Model.repository;

import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.DefaultUser;
import jakarta.persistence.Entity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Questa interfaccia permette di collegarsi al repository dell'utente.
 * Permette di eseguire operazioni CRUD per l'entità {@link DefaultUser}.
 */
@Repository
public interface DefaultUserCrudRepository extends CrudRepository<DefaultUser,Integer> {


    DefaultUser findDefaultUserByUsername(String username);



}
