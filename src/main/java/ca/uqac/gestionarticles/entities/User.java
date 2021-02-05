package ca.uqac.gestionarticles.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String login;
    private String password;
    private String pays;
    private String lastName;
    private String firstName;
    private Boolean active;
    private String photo;
    @ManyToMany(fetch= FetchType.EAGER)
    private Collection<Role> roles=new ArrayList<>();

    public Collection<Role> getRoles() {
        return roles;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }


    //getters
    public long getId(){
        return this.id;
    }

    public String getPassword(){
        return this.password;
    }

    public String getLastName(){
        return this.lastName;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public Boolean getActive(){
        return this.active;
    }

    //setters
    public void setId(long id){
        this.id=id;
    }

    public void setPassword(String password){
        this.password=password;
    }

    public void setLastName(String lastName){
        this.lastName=lastName;
    }

    public void setFirstName(String firstName){
        this.firstName=firstName;
    }

    public void setActive(Boolean active){
        this.active=active;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }
}
