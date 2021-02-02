package ca.uqac.gestionarticles.repositories;



import ca.uqac.gestionarticles.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;


public interface RoleRepository extends JpaRepository<Role,Long> {
    public Role findByRole(String role);
}
