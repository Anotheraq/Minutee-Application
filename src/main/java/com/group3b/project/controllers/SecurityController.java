package com.group3b.project.controllers;

import com.group3b.project.Encode;
import com.group3b.project.models.User;
import com.group3b.project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@SessionAttributes("user")
public class SecurityController {
    @Autowired
    UserRepository userRepository;
    Encode encode = new Encode();
    @PostMapping("/signup")
    public String register(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("confirm") String confirm,
            Model model) {
        if (email.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            model.addAttribute("message", "Please fill in all blanks!");
            return "signup";
        } else if (!password.equals(confirm)) {
            model.addAttribute("message", "Your password doesn't match!");
            return "signup";
        } else if(!email.matches("\\S+@\\S+\\.\\S+")){
            model.addAttribute("message", "Wrong email format!");
            return "signup";
        }

        User result = userRepository.getUser(email);
        if (result != null) {
            model.addAttribute("message", "User already exists");
            return "signup";
        }
        User user = new User(email, encode.getMd5(password));
        userRepository.addUser(user);
        return "login";

    }

    @PostMapping("/login")
    public String login(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            Model model,
            @ModelAttribute("user") User user) {

        if (email.isEmpty() || password.isEmpty()) {
            model.addAttribute("message", "Please fill in all blanks!");
            return "login";
        }else if(!email.matches("\\S+@\\S+\\.\\S+")){
            model.addAttribute("message", "Wrong email format!");
            return "login";
        }

        User result = userRepository.getUser(email);
        if(result == null){
            model.addAttribute("message", "User does not exists");
            return "login";
        }else if(!result.getPassword().equals(encode.getMd5(password))){
            model.addAttribute("message", "Wrong password");
            return "login";
        }

        user.setId(result.getId());
        user.setPassword(result.getPassword());
        user.setEmail(result.getEmail());

        return "redirect:/add-activity";
    }

    @GetMapping("/logout")
    public String logout(
            Model model,
            @ModelAttribute(name="user") User user,
            SessionStatus status) {
        if(user == null){
            return "login";
        }
        status.setComplete();

        return "login";
    }
    @ModelAttribute("user")
    public User user() {
        return new User();
    }
}
