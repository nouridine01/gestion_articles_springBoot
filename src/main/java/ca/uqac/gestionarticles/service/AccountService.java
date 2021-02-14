package ca.uqac.gestionarticles.service;


import ca.uqac.gestionarticles.entities.Role;
import ca.uqac.gestionarticles.entities.User;

public interface AccountService {
    public User saveUser(User user);
    public Role saveRole(Role role);
    public void addRoleToUser(String userName, String role);
    public User findUserByLogin(String login);
}
