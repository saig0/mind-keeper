package de.htwk.openNoteKeeper.client.note.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

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

	public void removeWhiteBoard(String whiteBoardKey);

	public NoteDTO createNote(String whiteBoardKey, NoteDTO note);

	public void updateNote(String userKey, NoteDTO note);

	public void removeNote(String noteKey);

	public void moveGroup(String groupKey, String targetGroupKey, Integer index);

	public void moveWhiteBoard(String whiteBoardKey, String targetGroupKey,
			Integer index);

	public void moveNote(String noteKey, String targetWhiteBoardKey);
}
