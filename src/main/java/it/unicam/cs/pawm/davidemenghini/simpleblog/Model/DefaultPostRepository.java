package it.unicam.cs.pawm.davidemenghini.simpleblog.Model;

import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DefaultPostRepository extends CrudRepository<Post,Integer> {

}
