package ca.uqac.gestionarticles.controllers;

import ca.uqac.gestionarticles.entities.Client;
import ca.uqac.gestionarticles.entities.User;
import ca.uqac.gestionarticles.repositories.ClientRepository;
import ca.uqac.gestionarticles.repositories.RoleRepository;
import ca.uqac.gestionarticles.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;


    @RequestMapping(value = "/users")
    public String index(Model model, @RequestParam(name = "page",defaultValue = "0") int page,
                        @RequestParam(name = "mc",defaultValue = "") String mc,
                        @RequestParam(name = "size",defaultValue = "5")int size,
                        @RequestParam(name = "message",defaultValue = "")String msg) {
        Page<User> liste =userRepository.rechercher("%"+mc+"%", PageRequest.of(page, size) );
        int[] pages = new int[liste.getTotalPages()];
        model.addAttribute("listes", liste.getContent());
        model.addAttribute("pages", pages);
        model.addAttribute("size", size);
        model.addAttribute("pageCourante", page);
        if(!("".equals(msg))) model.addAttribute("message", msg);
        model.addAttribute("mc", mc);
        //retourne la vue employees.html
        return "users/users";
    }


    @RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
    public String delete(Long id,String mc,String page,String size) {
        String msg="";
        try{
            userRepository.deleteById(id);
        }catch (Exception e){
            msg="impossible de supprimer cet utilisateur";
        }
        return "redirect:/users";
    }

    @RequestMapping(value = "/detailUser", method = RequestMethod.GET)
    public String detail(Model model,Long id) {
        model.addAttribute("user",userRepository.findById(id).get());
        return "users/detail";
    }



    @RequestMapping(value = "/createUser", method = RequestMethod.GET)
    public String form (Model model) {
    	model.addAttribute("listeRoles", roleRepository.findAll());
        model.addAttribute("user", new User());
        return "users/form";
    }

    @RequestMapping(value = "/saveUser", method = RequestMethod.POST)
    public String save (Model model , @Valid User user, @RequestParam(value = "roles", required = false) int[] roles, BindingResult br, HttpServletRequest request) {

        if(br.hasErrors()) {
        	model.addAttribute("listeRoles", roleRepository.findAll());
            model.addAttribute("user",user);
            return "users/form";
        }
        
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        
        if(roles == null)
            user.getRoles().add(roleRepository.findByRole("ROLE_CLIENT"));
        else
        	for(int i : roles)
        		user.getRoles().add(roleRepository.findById((long) i).get());

        userRepository.save(user);
        
        if(user.getRoles().contains(roleRepository.findByRole("ROLE_CLIENT"))) {
	        Client c = new Client();      
	        c.setUser(user);     
	        user.setClient(clientRepository.save(c));
        }
        
        model.addAttribute("user", userRepository.save(user));
        return "redirect:/detailUser?id=" + user.getId();
    }

    @RequestMapping(value = "/editUser", method = RequestMethod.GET)
    public String edit (Model model,Long id) {
    	model.addAttribute("listeRoles", roleRepository.findAll());
        model.addAttribute("user",userRepository.findById(id).get());
        return "users/edit";
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public String update (Model model , @Valid User user, BindingResult br, HttpServletRequest request) {
        User u = userRepository.findById(user.getId()).get();
        user.setRoles(u.getRoles());
        user.setClient(u.getClient());
        userRepository.save(user);
        model.addAttribute("user",user);
        return "redirect:/detailUser?id=" + user.getId();
    }

    /* @RequestMapping(value = "/recherche", method = RequestMethod.GET)
    public String recherche(Model model, @RequestParam(name = "page",defaultValue = "0") int page,
                            @RequestParam(name = "mc",defaultValue = "") String mc,
                            @RequestParam(name = "size",defaultValue = "5")int size
                            ) {
        Page<Object> liste =userRepository.rechercher("%"+mc+"%", PageRequest.of(page, size) );
        int[] pages = new int[liste.getTotalPages()];
        model.addAttribute("listes", liste.getContent());
        model.addAttribute("pages", pages);
        model.addAttribute("size", size);
        model.addAttribute("pageCourante", page);
        model.addAttribute("mc", mc);
        return "users/recherche";
    } */
}
