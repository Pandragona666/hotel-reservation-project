package jk.hotelreservationproject.controller;

import jk.hotelreservationproject.model.Contact;
import jk.hotelreservationproject.service.AutoMailingService;
import jk.hotelreservationproject.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class ContactController {

    private ContactService contactService;
    private AutoMailingService autoMailingService;

    @Autowired
    public ContactController(ContactService contactService, AutoMailingService autoMailingService) {
        this.contactService = contactService;
        this.autoMailingService = autoMailingService;
    }

    @GetMapping("/contact")
    public String contact(Model model, Authentication auth){
        Contact contact = new Contact();
        if(auth != null){
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            contact.setEmail(userDetails.getUsername());
            model.addAttribute("loggedEmail", userDetails.getUsername());
        }
        model.addAttribute("contact", contact);
        return "contact";
    }

    @PostMapping("/contact")
    public String contact(@ModelAttribute @Valid Contact contact, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "contact";
        }
        contactService.addContact(contact);
        autoMailingService.sendMessage(contact.getEmail(),
                "Hotel Message Confirmation",
                "Thank you for your reservation :)");
        return "redirect:/";
    }

}
