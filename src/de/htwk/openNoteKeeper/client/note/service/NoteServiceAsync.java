package de.htwk.openNoteKeeper.client.note.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.htwk.openNoteKeeper.shared.GroupDTO;
import de.htwk.openNoteKeeper.shared.NoteDTO;
import de.htwk.openNoteKeeper.shared.WhiteBoardDTO;

public interface NoteServiceAsync {

	void getAllGroupsForUser(String userKey,
			AsyncCallback<List<GroupDTO>> callback);

	void createGroupForUser(String userKey, String parentGroupKey,
			String title, AsyncCallback<GroupDTO> callback);

	void removeGroup(String userKey, String groupKey,
			AsyncCallback<Void> callback);

	void createWhiteBoard(String groupKey, String title,
			AsyncCallback<WhiteBoardDTO> callback);

	void removeWhiteBoard(String whiteBoardKey, AsyncCallback<Void> callback);

	void createNote(String whiteBoardKey, NoteDTO note,
			AsyncCallback<NoteDTO> callback);

	void updateNote(NoteDTO note, AsyncCallback<Void> callback);

	void removeNote(String noteKey, AsyncCallback<Void> callback);

	void moveGroup(String groupKey, String targetGroupKey, Integer index,
			AsyncCallback<Void> callback);

	void moveWhiteBoard(String whiteBoardKey, String targetGroupKey,
			Integer index, AsyncCallback<Void> callback);

	void moveNote(String noteKey, String targetWhiteBoardKey,
			AsyncCallback<Void> callback);

}
