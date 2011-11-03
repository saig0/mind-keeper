package de.htwk.openNoteKeeper.client.main.presenter;

import de.htwk.openNoteKeeper.shared.UserDTO;

public class Session {

	private static UserDTO currentUser;

	public static UserDTO getCurrentUser() {
		if (currentUser == null)
			throw new RuntimeException("no current user set");
		return currentUser;
	}

	public static void setCurrentUser(UserDTO currentUser) {
		Session.currentUser = currentUser;
	}
}
