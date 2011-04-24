package de.htwk.openNoteKeeper.client.note.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.htwk.openNoteKeeper.shared.Coordinate;
import de.htwk.openNoteKeeper.shared.NoteDTO;

public interface NoteServiceAsync {

	void getAllNotesForUser(String userId, AsyncCallback<List<NoteDTO>> callback);

	void createNoteForUser(String userId, String title, Coordinate position,
			Coordinate size, AsyncCallback<NoteDTO> callback);

	void updateNoteOfUser(String userId, NoteDTO note,
			AsyncCallback<Void> callback);

	void removeNoteOfUser(String userId, NoteDTO note,
			AsyncCallback<Void> callback);

}
