package ca.uqac.gestionarticles.service;



import ca.uqac.gestionarticles.entities.Role;
import ca.uqac.gestionarticles.entities.User;
import ca.uqac.gestionarticles.repositories.RoleRepository;
import ca.uqac.gestionarticles.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountServiceImpl implements  AccountService {
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Override
    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return  userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String userName, String role) {
        User user = userRepository.findByLogin(userName);
        Role role1 = roleRepository.findByRole(role);
        user.getRoles().add(role1);
    }

    @Override
    public User findUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }
}
