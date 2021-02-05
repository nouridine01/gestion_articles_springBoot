package ca.uqac.gestionarticles.repositories;

import ca.uqac.gestionarticles.entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article,Long> {
}
