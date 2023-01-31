package it.unicam.cs.pawm.davidemenghini.simpleblog.Model.repository;

import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Questa interfaccia definisce un repository di tipo CRUD (Create,Read,Update,Delete) per l'entità {@link Post}.
 */
@Repository
public interface DefaultPostCrudRepository extends CrudRepository<Post,Integer> {

}
