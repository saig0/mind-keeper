package de.htwk.openNoteKeeper.client.note.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.htwk.openNoteKeeper.shared.CoordinateDTO;
import de.htwk.openNoteKeeper.shared.NoteDTO;

public interface NoteServiceAsync {

	void getAllNotesForUser(String userId, AsyncCallback<List<NoteDTO>> callback);

	void createNoteForUser(String userId, String title, CoordinateDTO position,
			CoordinateDTO size, AsyncCallback<NoteDTO> callback);

	void updateNoteOfUser(String userId, NoteDTO note,
			AsyncCallback<Void> callback);

	void removeNoteOfUser(String userId, NoteDTO note,
			AsyncCallback<Void> callback);

}
