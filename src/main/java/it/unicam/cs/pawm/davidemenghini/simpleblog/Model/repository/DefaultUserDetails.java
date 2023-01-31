package it.unicam.cs.pawm.davidemenghini.simpleblog.Model.repository;
import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence.DefaultUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class DefaultUserDetails implements UserDetails {


    private final DefaultUser user;

    public DefaultUserDetails(DefaultUser user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(this.user.getRole_user());
        return List.of(authority);
    }

    @Override
    public String getPassword() {
        return this.user.getPsw();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
