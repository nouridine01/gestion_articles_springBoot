package ca.uqac.gestionarticles.repositories;

import ca.uqac.gestionarticles.entities.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LocationRepository extends JpaRepository<Location,Long> {

    @Query("select l from Location l where l.client.id = :x")
    public Page<Location> mesLocations(@Param("x") Long id, Pageable pageable);


}
