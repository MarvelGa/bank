package com.banksystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/bank-api")
    public String  showHomePage()  {
        return "home";
    }
}
