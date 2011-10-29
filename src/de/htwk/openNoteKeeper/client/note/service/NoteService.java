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

	public List<GroupDTO> getAllGroupsForUser(String userId);

	public GroupDTO createGroupForUser(String userId, GroupDTO parentGroup,
			String title);

	public void removeGroup(GroupDTO group);

	public WhiteBoardDTO createWhiteBoard(GroupDTO group, String title);

	public void removeWhiteBoard(WhiteBoardDTO whiteBoard);

	public NoteDTO createNote(WhiteBoardDTO whiteBoard, String title,
			CoordinateDTO position, CoordinateDTO size);

	public void updateNote(NoteDTO note);

	public void removeNote(NoteDTO note);
}
