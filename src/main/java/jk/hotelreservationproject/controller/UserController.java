package jk.hotelreservationproject.controller;

import jk.hotelreservationproject.model.User;
import jk.hotelreservationproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class UserController {

    UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String addUser(Model model){
        model.addAttribute("user", new User());
        return "adduser";
    }

    @PostMapping("/register")
    public String addUser(
            @ModelAttribute @Valid User user,
            BindingResult bindingResult,
            Model model){

        if (bindingResult.hasErrors()){
            return "adduser";
        }
        if (userService.passwordCheck(user.getPassword(), user.getPassword_confirm())){
            userService.registerUser(user);
            return "redirect:/";
        }
        model.addAttribute("passwordMessage", "Passwords do not match");
        return "adduser";
    }


    @GetMapping("/login")
    public String loginUser(){
        return  "redirect:/login";
    }



}
