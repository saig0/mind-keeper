package de.htwk.openNoteKeeper.client.main.presenter;

import de.htwk.openNoteKeeper.shared.UserDTO;

public class Session {

	private static UserDTO currentUser;

	private static boolean editorIsVisable = false;

	public static UserDTO getCurrentUser() {
		if (currentUser == null)
			throw new RuntimeException("no current user set");
		return currentUser;
	}

	public static void setCurrentUser(UserDTO currentUser) {
		Session.currentUser = currentUser;
	}

	public static boolean isEditorVisable() {
		return editorIsVisable;
	}

	public static void setEditorIsVisable(boolean editorIsVisable) {
		Session.editorIsVisable = editorIsVisable;
	}
}
