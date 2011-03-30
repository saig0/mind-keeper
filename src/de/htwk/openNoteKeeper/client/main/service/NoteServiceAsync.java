package de.htwk.openNoteKeeper.client.main.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.htwk.openNoteKeeper.shared.NoteDTO;

public interface NoteServiceAsync {

	void getAllNotesForUser(String userId, AsyncCallback<List<NoteDTO>> callback);

	void addNoteToUser(String userId, NoteDTO note, AsyncCallback<Void> callback);

	void updateNoteOfUser(String userId, NoteDTO note,
			AsyncCallback<Void> callback);

	void removeNoteOfUser(String userId, Long noteId,
			AsyncCallback<Void> callback);

}
