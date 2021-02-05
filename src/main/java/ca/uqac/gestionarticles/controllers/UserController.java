package ca.uqac.gestionarticles.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @GetMapping(path = "/index")
    public String index(){
        return "index";
    }
}
