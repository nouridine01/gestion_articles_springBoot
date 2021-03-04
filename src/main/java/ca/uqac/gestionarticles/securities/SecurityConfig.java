package ca.uqac.gestionarticles.securities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configurable
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
            //interface qui doit etre inplementé pour gerer l'auth à savoir chargement des user et de leurs roles
            UserDetailsService userDetailsService;
    @Autowired
            //a instancier dans une classe de config (ici dans la class principal) comme bean pour l'injecter car par defaut spring ne le fait pas
            BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //on ajoute {noop} pour eviter le hachage avant verification du mot de passe
        /*auth.inMemoryAuthentication().withUser("admin").password("{noop}passer123").roles("ADMIN").
                and().withUser("user").password("passer123").roles("user");*/

        //3 type de config inMerory jdbc et userDetails Service c'est ce dernier que nous allons utiliser pour une config plus personnaliser
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
    	
    	http.csrf().disable()
    		.authorizeRequests()
    			.antMatchers("/", "/login", "/inscription", "/saveUser" ,"/css/**").permitAll()
    			.antMatchers("/deleteArticle", "/createArticle", "/saveArticle", "/editArticle", "/updateArticle",
    					"/deleteCategorie", "/createCategorie", "/saveCategorie", "/editCategorie", "/updateCategorie",
    					"/users", "/createUser", "/deleteUser", "/editUser", "/updateUser").hasRole("ADMIN")
    			.antMatchers("/categories", "/detailCategorie", "/createUser", "/clients", "/deleteClient", 
    					"/satisfaireReservation", "/reservations", "/achats", "/createAchat", "/saveAchat",
    					"/locations", "/createLocation", "/saveLocation", "/detailUser",
    					"/modifierDateLocation", "/updateDateRetour").hasAnyRole("ADMIN", "VENDEUR")
    			.antMatchers("/mes*", "/reserver", "/saveReservation").hasRole("CLIENT")
    			.anyRequest().authenticated()
    			.and()
    		.formLogin()
    			.loginPage("/login")
    			.defaultSuccessUrl("/articles", true).permitAll()
    			.and()
    		.logout()
    			.logoutSuccessUrl("/login");
    }
}
