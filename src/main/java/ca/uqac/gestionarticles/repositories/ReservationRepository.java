package ca.uqac.gestionarticles.repositories;


import ca.uqac.gestionarticles.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
}
