package de.htwk.openNoteKeeper.client.note.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.htwk.openNoteKeeper.shared.CoordinateDTO;
import de.htwk.openNoteKeeper.shared.GroupDTO;
import de.htwk.openNoteKeeper.shared.NoteDTO;
import de.htwk.openNoteKeeper.shared.WhiteBoardDTO;

public interface NoteServiceAsync {

	void getAllGroupsForUser(String userId,
			AsyncCallback<List<GroupDTO>> callback);

	void createGroupForUser(String userId, GroupDTO parentGroup, String title,
			AsyncCallback<GroupDTO> callback);

	void removeGroup(GroupDTO group, AsyncCallback<Void> callback);

	void createWhiteBoard(GroupDTO group, String title,
			AsyncCallback<WhiteBoardDTO> callback);

	void removeWhiteBoard(WhiteBoardDTO whiteBoard, AsyncCallback<Void> callback);

	void createNote(WhiteBoardDTO whiteBoard, String title,
			CoordinateDTO position, CoordinateDTO size,
			AsyncCallback<NoteDTO> callback);

	void updateNote(NoteDTO note, AsyncCallback<Void> callback);

	void removeNote(NoteDTO note, AsyncCallback<Void> callback);

}
