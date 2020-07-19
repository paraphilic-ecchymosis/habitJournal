package com.crookedcoder.habitjournal.users;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserControler {
    
    private final UserRepository userRepository;

    @GetMapping("/api/users")
    public @ResponseBody List<User> getExistingUsers() {
        return userRepository.findAll();
    }

}