package de.htwk.openNoteKeeper.client.main.presenter;

import java.util.List;

import de.htwk.openNoteKeeper.shared.GroupDTO;
import de.htwk.openNoteKeeper.shared.UserDTO;

public class Session {

	private static UserDTO currentUser;

	private static List<GroupDTO> cachedGroups;

	public static UserDTO getCurrentUser() {
		if (currentUser == null)
			throw new RuntimeException("no current user set");
		return currentUser;
	}

	public static void setCurrentUser(UserDTO currentUser) {
		Session.currentUser = currentUser;
	}

	public static List<GroupDTO> getCachedGroups() {
		if (cachedGroups == null)
			throw new RuntimeException("no cached group set");
		return cachedGroups;
	}

	public static void setCachedGroups(List<GroupDTO> cachedGroups) {
		Session.cachedGroups = cachedGroups;
	}
}
