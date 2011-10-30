package de.htwk.openNoteKeeper.client.note.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.htwk.openNoteKeeper.shared.CoordinateDTO;
import de.htwk.openNoteKeeper.shared.GroupDTO;
import de.htwk.openNoteKeeper.shared.NoteDTO;
import de.htwk.openNoteKeeper.shared.WhiteBoardDTO;

@RemoteServiceRelativePath("note")
public interface NoteService extends RemoteService {

	public List<GroupDTO> getAllGroupsForUser(String userKey);

	public GroupDTO createGroupForUser(String userKey, String parentGroupKey,
			String title);

	public void removeGroup(String userKey, String groupKey);

	public WhiteBoardDTO createWhiteBoard(String groupKey, String title);

	public void removeWhiteBoard(WhiteBoardDTO whiteBoard);

	public NoteDTO createNote(WhiteBoardDTO whiteBoard, String title,
			CoordinateDTO position, CoordinateDTO size);

	public void updateNote(NoteDTO note);

	public void removeNote(NoteDTO note);
}
