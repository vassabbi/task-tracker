package com.birich.task_tracker.Controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.birich.task_tracker.Dto.RegisterRequest;
import com.birich.task_tracker.Service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "register";
    }
    
    @PostMapping("/register")
    public String register(
        @Valid @ModelAttribute RegisterRequest registerRequest,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()){
            return "register";
        }
        try {
            userService.register(registerRequest);
        }
        catch (IllegalArgumentException e){
            bindingResult.rejectValue(
                "username", 
                "error.username",
                e.getMessage()
            );
            return "register";
        }
        return "redirect:/login";
    }
    
}
