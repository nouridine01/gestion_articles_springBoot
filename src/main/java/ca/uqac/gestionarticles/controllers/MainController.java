package ca.uqac.gestionarticles.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ca.uqac.gestionarticles.entities.User;

@Controller
public class MainController implements ErrorController {

	@GetMapping("/")
	public String accueil(Model model) {
		return "accueil";
	}
	
	@RequestMapping(value = "/login")
	public String login(HttpServletRequest request, Model model) {
		
		HttpSession session = request.getSession(false);
        String errorMessage = null;
        if (session != null) {
            AuthenticationException ex = (AuthenticationException) session
                    .getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            if (ex != null) {
            	errorMessage = ex.getMessage();
            }
        }
        
        model.addAttribute("errorMessage", errorMessage);
		
		return "login";
	}
	
	@RequestMapping(value = "/inscription", method = RequestMethod.GET)
    public String form (Model model) {
        model.addAttribute("user", new User());
        return "users/form";
    }
	
	@RequestMapping("/error")
	public String handleError() {
		return "error";
	}
	
	@Override
	public String getErrorPath() {
        return null;
    }
}
