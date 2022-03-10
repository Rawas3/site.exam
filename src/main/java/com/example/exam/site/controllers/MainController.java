package com.example.exam.site.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "My site");
        return "page-home";
    }

    @GetMapping("/blog")
    public String blog(Model model) {
        model.addAttribute("title", "My blog");
        return "page-blog";
    }
}
