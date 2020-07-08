package com.crookedcoder.habitjournal.service;

import static com.crookedcoder.habitjournal.util.AuthenticationUtil.getUsername;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.crookedcoder.habitjournal.entity.Entry;
import com.crookedcoder.habitjournal.entity.HjUser;
import com.crookedcoder.habitjournal.entity.Journal;
import com.crookedcoder.habitjournal.model.EntryDetailsDto;
import com.crookedcoder.habitjournal.model.HabitDto;
import com.crookedcoder.habitjournal.model.JournalDto;
import com.crookedcoder.habitjournal.model.ListEntriesDto;
import com.crookedcoder.habitjournal.repository.JournalAccessControlRepository;
import com.crookedcoder.habitjournal.repository.JournalRepository;
import com.crookedcoder.habitjournal.repository.HjUserRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class JournalQueryServiceNoSql implements JournalQueryService {
    
    private HabitQueryService habitService;
	private MilestoneQueryService milestoneService;
	private JournalRepository journalRepository;
	private JournalAccessControlRepository journalAccessControlRepo;
	private HjUserRepository userRepository;
    private Map<String, JournalDto> journal = null;

    @Override
    public JournalDto getJournal(String userName) {
        return journal.get(userName);
    }

    @Override
	public ListEntriesDto getJournalEntries() {
		Journal journal = this.journalRepository.findByUsername(getUsername());
		List<Entry> entries = journal.getEntries();
		return createListEntriesResponse(getUsername(), entries);
	}

    private ListEntriesDto createListEntriesResponse(String username, List<Entry> entries) {
		List<EntryDetailsDto> entryDetails = new ArrayList<>();
		for(Entry entry : entries) {
			entryDetails.add(new EntryDetailsDto(entry.getId(), entry.getUsername(), entry.getHabit().getId(), Integer.toString(entry.getUnitsCompleted()), Long.toString(entry.getTimestamp())));
		}
		return new ListEntriesDto(username, entryDetails);
	}
}