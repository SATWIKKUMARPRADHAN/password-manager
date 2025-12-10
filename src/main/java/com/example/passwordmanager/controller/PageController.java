package com.example.passwordmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Simple controller for non-data pages (landing, about, profile view).
 */
@Controller
public class PageController {

    @GetMapping("/")
    public String landing() {
        return "landing";
    }

    @GetMapping("/about")
    public String about() {
        return "landing"; // or create an about.html if you prefer
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        // Add any attributes your profile page expects
        model.addAttribute("username", "Dylan");
        model.addAttribute("memberSince", "2024");
        return "profile";
    }
}
