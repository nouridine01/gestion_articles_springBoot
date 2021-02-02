package ca.uqac.gestionarticles.repositories;



import ca.uqac.gestionarticles.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

public interface UserRepository extends JpaRepository<User,Long> {
    public User findByLogin(String login);
    public Page<User> findByPays(String pays, Pageable pageable);
}
