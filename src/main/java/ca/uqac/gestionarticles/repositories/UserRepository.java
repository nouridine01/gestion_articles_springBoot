package ca.uqac.gestionarticles.repositories;

import ca.uqac.gestionarticles.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserRepository extends JpaRepository<User,Long> {
    public User findByLogin(String login);

    @Query("select u from User u where u.login like :x")
    public Page<User> chercher(@Param("x") String mc, Pageable pageable);

    @Query("select u from User u where u.lastName like :x")
    public Page<Object> rechercher(@Param("x") String mc, Pageable pageable);
}
