package com.crookedcoder.habitjournal.entities;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.crookedcoder.habitjournal.entities.Entry;
import com.crookedcoder.habitjournal.entities.Milestone;
import com.crookedcoder.habitjournal.entities.Habit;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Document(collection = "journal")
@RequiredArgsConstructor
public class Journal {
    
    @Id
	private String id;
	@Indexed(unique=true)
    private final String username;
    private List<Entry> entries;
    private List<Milestone> milestones;
    private List<Habit> habits;


	public void addEntry(Entry entry) {
		this.entries.add(entry);
	}
    
    public void addHabit(Habit habit) {
		this.habits.add(habit);
    }
    
    public void addMilestone(Milestone milestone) {
		this.milestones.add(milestone);
	}

	public List<Entry> getEntries() {
		return Collections.unmodifiableList(entries);
    }
    
    public List<Milestone> getMilestones() {
		return Collections.unmodifiableList(milestones);
    }
    
    public List<Habit> getHabits() {
		return Collections.unmodifiableList(habits);
    }
    
    public void deleteEntry(Entry entry) {
		entries.remove(entry);
    }
    
    public void deleteHabit(Habit habit) {
		habits.remove(habit);
	}
    
    public void deleteMilestone(Milestone milestone) {
		milestones.remove(milestone);
	}
    
    // TODO: Need filtered stream results for
    // returning lists with constraints.
	
	
	public Entry getEntryById(String id) {
		for(Entry entry : this.entries) {
			if(id.equals(entry.getId())) {
				return entry;
			}
		}
		return null;
	}

	public List<Entry> getEntriesByHabitId(String id2) {
		return null;
	}
}