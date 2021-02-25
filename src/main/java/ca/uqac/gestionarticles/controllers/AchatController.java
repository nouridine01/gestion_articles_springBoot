package ca.uqac.gestionarticles.controllers;

import ca.uqac.gestionarticles.entities.*;
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
import java.util.Date;

@Controller
public class AchatController {
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

    @RequestMapping(value = "/achat", method = RequestMethod.GET)
    public String achat (Model model , Long id, HttpServletRequest request) {
        model.addAttribute("article_id",id);
        return "achats/form";
    }

    @RequestMapping(value = "/mesAchats", method = RequestMethod.GET)
    public String achat (Model model, @RequestParam(name = "page",defaultValue = "0") int page,Long article_id,
                            @RequestParam(name = "size",defaultValue = "5")int size) {

        Page<Achat> liste = achatRepository.mesAchats(utils.getClient().getId(), PageRequest.of(page,size));
        int[] pages = new int[liste.getTotalPages()];
        model.addAttribute("listes", liste.getContent());
        model.addAttribute("pages", pages);
        model.addAttribute("size", size);
        model.addAttribute("pageCourante", page);
        return "achats/mesAchats";
    }

    @RequestMapping(value = "/createAchat", method = RequestMethod.GET)
    public String form (Model model , Long article_id, Date date) {
        model.addAttribute("achat", new Achat());
        model.addAttribute("article_id",article_id);

        return "achats/form";
    }

    @RequestMapping(value = "/saveAchat", method = RequestMethod.POST)
    public String save (Model model , @Valid Achat achat, Long article_id, BindingResult br, HttpServletRequest request) {
        Article a = articleRepository.findById(article_id).get();
        if(br.hasErrors()) {
            model.addAttribute("achat",achat);
            model.addAttribute("article_id",article_id);
            return "achats/form";
        }
        achat.setArticle(a);
        achat.setDate(utils.getDate());
        achat.setCreateBy(utils.getUser());
        model.addAttribute("achat", achatRepository.save(achat));
        return "achats/detail";
    }

}

