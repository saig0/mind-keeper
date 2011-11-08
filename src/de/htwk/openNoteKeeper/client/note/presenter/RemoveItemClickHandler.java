package de.htwk.openNoteKeeper.client.note.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import de.htwk.openNoteKeeper.client.main.presenter.Session;
import de.htwk.openNoteKeeper.client.note.presenter.NavigationTreePresenter.NavigationTreeView;
import de.htwk.openNoteKeeper.client.note.service.NoteServiceAsync;
import de.htwk.openNoteKeeper.client.util.LoadingScreenCallback;
import de.htwk.openNoteKeeper.shared.GroupDTO;
import de.htwk.openNoteKeeper.shared.UserDTO;
import de.htwk.openNoteKeeper.shared.WhiteBoardDTO;

public class RemoveItemClickHandler implements ClickHandler {

	private final NavigationTreeView view;
	private final NoteServiceAsync noteService;

	public RemoveItemClickHandler(NavigationTreeView view,
			NoteServiceAsync noteService) {
		this.view = view;
		this.noteService = noteService;
	}

	public void onClick(ClickEvent event) {
		if (view.hasSelectedGroup()) {
			removeGroup(event);
		} else if (view.hasSelectedWhiteBoard()) {
			removeWhiteBoard(event);
		}
	}

	private void removeWhiteBoard(ClickEvent event) {
		WhiteBoardDTO whiteBoard = view.getSelectedWhiteBoard();
		noteService.removeWhiteBoard(whiteBoard.getKey(),
				new LoadingScreenCallback<Void>(event) {

					@Override
					protected void success(Void result) {
						view.removeSelectedWhiteBoard();
					}
				});
	}

	private void removeGroup(ClickEvent event) {
		GroupDTO group = view.getSelectedGroup();
		UserDTO user = Session.getCurrentUser();
		noteService.removeGroup(user.getId(), group.getKey(),
				new LoadingScreenCallback<Void>(event) {

					@Override
					protected void success(Void result) {
						view.removeSelectedGroup();
					}
				});
	}
}