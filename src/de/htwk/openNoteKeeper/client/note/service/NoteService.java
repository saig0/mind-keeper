package de.htwk.openNoteKeeper.client.note.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.htwk.openNoteKeeper.shared.NoteDTO;

@RemoteServiceRelativePath("note")
public interface NoteService extends RemoteService {

	public List<NoteDTO> getAllNotesForUser(String userId);

	public NoteDTO createNoteForUser(String userId, String title);

	public void updateNoteOfUser(String userId, NoteDTO note);

	public void removeNoteOfUser(String userId, Long noteId);
}
