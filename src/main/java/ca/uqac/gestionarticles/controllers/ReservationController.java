package ca.uqac.gestionarticles.controllers;


import ca.uqac.gestionarticles.entities.Achat;
import ca.uqac.gestionarticles.entities.Location;
import ca.uqac.gestionarticles.entities.Reservation;
import ca.uqac.gestionarticles.repositories.*;
import ca.uqac.gestionarticles.service.AccountService;
import ca.uqac.gestionarticles.service.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;

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
        model.addAttribute("article_id",id);
        return "reservations/form";
    }

    @RequestMapping(value = "/satisfaireRservation", method = RequestMethod.POST)
    public String satisfairereservation (Model model , Long id, Date date,HttpServletRequest request) {
        Reservation reservation = reservationRepository.findById(id).get();
        String type = reservation.getType();
        if(type.equals("achat")){
            Achat achat = new Achat();
            achat.setClient(reservation.getClient());
            achat.setArticle(reservation.getArticle());
            achat.setQuantite(reservation.getQuantity());
            achat.setDate(utils.getDate());
            achat.setCreateBy(utils.getUser());
            achatRepository.save(achat);
        }else{
            Location location = new Location();
            location.setArticle(reservation.getArticle());
            location.setClient(reservation.getClient());
            location.setCreateBy(utils.getUser());
            location.setDate(utils.getDate());
            location.setQuantite(reservation.getQuantity());
            location.setDate_retour(date);
            locationRepository.save(location);
        }
        reservation.setSatisfaite(true);
        reservationRepository.save(reservation);
        model.addAttribute("article_id",id);
        return "reservations/form";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save (Model model ,Long id,@Valid Reservation reservation, HttpServletRequest request) {
        reservation.setArticle(articleRepository.findById(id).get());
        reservation.getArticle().setQuantite(reservation.getArticle().getQuantite() - 1);
        reservation.setClient(utils.getClient());
        reservation.setDate(utils.getDate());
        reservationRepository.save(reservation);
        model.addAttribute("reservation",reservation);
        return "reservations/detail";
    }

    @RequestMapping(value = "/reserverEnCours", method = RequestMethod.GET)
    public String reservationEnCours (Model model, @RequestParam(name = "page",defaultValue = "0") int page,
                                      @RequestParam(name = "size",defaultValue = "5")int size) {
        Page<Reservation> liste =reservationRepository.reservationEnCours( PageRequest.of(page, size) );
        int[] pages = new int[liste.getTotalPages()];
        model.addAttribute("listes", liste.getContent());
        model.addAttribute("pages", pages);
        model.addAttribute("size", size);
        model.addAttribute("pageCourante", page);
        return "reservations/liste";
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
