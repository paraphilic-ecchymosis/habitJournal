package com.crookedcoder.habitjournal.service;

import static com.crookedcoder.habitjournal.util.AuthenticationUtil.getUsername;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Service;

import com.crookedcoder.habitjournal.entity.Habit;
import com.crookedcoder.habitjournal.entity.Journal;
import com.crookedcoder.habitjournal.entity.JournalAccessControl;
import com.crookedcoder.habitjournal.entity.Entry;
import com.crookedcoder.habitjournal.entity.Type;
import com.crookedcoder.habitjournal.model.AddEntryToJournalDto;
import com.crookedcoder.habitjournal.repository.HabitRepository;
import com.crookedcoder.habitjournal.repository.JournalAccessControlRepository;
import com.crookedcoder.habitjournal.repository.JournalRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class JournalCommandServiceNoSql  implements JournalCommandService {
    
    private JournalRepository journalRepository;
	private HabitRepository habitRepository;
	private JournalAccessControlRepository accessControl;
	
	@Override
	public void addEntryToJournal(AddEntryToJournalDto request) {
		Journal journal = journalRepository.findByUsername(getUsername());
		Entry entry = createEntryEntity(request);
		journal.addEntry(entry);
		journalRepository.save(journal);
	}

	@PreFilter("hasRole('ADMIN') or filterObject.username == authentication.username")
	public void addEntryToJournal(List<AddEntryToJournalDto> entriesDto) {
		Journal journal = journalRepository.findByUsername(getUsername());
		for(AddEntryToJournalDto entryDto : entriesDto) {
			Entry entry = createEntryEntity(entryDto);
			journal.addEntry(entry);
		}
		journalRepository.save(journal);
	}
	
	@Override
	public void removeEntryFromJournal(String entryId) {
		Journal journal = journalRepository.findByUsername(getUsername());
		Entry entry = journal.getEntryById(entryId);
		journal.deleteEntry(entry);
		journalRepository.save(journal);
	}
	
	@Override
	public void createNewJournal(String username) {
		Journal journal = journalRepository.save(new Journal(username));
		JournalAccessControl journalAccessControl = new JournalAccessControl(getUsername(), journal.getId(), "READ");
		this.accessControl.save(journalAccessControl);
	}
	
	private Entry createEntryEntity(AddEntryToJournalDto request) {
		Habit habit = habitRepository.findByID(request.getHabitID());
		Entry entry = new Entry(request.getUsername(), habit, Integer.parseInt(request.getUnitsCompleted()), Long.parseLong(request.getTimeStamp()));
		return entry;
	}

	@Override
	public boolean userHasAJournal(String username) {
		return this.journalRepository.existsByUsername(username);
	}
}