package com.crookedcoder.habitjournal.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crookedcoder.habitjournal.exception.DuplicateResourceException;
import com.crookedcoder.habitjournal.exception.ResourceNotFoundException;
import com.crookedcoder.habitjournal.journal.habits.Habit;
import com.crookedcoder.habitjournal.journal.habits.HabitRepository;
import com.crookedcoder.habitjournal.service.HabitService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class HabitServiceImpl implements HabitService {

    private final HabitRepository habitRepository;

    @Override
    public Habit createHabit(Habit habit) {
        log.info("Creating new habit: {}", habit.getName());

        // Check if habit with same name already exists
        if (habitRepository.existsByName(habit.getName())) {
            throw new DuplicateResourceException("Habit", "name", habit.getName());
        }

        // Set default values if not provided
        if (habit.getRecurring() == null) {
            habit.setRecurring(false);
        }
        if (habit.getActive() == null) {
            habit.setActive(true);
        }

        Habit savedHabit = habitRepository.save(habit);
        log.info("Habit created successfully: {}", savedHabit.getId());
        return savedHabit;
    }

    @Override
    @Transactional(readOnly = true)
    public Habit getHabitById(String id) {
        return habitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Habit", "id", id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Habit> getHabitsByJournalId(String journalId) {
        return habitRepository.findAll().stream()
                .filter(habit -> habit.getJournalId().equals(journalId))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Habit> getActiveHabitsByJournalId(String journalId) {
        return habitRepository.findAll().stream()
                .filter(habit -> habit.getJournalId().equals(journalId) && habit.getActive())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Habit> getRecurringHabitsByJournalId(String journalId) {
        return habitRepository.findAll().stream()
                .filter(habit -> habit.getJournalId().equals(journalId) && habit.getRecurring())
                .collect(Collectors.toList());
    }

    @Override
    public Habit updateHabit(String id, Habit updatedHabit) {
        Habit existingHabit = getHabitById(id);

        // Update fields
        if (updatedHabit.getName() != null && !updatedHabit.getName().equals(existingHabit.getName())) {
            if (habitRepository.existsByName(updatedHabit.getName())) {
                throw new DuplicateResourceException("Habit", "name", updatedHabit.getName());
            }
            existingHabit.setName(updatedHabit.getName());
        }
        if (updatedHabit.getDescription() != null) {
            existingHabit.setDescription(updatedHabit.getDescription());
        }
        if (updatedHabit.getGoal() != null) {
            existingHabit.setGoal(updatedHabit.getGoal());
        }
        if (updatedHabit.getRecurring() != null) {
            existingHabit.setRecurring(updatedHabit.getRecurring());
        }
        if (updatedHabit.getActive() != null) {
            existingHabit.setActive(updatedHabit.getActive());
        }

        Habit saved = habitRepository.save(existingHabit);
        log.info("Habit updated successfully: {}", saved.getId());
        return saved;
    }

    @Override
    public Habit toggleHabitActive(String id) {
        Habit habit = getHabitById(id);
        habit.setActive(!habit.getActive());
        Habit saved = habitRepository.save(habit);
        log.info("Habit active status toggled: {} -> {}", saved.getId(), saved.getActive());
        return saved;
    }

    @Override
    public void deleteHabit(String id) {
        Habit habit = getHabitById(id);
        habitRepository.delete(habit);
        log.info("Habit deleted successfully: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Habit> getAllHabits() {
        return habitRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return habitRepository.existsByName(name);
    }
}
