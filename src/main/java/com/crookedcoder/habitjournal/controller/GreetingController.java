package com.crookedcoder.habitjournal.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GreetingController {
    

    @GetMapping("welcome")
    public String greeting (Map<String, Object> model) {
        model.put("message", "Hello Jacob");
        return "welcome";
    }
}