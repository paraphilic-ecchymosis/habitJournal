package com.crookedcoder.persistence.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class JournalController {
    

    @GetMapping("journal")
    public String getJournal(Map<String, Object> model) {
        model.put("message", "Jacob");
        return "journal";
    }
}