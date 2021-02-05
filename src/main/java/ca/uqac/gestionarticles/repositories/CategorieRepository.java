package ca.uqac.gestionarticles.repositories;

import ca.uqac.gestionarticles.entities.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieRepository extends JpaRepository<Categorie,Long> {
}
