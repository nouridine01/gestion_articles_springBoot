package ca.uqac.gestionarticles.controllers;


import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ca.uqac.gestionarticles.entities.Achat;
import ca.uqac.gestionarticles.entities.Location;
import ca.uqac.gestionarticles.entities.Reservation;
import ca.uqac.gestionarticles.repositories.AchatRepository;
import ca.uqac.gestionarticles.repositories.ArticleRepository;
import ca.uqac.gestionarticles.repositories.LocationRepository;
import ca.uqac.gestionarticles.repositories.ReservationRepository;
import ca.uqac.gestionarticles.repositories.UserRepository;
import ca.uqac.gestionarticles.service.AccountService;
import ca.uqac.gestionarticles.service.Utils;

@Controller
public class ReservationController {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Utils utils;
    @Autowired
    private AchatRepository achatRepository;
    @Autowired
    private LocationRepository locationRepository;

    @RequestMapping(value = "/reserver", method = RequestMethod.GET)
    public String reservation (Model model ,Long id, HttpServletRequest request) {
    	Reservation r = new Reservation();
    	model.addAttribute("reservation", r);
        model.addAttribute("article",articleRepository.findById(id).get());
        return "reservations/form";
    }
    
    @RequestMapping(value = "/detailReservation", method = RequestMethod.GET)
    public String detailReservation (Model model, Long id) {
    	model.addAttribute("reservation", reservationRepository.findById(id).get());
    	return "reservations/detail";
    }

    @RequestMapping(value = "/satisfaireReservation", method = RequestMethod.POST)
    public String satisfairereservation (Model model , Long id, @DateTimeFormat(pattern = "yyyy-MM-dd") Date date_retour, HttpServletRequest request) {
        Reservation reservation = reservationRepository.findById(id).get();
        String type = reservation.getType();
        if(type.equals("achat")){
            Achat achat = new Achat();
            achat.setClient(reservation.getClient());
            achat.setArticle(reservation.getArticle());
            achat.setQuantite(reservation.getQuantity());
            achat.setDate(utils.getDate());
            achat.setCreateBy(utils.getUser());
            model.addAttribute("achat", achat);
            achatRepository.save(achat);
            
            reservation.setSatisfaite(true);
            reservationRepository.save(reservation);
            
            return "redirect:/achats";
        }else{
            Location location = new Location();
            location.setArticle(reservation.getArticle());
            location.setClient(reservation.getClient());
            location.setCreateBy(utils.getUser());
            location.setDate(utils.getDate());
            location.setDate_retour(date_retour);
            location.setQuantite(reservation.getQuantity());
            location.setCreateBy(utils.getUser());
            model.addAttribute("location", location);
            locationRepository.save(location);
            
            reservation.setSatisfaite(true);
            reservationRepository.save(reservation);
            
            return "redirect:/locations";
        }
    }

    @RequestMapping(value = "/saveReservation", method = RequestMethod.POST)
    public String save (Model model, @Valid Reservation reservation, Long article_id, HttpServletRequest request) {
        reservation.setArticle(articleRepository.findById(article_id).get());
        reservation.getArticle().setQuantite(reservation.getArticle().getQuantite() - reservation.getQuantity());
        reservation.setClient(utils.getClient());
        reservation.setDate(utils.getDate());
        
        model.addAttribute("reservation",reservationRepository.save(reservation));
        return "redirect:/detailReservation?id=" + reservation.getId();
    }

    @RequestMapping(value = "/reservations", method = RequestMethod.GET)
    public String reservationEnCours (Model model, @RequestParam(name = "page",defaultValue = "0") int page,
                                      @RequestParam(name = "size",defaultValue = "5")int size) {
        Page<Reservation> liste =reservationRepository.reservationEnCours( PageRequest.of(page, size) );
        int[] pages = new int[liste.getTotalPages()];
        model.addAttribute("listes", liste.getContent());
        model.addAttribute("pages", pages);
        model.addAttribute("size", size);
        model.addAttribute("pageCourante", page);
        return "reservations/reservations";
    }

    @RequestMapping(value = "/mesReservations", method = RequestMethod.GET)
    public String reservation (Model model, @RequestParam(name = "page",defaultValue = "0") int page,
                               @RequestParam(name = "size",defaultValue = "5")int size) {
        Page<Reservation> liste =reservationRepository.mesReservations(utils.getClient().getId(),PageRequest.of(page,size) );
        int[] pages = new int[liste.getTotalPages()];
        model.addAttribute("listes", liste.getContent());
        model.addAttribute("pages", pages);
        model.addAttribute("size", size);
        model.addAttribute("pageCourante", page);
        return "reservations/mesreservations";
    }


}
