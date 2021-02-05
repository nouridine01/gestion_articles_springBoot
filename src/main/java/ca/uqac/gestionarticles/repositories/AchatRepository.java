package ca.uqac.gestionarticles.repositories;

import ca.uqac.gestionarticles.entities.Achat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AchatRepository extends JpaRepository<Achat,Long> {
}
