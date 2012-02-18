package de.htwk.openNoteKeeper.client.note.presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import de.htwk.openNoteKeeper.client.main.MainModule;
import de.htwk.openNoteKeeper.client.main.presenter.Session;
import de.htwk.openNoteKeeper.client.note.presenter.NavigationTreePresenter.NavigationTreeView;
import de.htwk.openNoteKeeper.client.note.service.NoteServiceAsync;
import de.htwk.openNoteKeeper.client.util.StatusScreenCallback;
import de.htwk.openNoteKeeper.client.util.StatusScreenCallback.Status;
import de.htwk.openNoteKeeper.client.widget.StatusArea;
import de.htwk.openNoteKeeper.client.widget.StatusPanel;
import de.htwk.openNoteKeeper.shared.GroupDTO;
import de.htwk.openNoteKeeper.shared.UserDTO;
import de.htwk.openNoteKeeper.shared.WhiteBoardDTO;

public class RemoveItemClickHandler implements ClickHandler {

	private final NavigationTreeView view;
	private final NoteServiceAsync noteService;

	private MainModule mainModule = GWT.create(MainModule.class);

	public RemoveItemClickHandler(NavigationTreeView view,
			NoteServiceAsync noteService) {
		this.view = view;
		this.noteService = noteService;
	}

	public void onClick(ClickEvent event) {
		if (view.hasSelectedGroup()) {
			removeGroup();
		} else if (view.hasSelectedWhiteBoard()) {
			removeWhiteBoard();
		}
	}

	private void removeWhiteBoard() {
		final WhiteBoardDTO whiteBoard = view.getSelectedWhiteBoard();
		noteService.removeWhiteBoard(whiteBoard.getKey(),
				new StatusScreenCallback<Void>(Status.Remove_Whiteboard) {

					@Override
					protected void success(Void result) {
						view.removeSelectedWhiteBoard();

						StatusArea statusArea = mainModule.getStatusArea();
						statusArea.addStatusMessage(new StatusPanel(
								"Whiteboard: " + whiteBoard.getTitle()
										+ " gelöscht", true, 5));
					}
				});
	}

	private void removeGroup() {
		final GroupDTO group = view.getSelectedGroup();
		UserDTO user = Session.getCurrentUser();
		noteService.removeGroup(user.getId(), group.getKey(),
				new StatusScreenCallback<Void>(Status.Remove_Group) {

					@Override
					protected void success(Void result) {
						view.removeSelectedGroup();

						StatusArea statusArea = mainModule.getStatusArea();
						statusArea.addStatusMessage(new StatusPanel("Gruppe: "
								+ group.getTitle() + " gelöscht", true, 5));
					}
				});
	}
}