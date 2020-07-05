package com.crookedcoder.persistence.controller;

import java.util.Map;

import com.crookedcoder.persistence.model.Behavior;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BehaviorController {
    
    @GetMapping("behaviors-template")
    public String getBehaviors(@ModelAttribute ("behaviors")Behavior behavior, Map<String, Object> model) {
        // TODO: User data.
        return "behaviors-template";
    }

    @PostMapping("behaviors-template")
    public String addBehavior(@ModelAttribute ("behaviors")Behavior behavior) {
        System.out.println("Behaviors: " + behavior.toString());
        return "redirect:behaviors-template";
    }

}