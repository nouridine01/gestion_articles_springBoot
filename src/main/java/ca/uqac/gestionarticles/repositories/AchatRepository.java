package ca.uqac.gestionarticles.repositories;

import ca.uqac.gestionarticles.entities.Achat;
import ca.uqac.gestionarticles.entities.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AchatRepository extends JpaRepository<Achat,Long> {
    @Query("select a from Achat a where a.client.id = :x")
    public Page<Achat> mesAchats(@Param("x") Long id, Pageable pageable);
}

