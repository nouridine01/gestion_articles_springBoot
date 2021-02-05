package ca.uqac.gestionarticles.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private Date date;
    private Date date_remise;
    private Boolean satisfaite;
    private int quantity;
    @ManyToOne
    Article article;
    @ManyToOne
    Client client;

    public Date getDate_remise() {
        return date_remise;
    }

    public void setDate_remise(Date date_remise) {
        this.date_remise = date_remise;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getSatisfaite() {
        return satisfaite;
    }

    public void setSatisfaite(Boolean satisfaite) {
        this.satisfaite = satisfaite;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
