package de.htwk.openNoteKeeper.client.note.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import de.htwk.openNoteKeeper.client.main.presenter.Session;
import de.htwk.openNoteKeeper.client.note.presenter.NavigationTreePresenter.NavigationInputView;
import de.htwk.openNoteKeeper.client.note.presenter.NavigationTreePresenter.NavigationTreeView;
import de.htwk.openNoteKeeper.client.note.service.NoteServiceAsync;
import de.htwk.openNoteKeeper.client.util.StatusScreenCallback;
import de.htwk.openNoteKeeper.client.util.StatusScreenCallback.Status;
import de.htwk.openNoteKeeper.shared.GroupDTO;
import de.htwk.openNoteKeeper.shared.UserDTO;
import de.htwk.openNoteKeeper.shared.WhiteBoardDTO;

public class SaveInputClickHandler implements ClickHandler {

	private final NavigationInputView navigationInputView;
	private final GroupDTO group;
	private final Type type;

	private final NavigationTreeView view;
	private final NoteServiceAsync noteService;

	public enum Type {
		Group, WhiteBoard
	}

	public SaveInputClickHandler(NoteServiceAsync noteService,
			NavigationTreeView view, NavigationInputView navigationInputView,
			GroupDTO group, Type type) {
		this.view = view;
		this.noteService = noteService;
		this.navigationInputView = navigationInputView;
		this.group = group;
		this.type = type;
	}

	public void onClick(ClickEvent event) {
		String newGroupName = navigationInputView.getNameOfInputField();

		if (!newGroupName.isEmpty()) {
			navigationInputView.hide();

			switch (type) {
			case Group:
				createGroup(group, newGroupName);
				break;
			case WhiteBoard:
				createWhitBoard(group, newGroupName);
				break;
			}
		}
	}

	private void createGroup(final GroupDTO group, String newGroupName) {
		UserDTO user = Session.getCurrentUser();
		noteService.createGroupForUser(user.getId(), group.getKey(),
				newGroupName, new StatusScreenCallback<GroupDTO>(
						Status.Add_Group) {

					@Override
					protected void success(GroupDTO newGroup) {
						view.addGroupToTree(newGroup);
					}
				});
	}

	private void createWhitBoard(final GroupDTO group, String newGroupName) {
		noteService.createWhiteBoard(group.getKey(), newGroupName,
				new StatusScreenCallback<WhiteBoardDTO>(Status.Add_Whiteboard) {

					@Override
					protected void success(WhiteBoardDTO whiteboard) {
						view.addWhiteBoardToGroup(whiteboard);
					}
				});
	}
}
