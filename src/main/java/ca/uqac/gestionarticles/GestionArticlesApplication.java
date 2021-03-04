package ca.uqac.gestionarticles;

import ca.uqac.gestionarticles.entities.Client;
import ca.uqac.gestionarticles.entities.Role;
import ca.uqac.gestionarticles.entities.User;
import ca.uqac.gestionarticles.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class GestionArticlesApplication implements CommandLineRunner {

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private AccountService accountService;

	public static void main(String[] args) {
		SpringApplication.run(GestionArticlesApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder getEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Override
	public void run(String... args) throws Exception {

		/*System.out.println(bCryptPasswordEncoder.encode("passer123"));
		Role r1 = new Role();
		Role r2 = new Role();
		Role r3 = new Role();
		User user1 = new User();
		User user2 = new User();
		User user3 = new User();

		r1.setRole("ADMIN");
		r1 = accountService.saveRole(r1);
		user1.setLogin("admin");
		user1.setPassword("passer");
		user1.setFirstName("nouridine");
		user1.setLastName("oumarou");
		user1.setActive(true);
		user1.getRoles().add(r1);
		accountService.saveUser(user1);

		r2.setRole("CLIENT");
		accountService.saveRole(r2);
		user2.setLogin("client");
		user2.setPassword("passer");
		user2.setFirstName("nouridine");
		user2.setLastName("oumarou");
		user2.setActive(true);
		user2.getRoles().add(r2);
		user2.setClient(new Client());
		accountService.saveUser(user2);

		r3.setRole("VENDEUR");
		accountService.saveRole(r3);
		user3.setLogin("vendeur");
		user3.setPassword("passer");
		user3.setFirstName("nouridine");
		user3.setLastName("oumarou");
		user3.setActive(true);
		user3.getRoles().add(r3);
		accountService.saveUser(user3);*/


	}



}
