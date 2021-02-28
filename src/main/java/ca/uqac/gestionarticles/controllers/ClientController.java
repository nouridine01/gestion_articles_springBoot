package ca.uqac.gestionarticles.controllers;

import ca.uqac.gestionarticles.entities.Client;
import ca.uqac.gestionarticles.repositories.ClientRepository;
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
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping(value = "/clients")
    public String index(Model model, @RequestParam(name = "page",defaultValue = "0") int page,
                        @RequestParam(name = "mc",defaultValue = "") String mc,
                        @RequestParam(name = "size",defaultValue = "5")int size,
                        @RequestParam(name = "message",defaultValue = "")String msg) {
        Page<Client> liste =clientRepository.chercher("%"+mc+"%", PageRequest.of(page, size) );
        int[] pages = new int[liste.getTotalPages()];
        model.addAttribute("listes", liste.getContent());
        model.addAttribute("pages", pages);
        model.addAttribute("size", size);
        model.addAttribute("pageCourante", page);
        if(!("".equals(msg))) model.addAttribute("message", msg);
        model.addAttribute("mc", mc);
        //retourne la vue employees.html
        return "clients/clients";
    }

    @RequestMapping(value = "/deleteClient", method = RequestMethod.GET)
    public String delete(Long id,String mc,String page,String size) {

        String msg="";
        try{
        	clientRepository.findById(id).get().getUser().setActive(false);
            clientRepository.deleteById(id);
        }catch (Exception e){
            msg="impossible de supprimer ce client";
        }
        return "redirect:/clients";
    }

    @RequestMapping(value = "/detailClient", method = RequestMethod.GET)
    public String detail(Model model,Long id) {
        model.addAttribute("client",clientRepository.findById(id).get());
        return "clients/detail";
    }

    @RequestMapping(value = "/createClient", method = RequestMethod.GET)
    public String form (Model model) {
        model.addAttribute("client", new Client());
        return "clients/form";
    }

    @RequestMapping(value = "/saveClient", method = RequestMethod.POST)
    public String save (Model model , @Valid Client client, BindingResult br, HttpServletRequest request) {

        if(br.hasErrors()) {
            model.addAttribute("client",client);
            return "clients/form";
        }

        model.addAttribute("client", clientRepository.save(client));
        return "redirect:/detailClient?id=" + client.getId();
    }

    @RequestMapping(value = "/editClient", method = RequestMethod.GET)
    public String edit (Model model,Long id) {
        model.addAttribute("client",clientRepository.findById(id).get());
        return "clients/edit";
    }

    @RequestMapping(value = "/updateClient", method = RequestMethod.POST)
    public String update (Model model , @Valid Client client, BindingResult br, HttpServletRequest request) {
        Client c = clientRepository.findById(client.getId()).get();
        client.setUser(c.getUser());
        client.setAchats(c.getAchats());
        client.setLocations(c.getLocations());
        client.setReservations(c.getReservations());
        clientRepository.save(client);
        model.addAttribute("client",client);
        return "redirect:/detailClient?id=" + client.getId();
    }
}
