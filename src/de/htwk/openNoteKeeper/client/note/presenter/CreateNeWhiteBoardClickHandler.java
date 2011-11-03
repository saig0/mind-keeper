package de.htwk.openNoteKeeper.client.note.presenter;

import com.google.gwt.event.dom.client.ClickEvent;

import de.htwk.openNoteKeeper.client.note.presenter.NavigationTreePresenter.NavigationTreeView;
import de.htwk.openNoteKeeper.client.note.service.NoteServiceAsync;
import de.htwk.openNoteKeeper.client.util.LoadingScreenCallback;
import de.htwk.openNoteKeeper.shared.GroupDTO;
import de.htwk.openNoteKeeper.shared.WhiteBoardDTO;

public class CreateNeWhiteBoardClickHandler extends CreateNewItemClickHandler {

	private final NoteServiceAsync noteService;

	public CreateNeWhiteBoardClickHandler(NavigationTreeView view,
			NoteServiceAsync noteService) {
		super(view);
		this.noteService = noteService;
	}

	@Override
	protected void showItemInputField() {
		view.showNewWhiteBoardInputField();
	}

	@Override
	void serviceCall(GroupDTO group, ClickEvent event, String newWhiteBoardName) {
		noteService.createWhiteBoard(group.getKey(), newWhiteBoardName,
				new LoadingScreenCallback<WhiteBoardDTO>(event) {

					@Override
					protected void success(WhiteBoardDTO whiteBoard) {
						view.addWhiteBoardToSelectedGroup(whiteBoard);
					}
				});
	}
}