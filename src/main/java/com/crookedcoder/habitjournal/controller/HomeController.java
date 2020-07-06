package com.crookedcoder.habitjournal.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    @GetMapping("home-template")
    public String getHome(Map<String, Object> model) {
        
        return "home-template";
    }
}