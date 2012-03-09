package de.htwk.openNoteKeeper.client.note.presenter.whiteboard;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

import de.htwk.openNoteKeeper.client.main.presenter.Session;
import de.htwk.openNoteKeeper.client.note.NoteEventBus;
import de.htwk.openNoteKeeper.client.note.service.NoteServiceAsync;
import de.htwk.openNoteKeeper.client.note.view.whiteboard.NoteMoveViewImpl;
import de.htwk.openNoteKeeper.client.util.LoadingScreenCallback;
import de.htwk.openNoteKeeper.client.util.StatusScreenCallback;
import de.htwk.openNoteKeeper.client.util.StatusScreenCallback.Status;
import de.htwk.openNoteKeeper.shared.GroupDTO;
import de.htwk.openNoteKeeper.shared.NoteDTO;
import de.htwk.openNoteKeeper.shared.WhiteBoardDTO;

@Presenter(view = NoteMoveViewImpl.class)
public class NoteMovePresenter extends
		BasePresenter<NoteMoveViewImpl, NoteEventBus> {

	public interface NoteMoveView extends IsWidget {
		public void setGroups(List<GroupDTO> groups);

		public void expandTree(NoteDTO note);

		public void show();

		public void hide();

		public HasClickHandlers getAbortButton();

		public HasClickHandlers getSaveButton();

		public WhiteBoardDTO getSelectedWhiteBoard();
	}

	@Inject
	private NoteServiceAsync noteService;

	private NoteDTO note;
	private WhiteBoardDTO whiteBoard;

	@Override
	public void bind() {
		view.getAbortButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				view.hide();
			}
		});

		view.getSaveButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				final WhiteBoardDTO selectedWhiteBoard = view
						.getSelectedWhiteBoard();
				if (selectedWhiteBoard != null) {
					if (!selectedWhiteBoard.getKey()
							.equals(whiteBoard.getKey())) {
						noteService.moveNote(note.getKey(),
								selectedWhiteBoard.getKey(),
								new LoadingScreenCallback<Void>(event) {

									@Override
									protected void success(Void result) {
										eventBus.removeNote(note);
										view.hide();
										eventBus.loggedIn(Session
												.getCurrentUser());
									}
								});
					} else {
						view.hide();
					}
				}
			}
		});

	}

	public void onShowNoteMoveView(final NoteDTO note) {
		this.note = note;
		noteService.getAllGroupsForUser(Session.getCurrentUser().getId(),
				new StatusScreenCallback<List<GroupDTO>>(Status.Loading) {

					@Override
					protected void success(List<GroupDTO> groups) {
						view.setGroups(groups);
						view.expandTree(note);
						view.show();
					}
				});
	}

	public void onSelectWhiteBoard(WhiteBoardDTO selectedWhiteBoard) {
		this.whiteBoard = selectedWhiteBoard;
	}
}
