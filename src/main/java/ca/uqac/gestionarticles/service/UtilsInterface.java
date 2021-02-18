package ca.uqac.gestionarticles.service;

import ca.uqac.gestionarticles.entities.Client;
import ca.uqac.gestionarticles.entities.User;

public interface UtilsInterface {
    public java.sql.Date getDate();
    public Client getClient();
    public User  getUser();
}
