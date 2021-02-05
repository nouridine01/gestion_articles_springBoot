package ca.uqac.gestionarticles.repositories;



import ca.uqac.gestionarticles.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;



public interface RoleRepository extends JpaRepository<Role,Long> {
    public Role findByRole(String role);
}
