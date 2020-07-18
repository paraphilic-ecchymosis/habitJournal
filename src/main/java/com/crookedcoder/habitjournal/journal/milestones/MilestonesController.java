package com.crookedcoder.habitjournal.journal.milestones;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MilestonesController {

    private final MilestoneRepository milestoneRepository;
    
    @PostMapping("/milestone")
	public String createMilestone(@Valid @ModelAttribute("milestone") Milestone milestone, BindingResult result) {
		if(result.hasErrors()) {
            return "milestones/milestones";
		}

        milestoneRepository.save(milestone);
        return "redirect:/milestone?success";
        
    }

    @GetMapping("/milestones")
	public @ResponseBody List<Milestone> getUserMilestones() {
        return milestoneRepository.findAll();
    }
}