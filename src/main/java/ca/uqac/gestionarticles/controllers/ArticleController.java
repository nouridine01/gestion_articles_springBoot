package ca.uqac.gestionarticles.controllers;

import ca.uqac.gestionarticles.entities.Article;
import ca.uqac.gestionarticles.entities.Categorie;
import ca.uqac.gestionarticles.repositories.ArticleRepository;
import ca.uqac.gestionarticles.repositories.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ca.uqac.gestionarticles.service.Utils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CategorieRepository categorieRepository;
    private Utils utils;

    @RequestMapping(value = "/articles")
    public String index(Model model, @RequestParam(name = "page",defaultValue = "0") int page,
                        @RequestParam(name = "mc",defaultValue = "") String mc,
                        @RequestParam(name = "size",defaultValue = "5")int size,
                        @RequestParam(name = "message",defaultValue = "")String msg) {
        Page<Article> liste =articleRepository.chercher("%"+mc+"%", PageRequest.of(page, size) );
        int[] pages = new int[liste.getTotalPages()];
        model.addAttribute("listes", liste.getContent());
        model.addAttribute("pages", pages);
        model.addAttribute("size", size);
        model.addAttribute("pageCourante", page);
        if(!("".equals(msg))) model.addAttribute("message", msg);
        model.addAttribute("mc", mc);
        //retourne la vue employees.html
        return "articles/articles";
    }

    @RequestMapping(value = "/deleteArticle", method = RequestMethod.GET)
    public String delete(Model model, Long id) {

        String msg="";
        try{
            articleRepository.deleteById(id);
        }catch (Exception e){
            msg="impossible de supprimer cet article";
        }
        return "redirect:/articles";
    }

    @RequestMapping(value = "/detailArticle", method = RequestMethod.GET)
    public String detail(Model model,Long id) {
        model.addAttribute("article",articleRepository.findById(id).get());
        return "articles/detail";
    }



    @RequestMapping(value = "/createArticle", method = RequestMethod.GET)
    public String form (Model model) {
        model.addAttribute("categorieListe", categorieRepository.findAll());
        model.addAttribute("article", new Article());
        return "articles/form";
    }

    @RequestMapping(value = "/saveArticle", method = RequestMethod.POST)
    public String save (Model model , @Valid Article article, BindingResult br, HttpServletRequest request) {

        if(br.hasErrors()) {
            model.addAttribute("categorieListe", categorieRepository.findAll());
            model.addAttribute("article",article);
            return "articles/form";
        }


        model.addAttribute("article", articleRepository.save(article));
        return "redirect:/detailArticle?id=" + article.getId();
    }

    @RequestMapping(value = "/editArticle", method = RequestMethod.GET)
    public String edit (Model model,Long id) {
        model.addAttribute("categorieListe", categorieRepository.findAll());
        model.addAttribute("article",articleRepository.findById(id).get());
        return "articles/edit";
    }

    @RequestMapping(value = "/updateArticle", method = RequestMethod.POST)
    public String update (Model model , @Valid Article article, BindingResult br, HttpServletRequest request) {
        Article a=articleRepository.findById(article.getId()).get();
        article.setAchatEffectues(a.getAchatEffectues());
        article.setLocationEnCours(a.getLocationEnCours());
        article.setReservationEnCours(a.getReservationEnCours());
        article.setCreateBy(a.getCreateBy());
        articleRepository.save(article);
        model.addAttribute("article",article);
        return "redirect:/detailArticle?id=" + article.getId();
    }
}
