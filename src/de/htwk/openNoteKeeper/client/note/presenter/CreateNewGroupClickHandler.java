package de.htwk.openNoteKeeper.client.note.presenter;

import com.google.gwt.event.dom.client.ClickEvent;

import de.htwk.openNoteKeeper.client.main.presenter.Session;
import de.htwk.openNoteKeeper.client.note.presenter.NavigationTreePresenter.NavigationTreeView;
import de.htwk.openNoteKeeper.client.note.service.NoteServiceAsync;
import de.htwk.openNoteKeeper.client.util.LoadingScreenCallback;
import de.htwk.openNoteKeeper.shared.GroupDTO;
import de.htwk.openNoteKeeper.shared.UserDTO;

public class CreateNewGroupClickHandler extends CreateNewItemClickHandler {

	private final NoteServiceAsync noteService;

	public CreateNewGroupClickHandler(NavigationTreeView view,
			NoteServiceAsync noteService) {
		super(view);
		this.noteService = noteService;
	}

	@Override
	protected void showItemInputField() {
		view.showNewGroupInputField();
	}

	@Override
	void serviceCall(GroupDTO group, ClickEvent event, String newGroupName) {
		UserDTO user = Session.getCurrentUser();
		noteService.createGroupForUser(user.getId(), group.getKey(),
				newGroupName, new LoadingScreenCallback<GroupDTO>(event) {

					@Override
					protected void success(GroupDTO newGroup) {
						view.addGroupToSelectedGroup(newGroup);
					}
				});
	}
}