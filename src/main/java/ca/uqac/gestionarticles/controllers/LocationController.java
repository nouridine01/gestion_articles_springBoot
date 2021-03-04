package ca.uqac.gestionarticles.controllers;


import ca.uqac.gestionarticles.entities.Achat;
import ca.uqac.gestionarticles.entities.Article;
import ca.uqac.gestionarticles.entities.Client;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class LocationController {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ClientRepository clientRepository;
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
    
    @RequestMapping(value = "/modifierDatelocation", method = RequestMethod.GET)
    public String location (Model model , Long id,Long article_id, HttpServletRequest request) {
        Location l = locationRepository.findById(id).get();
        model.addAttribute("location", l);
        return "locations/modifierRetour";
    }

    @RequestMapping(value = "/updateDateRetour", method = RequestMethod.POST)
    public String update (Model model , @Valid Location location,Long article_id, BindingResult br, HttpServletRequest request) {
        Location l=locationRepository.findById(location.getId()).get();
        l.setDate_retour(location.getDate_retour());
        locationRepository.save(l);
        model.addAttribute("location",l);
        return "locations/detail";
    }

    @RequestMapping(value = "/createLocation", method = RequestMethod.GET)
    public String form (Model model,Long article_id) {
        List<Client> clientListe = clientRepository.findAll();
        model.addAttribute("clientListe", clientListe);
        model.addAttribute("location", new Location());
        model.addAttribute("article", articleRepository.findById(article_id).get());

        return "locations/form";
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
        location.getArticle().setQuantite(location.getArticle().getQuantite() - location.getQuantite());
        location.setDate(utils.getDate());
        location.setCreateBy(utils.getUser());
        model.addAttribute("location",locationRepository.save(location));

        return "redirect:/detailLocation?id=" + location.getId();
    }

    @RequestMapping(value = "/detailLocation", method = RequestMethod.GET)
    public String detail(Model model,Long id) {
        model.addAttribute("location", locationRepository.findById(id).get());
        return "locations/detail";
    }

}
