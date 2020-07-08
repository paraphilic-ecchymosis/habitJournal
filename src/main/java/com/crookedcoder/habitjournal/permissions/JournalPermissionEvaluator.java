package com.crookedcoder.habitjournal.permissions;

import java.io.Serializable;
import java.util.List;
import static com.crookedcoder.habitjournal.util.AuthenticationUtil.getUsername;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.crookedcoder.habitjournal.entity.Journal;
import com.crookedcoder.habitjournal.entity.JournalAccessControl;
import com.crookedcoder.habitjournal.repository.JournalAccessControlRepository;
import com.crookedcoder.habitjournal.repository.JournalRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JournalPermissionEvaluator implements PermissionEvaluator {
    
    private JournalAccessControlRepository journalAccessControlRepository;
	private JournalRepository journalRepository;
	
	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
		if (targetDomainObject != null || (targetDomainObject instanceof Journal) ) {
			String principalUsername = getUsername();
			Journal journal = (Journal)targetDomainObject;
			boolean userIsJournalOwner = principalUsername.equals(journal.getUsername());
			return (userIsJournalOwner || userHasReadPermissionForTargetJournal(principalUsername, journal.getId()));
		}
		return false;
	}
	
	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
			Object permission) {
		if(targetId != null) {
			User user = (User)authentication.getPrincipal();
			Journal journal = this.journalRepository.findById(targetId.toString()).get();
			boolean userIsJournalOwner = user.getUsername().equals(journal.getUsername());
			return (userIsJournalOwner || userHasReadPermissionForTargetJournal(user.getUsername(), targetId.toString()));
		}
		return false;
	}
	
	private boolean userHasReadPermissionForTargetJournal(String username, String journalId) {
		List<JournalAccessControl> userJournalAC = this.journalAccessControlRepository.findAllByUsername(username);
		if(userJournalAC != null) {
			return userJournalAC.stream().anyMatch(ac -> ac.getJournalId().equals(journalId));
		}
		return false;
	}

	public static void main(String[] args) {
		System.out.println(Journal.class.getName());
	}
}