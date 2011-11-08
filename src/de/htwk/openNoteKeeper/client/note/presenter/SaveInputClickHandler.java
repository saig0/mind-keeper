package de.htwk.openNoteKeeper.client.note.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.TreeItem;

import de.htwk.openNoteKeeper.client.main.presenter.Session;
import de.htwk.openNoteKeeper.client.note.presenter.NavigationTreePresenter.NavigationInputView;
import de.htwk.openNoteKeeper.client.note.presenter.NavigationTreePresenter.NavigationTreeView;
import de.htwk.openNoteKeeper.client.note.service.NoteServiceAsync;
import de.htwk.openNoteKeeper.client.util.LoadingScreenCallback;
import de.htwk.openNoteKeeper.shared.GroupDTO;
import de.htwk.openNoteKeeper.shared.UserDTO;
import de.htwk.openNoteKeeper.shared.WhiteBoardDTO;

public class SaveInputClickHandler implements ClickHandler {

	private final NavigationInputView navigationInputView;
	private final TreeItem dropTreeItem;
	private final DragableWidget dragWidget;

	private final NavigationTreeView view;
	private final NoteServiceAsync noteService;

	public SaveInputClickHandler(NoteServiceAsync noteService,
			NavigationTreeView view, NavigationInputView navigationInputView,
			TreeItem dropTreeItem, DragableWidget dragWidget) {
		this.view = view;
		this.noteService = noteService;
		this.navigationInputView = navigationInputView;
		this.dropTreeItem = dropTreeItem;
		this.dragWidget = dragWidget;
	}

	public void onClick(ClickEvent event) {
		String newGroupName = navigationInputView.getNameOfInputField();

		if (!newGroupName.isEmpty()) {
			navigationInputView.hide();

			if (dragWidget instanceof GroupDragWidget) {
				createGroup(dropTreeItem, event, newGroupName);
			} else if (dragWidget instanceof WhiteBoardDragWidget) {
				createWhitBoard(dropTreeItem, event, newGroupName);
			}

		}
	}

	private void createGroup(final TreeItem dropTreeItem, ClickEvent event,
			String newGroupName) {
		GroupDTO group = (GroupDTO) dropTreeItem.getUserObject();

		UserDTO user = Session.getCurrentUser();
		noteService.createGroupForUser(user.getId(), group.getKey(),
				newGroupName, new LoadingScreenCallback<GroupDTO>(event) {

					@Override
					protected void success(GroupDTO newGroup) {
						view.addGroupToTree(dropTreeItem, newGroup);
					}
				});
	}

	private void createWhitBoard(final TreeItem dropTreeItem, ClickEvent event,
			String newGroupName) {
		GroupDTO group = (GroupDTO) dropTreeItem.getUserObject();
		noteService.createWhiteBoard(group.getKey(), newGroupName,
				new LoadingScreenCallback<WhiteBoardDTO>(event) {

					@Override
					protected void success(WhiteBoardDTO whiteboard) {
						view.addWhiteBoardToGroup(dropTreeItem, whiteboard);
					}
				});
	}
}
