package com.example.passwordmanager.controller;

import com.example.passwordmanager.model.PasswordEntry;
import com.example.passwordmanager.service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PasswordController {

    @Autowired
    private PasswordService service;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("entry", new PasswordEntry());
        model.addAttribute("entries", service.getAll());
        return "index";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute PasswordEntry entry) {
        service.save(entry);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/";
    }
}
