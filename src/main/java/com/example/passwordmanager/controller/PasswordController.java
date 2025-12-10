package com.example.passwordmanager.controller;

import com.example.passwordmanager.model.PasswordEntry;
import com.example.passwordmanager.service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for the password manager app UI.
 * - GET /app -> show the app dashboard with all entries
 * - POST /add -> add a new PasswordEntry (form posts fields website, username,
 * password)
 * - GET /delete/{id} -> delete an entry and redirect back to /app
 */
@Controller
public class PasswordController {

    @Autowired
    private PasswordService service;

    @GetMapping("/app")
    public String appPage(Model model) {
        model.addAttribute("entries", service.getAll()); // list of PasswordEntry
        // optional: add summary counts for profile sidebar
        model.addAttribute("count", service.getAll().size());
        model.addAttribute("username", "Dylan"); // or from auth
        return "app";
    }

    @PostMapping("/add")
    public String add(@RequestParam String website,
            @RequestParam String username,
            @RequestParam String password) {
        // Build entity and save (service should hash password as you implemented)
        PasswordEntry e = new PasswordEntry(website, username, password);
        service.save(e);
        return "redirect:/app";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/app";
    }
}
