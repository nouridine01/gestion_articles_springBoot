package ca.uqac.gestionarticles.controllers;

import ca.uqac.gestionarticles.entities.Article;
import ca.uqac.gestionarticles.entities.Categorie;
import ca.uqac.gestionarticles.entities.User;
import ca.uqac.gestionarticles.repositories.ArticleRepository;
import ca.uqac.gestionarticles.repositories.CategorieRepository;
import ca.uqac.gestionarticles.repositories.RoleRepository;
import ca.uqac.gestionarticles.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CategorieRepository categorieRepository;
    @Autowired
    private RoleRepository roleRepository;

    @RequestMapping(value = "/users")
    public String index(Model model, @RequestParam(name = "page",defaultValue = "0") int page,
                        @RequestParam(name = "mc",defaultValue = "") String mc,
                        @RequestParam(name = "size",defaultValue = "5")int size,
                        @RequestParam(name = "message",defaultValue = "")String msg) {
        Page<User> liste =userRepository.chercher("%"+mc+"%", PageRequest.of(page, size) );
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
        return "redirect:/users?page="+page+"&mc="+mc+"&size="+size+"message="+msg;
    }

    @RequestMapping(value = "/detailUser", method = RequestMethod.GET)
    public String detail(Model model,Long id) {
        model.addAttribute("user",userRepository.findById(id).get());
        return "users/detail";
    }



    @RequestMapping(value = "/createUser", method = RequestMethod.GET)
    public String form (Model model) {
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("user", new User());
        return "users/form";
    }

    @RequestMapping(value = "/saveUser", method = RequestMethod.POST)
    public String save (Model model , @Valid User user, BindingResult br, HttpServletRequest request) {

        if(br.hasErrors()) {
            model.addAttribute("roles", roleRepository.findAll());
            model.addAttribute("user",user);
            return "users/form";
        }

        user.getRoles().forEach(role ->{
            if (role.getRole().equals("ROLE_CLIENT"));
        });
        model.addAttribute("user", userRepository.save(user));
        return "users/detail";
    }

    @RequestMapping(value = "/editUser", method = RequestMethod.GET)
    public String edit (Model model,Long id) {
        model.addAttribute("user",userRepository.findById(id).get());
        return "users/edit";
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public String update (Model model , @Valid User user, BindingResult br, HttpServletRequest request) {
        User u = userRepository.findById(user.getId()).get();
        user.setRoles(u.getRoles());
        user.setClient(u.getClient());
        userRepository.save(user);
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("user",user);
        return "users/detail";
    }

    @RequestMapping(value = "/recherche", method = RequestMethod.GET)
    public String recherche(Model model, @RequestParam(name = "page",defaultValue = "0") int page,
                            @RequestParam(name = "mc",defaultValue = "") String mc,
                            @RequestParam(name = "size",defaultValue = "5")int size
                            ) {
        Page<User> listeu =userRepository.chercher("%"+mc+"%", PageRequest.of(page, size) );
        Page<Article> listea =articleRepository.chercher("%"+mc+"%", PageRequest.of(page, size) );
        Page<Categorie> listec =categorieRepository.chercher("%"+mc+"%", PageRequest.of(page, size) );
        List<Object> liste = new ArrayList<>();
        liste.addAll(listec.getContent());
        liste.addAll(listea.getContent());
        liste.addAll(listeu.getContent());
        model.addAttribute("listes", liste);
        model.addAttribute("mc", mc);
        return "users/recherche";
    }
}
