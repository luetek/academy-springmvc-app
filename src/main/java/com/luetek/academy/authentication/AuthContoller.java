package com.luetek.academy.authentication;

import com.luetek.academy.authentication.dtos.CreateUserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller()
@RequestMapping("/auth")
public class AuthContoller {

    private final UserAccountModificationService userAccountModificationService;

    public AuthContoller (UserAccountModificationService userAccountModificationService) {
        this.userAccountModificationService = userAccountModificationService;
    }

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new CreateUserDto());
        return "auth/signup";
    }

    @PostMapping("/signup")
    public String processSignup(@ModelAttribute CreateUserDto user) {
        // Here you would typically save the user to the database
        userAccountModificationService.createUserAccount(user);
        return "redirect:/"; // Redirect after successful signup
    }
}
