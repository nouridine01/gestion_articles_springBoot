package ca.uqac.gestionarticles.entities;



import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    @OneToMany(fetch= FetchType.LAZY)
    @JoinColumn(name="categorie_id")
    private List<Article> articles= new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    @PreRemove
    public void checkArticleAssociationBeforeRemoval() {
        if (!this.articles.isEmpty()) {
            throw new RuntimeException("Vous ne pouvez pas supprimer une catégorie qui a des articles.");
        }
    }
}
