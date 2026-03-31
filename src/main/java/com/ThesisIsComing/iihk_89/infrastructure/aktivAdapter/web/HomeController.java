package com.ThesisIsComing.iihk_89.infrastructure.aktivAdapter.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String startseite() {
        return "Startseite";
    }
}