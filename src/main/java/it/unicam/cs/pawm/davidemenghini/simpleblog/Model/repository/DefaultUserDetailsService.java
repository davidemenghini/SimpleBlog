package it.unicam.cs.pawm.davidemenghini.simpleblog.Model.repository;

import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.DefaultUser;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.repository.DefaultUserCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class DefaultUserDetailsService implements UserDetailsService {

    @Autowired
    private DefaultUserCrudRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        DefaultUser user = this.userRepo.findDefaultUserByUsername(username);
        if(user==null){
            throw new UsernameNotFoundException("Username non trovato.");
        }
        return new DefaultUserDetails(user);
    }
}
