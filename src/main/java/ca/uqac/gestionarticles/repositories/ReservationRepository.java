package ca.uqac.gestionarticles.repositories;

import ca.uqac.gestionarticles.entities.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    @Query("select r from Reservation r where r.satisfaite=false")
    public Page<Reservation> reservationEnCours(Pageable pageable);

    @Query("select r from Reservation r where r.client.id = :x")
    public Page<Reservation> mesReservations(@Param("x") Long id,Pageable pageable);
}
