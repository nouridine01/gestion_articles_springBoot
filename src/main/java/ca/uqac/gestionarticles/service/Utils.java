package ca.uqac.gestionarticles.service;

import ca.uqac.gestionarticles.entities.Client;
import ca.uqac.gestionarticles.entities.User;
import ca.uqac.gestionarticles.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Service
public class Utils implements  UtilsInterface{
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserRepository userRepository;

    public Client getClient(){
        return userRepository.findByLogin(accountService.getCurrentUserLogin()).getClient();
    }

    public User getUser(){
        return userRepository.findByLogin(accountService.getCurrentUserLogin());
    }

    public java.sql.Date getDate() {
        LocalDate d = new Date().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        return java.sql.Date.valueOf(d);
    }
}
