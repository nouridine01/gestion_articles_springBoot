package ca.uqac.gestionarticles.controllers;


import ca.uqac.gestionarticles.entities.Achat;
import ca.uqac.gestionarticles.entities.Article;
import ca.uqac.gestionarticles.entities.Location;
import ca.uqac.gestionarticles.repositories.*;
import ca.uqac.gestionarticles.service.AccountService;
import ca.uqac.gestionarticles.service.Utils;
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
public class LocationController {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private Utils utils;
    @Autowired
    private LocationRepository locationRepository;
    
    @RequestMapping(value="/locations", method = RequestMethod.GET)
    public String locations (Model model, @RequestParam(name = "page",defaultValue = "0") int page,
            @RequestParam(name = "size",defaultValue = "5")int size) {
    	
    	Page<Location> liste = locationRepository.findAll(PageRequest.of(page, size));
    	int[] pages = new int[liste.getTotalPages()];

        model.addAttribute("listes", liste.getContent());
        model.addAttribute("pages", pages);
        model.addAttribute("size", size);
        model.addAttribute("pageCourante", page);
        return "locations/locations";
    }

    @RequestMapping(value = "/mesLocations", method = RequestMethod.GET)
    public String location (Model model, @RequestParam(name = "page",defaultValue = "0") int page,Long article_id,
                               @RequestParam(name = "size",defaultValue = "5")int size) {

        Page<Location> liste = locationRepository.mesLocations(utils.getClient().getId(),PageRequest.of(page,size));
        int[] pages = new int[liste.getTotalPages()];
        model.addAttribute("listes", liste.getContent());
        model.addAttribute("pages", pages);
        model.addAttribute("size", size);
        model.addAttribute("pageCourante", page);
        return "locations/meslocations";
    }
    
    @RequestMapping(value = "/RepousserDatelocation", method = RequestMethod.GET)
    public String location (Model model , Long id,Long article_id, HttpServletRequest request) {
        model.addAttribute("location_id",id);
        return "locations/form";
    }

    @RequestMapping(value = "/updateDateRetour", method = RequestMethod.POST)
    public String update (Model model , @Valid Location location,Long article_id, BindingResult br, HttpServletRequest request) {
        Location l=locationRepository.findById(location.getId()).get();
        l.setDate_retour(location.getDate_retour());
        locationRepository.save(l);
        model.addAttribute("location",location);
        return "locations/detail";
    }

    @RequestMapping(value = "/createLocation", method = RequestMethod.GET)
    public String form (Model model,Long article_id) {
        model.addAttribute("location", new Location());
        model.addAttribute("article_id",article_id);
        return "loactions/form";
    }

    @RequestMapping(value = "/saveLocation", method = RequestMethod.POST)
    public String save (Model model , @Valid Location location, Long article_id, BindingResult br, HttpServletRequest request) {
        Article a = articleRepository.findById(article_id).get();
        if(br.hasErrors()) {
            model.addAttribute("location",location);
            model.addAttribute("article_id",article_id);
            return "locations/form";
        }
        location.setArticle(a);
        location.setDate(utils.getDate());
        location.setCreateBy(utils.getUser());
        model.addAttribute("location",locationRepository.save(location));
        return "locations/detail";
    }

}
