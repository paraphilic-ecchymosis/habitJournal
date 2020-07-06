package com.crookedcoder.habitjournal.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.crookedcoder.habitjournal.entity.Milestone;
import com.crookedcoder.habitjournal.model.MilestoneDto;
import com.crookedcoder.habitjournal.repository.MilestoneRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MilestoneQueryServiceNoSql implements MilestoneQueryService {

    private final MilestoneRepository milestoneRepository;
    private Map<String, MilestoneDto> milestones = null;

    @Override
    public List<MilestoneDto> getMilestones() {
        if(this.milestones == null) {
            this.milestones = new HashMap<>();
            for(Milestone milestone : milestoneRepository.findAll()) {
                // TODO: Too tired to finish this.
                this.milestones.put(milestone.getName(), new MilestoneDto(milestone.getId(), milestone.getName(), null,
                        null, null, null));
            }
        }
        return new ArrayList<>(milestones.values());
    }

    @Override
    public MilestoneDto getMilestone(String name) {
        return milestones.get(name);
    }
    
}