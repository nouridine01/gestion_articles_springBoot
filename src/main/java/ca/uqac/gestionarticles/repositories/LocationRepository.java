package ca.uqac.gestionarticles.repositories;

import ca.uqac.gestionarticles.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location,Long> {
}
