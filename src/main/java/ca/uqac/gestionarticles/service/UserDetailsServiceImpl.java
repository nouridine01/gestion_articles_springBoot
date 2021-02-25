package ca.uqac.gestionarticles.service;



import ca.uqac.gestionarticles.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    AccountService accountService;
    //c'est cette methode que le filtre de spring(spring security FilterChain) appel tjrs une fois qu'il recoit un login pour effectuer la verification
    // donc nous allons la redefinir pour dire à spring comment chager le user et ses roles
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = accountService.findUserByLogin(login);
        if(user==null) throw new UsernameNotFoundException("user");

        //pour spring security  les roles d'un user  est une collections de type GrantedAuthority donc il faut adapter les roles du user
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(r->{
            authorities.add(new SimpleGrantedAuthority(r.getRole()));
        });

        //retourner un objet User de Spring Security qui n'est pas notre Class user
        return new org.springframework.security.core.userdetails.User(user.getLogin(),user.getPassword(),authorities);

    }
}
