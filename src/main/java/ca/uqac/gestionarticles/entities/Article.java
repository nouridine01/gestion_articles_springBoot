package ca.uqac.gestionarticles.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    @ManyToOne(fetch = FetchType.EAGER)
    private Categorie categorie;
    private double prix;
    private int quantite;
    private User createBy;
    @OneToMany
    List<Reservation> reservationEnCours = new ArrayList<>();
    @OneToMany
    List<Achat> achatEffectues = new ArrayList<>();
    @OneToMany
    List<Location> locationEnCours = new ArrayList<>();

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

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public User getCreateBy() {
        return createBy;
    }

    public void setCreateBy(User createBy) {
        this.createBy = createBy;
    }

    public List<Reservation> getReservationEnCours() {
        return reservationEnCours;
    }

    public void setReservationEnCours(List<Reservation> reservationEnCours) {
        this.reservationEnCours = reservationEnCours;
    }

    public List<Achat> getAchatEffectues() {
        return achatEffectues;
    }

    public void setAchatEffectues(List<Achat> achatEffectues) {
        this.achatEffectues = achatEffectues;
    }

    public List<Location> getLocationEnCours() {
        return locationEnCours;
    }

    public void setLocationEnCours(List<Location> locationEnCours) {
        this.locationEnCours = locationEnCours;
    }
}
