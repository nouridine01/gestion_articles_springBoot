package ca.uqac.gestionarticles.controllers;

import ca.uqac.gestionarticles.entities.Categorie;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class CategorieController {
    @Autowired
    private CategorieRepository categorieRepository;

    @RequestMapping(value = "/categories")
    public String index(Model model, @RequestParam(name = "page",defaultValue = "0") int page,
                        @RequestParam(name = "mc",defaultValue = "") String mc,
                        @RequestParam(name = "size",defaultValue = "5")int size,
                        @RequestParam(name = "message",defaultValue = "")String msg) {
        Page<Categorie> liste =categorieRepository.chercher("%"+mc+"%", PageRequest.of(page, size) );
        int[] pages = new int[liste.getTotalPages()];
        model.addAttribute("listes", liste.getContent());
        model.addAttribute("pages", pages);
        model.addAttribute("size", size);
        model.addAttribute("pageCourante", page);
        if(!("".equals(msg))) model.addAttribute("message", msg);
        model.addAttribute("mc", mc);
        //retourne la vue employees.html
        return "categories/categories";
    }

    @RequestMapping(value = "/deleteCategorie", method = RequestMethod.GET)
    public String delete(Long id,String mc,String page,String size) {
        String msg="";
        try{
            categorieRepository.deleteById(id);
        }catch (Exception e){
        	return "redirect:/categories?message=Impossible de supprimer cette categorie";
        }

        return "redirect:/categories";
    }

    @RequestMapping(value = "/detailCategorie", method = RequestMethod.GET)
    public String detail(Model model,Long id) {
        model.addAttribute("categorie",categorieRepository.findById(id).get());
        return "categories/detail";
    }

    @RequestMapping(value = "/createCategorie", method = RequestMethod.GET)
    public String form (Model model) {
        model.addAttribute("categorie", new Categorie());
        return "categories/form";
    }

    @RequestMapping(value = "/saveCategorie", method = RequestMethod.POST)
    public String save (Model model , @Valid Categorie categorie, BindingResult br, HttpServletRequest request) {

        if(br.hasErrors()) {
            model.addAttribute("categorie",categorie);
            return "categories/form";
        }


        model.addAttribute("categorie", categorieRepository.save(categorie));
        return "redirect:/detailCategorie?id=" + categorie.getId();
    }

    @RequestMapping(value = "/editCategorie", method = RequestMethod.GET)
    public String edit (Model model,Long id) {
        model.addAttribute("categorie",categorieRepository.findById(id).get());
        return "categories/edit";
    }

    @RequestMapping(value = "/updateCategorie", method = RequestMethod.POST)
    public String update (Model model , @Valid Categorie categorie, BindingResult br, HttpServletRequest request) {
        Categorie c = categorieRepository.findById(categorie.getId()).get();
        categorie.setArticles(c.getArticles());
        categorieRepository.save(categorie);
        model.addAttribute("categorie",categorie);
        return "redirect:/detailCategorie?id=" + categorie.getId();
    }
}
