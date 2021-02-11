package ca.uqac.gestionarticles.controllers;

import ca.uqac.gestionarticles.entities.User;
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

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/users")
    public String index(Model model, @RequestParam(name = "page",defaultValue = "0") int page,
                        @RequestParam(name = "mc",defaultValue = "") String mc,
                        @RequestParam(name = "size",defaultValue = "5")int size) {
        Page<User> liste =userRepository.chercher("%"+mc+"%", PageRequest.of(page, size) );
        int[] pages = new int[liste.getTotalPages()];
        model.addAttribute("listes", liste.getContent());
        model.addAttribute("pages", pages);
        model.addAttribute("size", size);
        model.addAttribute("pageCourante", page);
        model.addAttribute("mc", mc);
        //retourne la vue employees.html
        return "users/users";
    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
    public String delete(Long id,String mc,String page,String size) {
        userRepository.deleteById(id);
        return "redirect:/users?page="+page+"&mc="+mc+"&size="+size;
    }

    @RequestMapping(value = "/detailUser", method = RequestMethod.GET)
    public String detail(Model model,Long id) {
        model.addAttribute("user",userRepository.findById(id).get());
        return "users/detail";
    }



    @RequestMapping(value = "/createUser", method = RequestMethod.GET)
    public String form (Model model) {
        model.addAttribute("user", new User());
        return "users/form";
    }

    @RequestMapping(value = "/saveUser", method = RequestMethod.POST)
    public String save (Model model , @Valid User user, BindingResult br, HttpServletRequest request) {

        if(br.hasErrors()) {
            model.addAttribute("user",user);
            return "users/form";
        }


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
       userRepository.save(user);
        model.addAttribute("user",user);
        return "users/detail";
    }
}
