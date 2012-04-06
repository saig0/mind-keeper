package de.htwk.openNoteKeeper.client.note.presenter.actionBar;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

import de.htwk.openNoteKeeper.client.main.presenter.Session;
import de.htwk.openNoteKeeper.client.note.NoteEventBus;
import de.htwk.openNoteKeeper.client.note.service.NoteServiceAsync;
import de.htwk.openNoteKeeper.client.note.view.actionBar.NoteCreationViewImpl;
import de.htwk.openNoteKeeper.client.util.LoadingScreenCallback;
import de.htwk.openNoteKeeper.shared.CoordinateDTO;
import de.htwk.openNoteKeeper.shared.GroupDTO;
import de.htwk.openNoteKeeper.shared.NoteDTO;
import de.htwk.openNoteKeeper.shared.UserDTO;
import de.htwk.openNoteKeeper.shared.WhiteBoardDTO;

@Presenter(view = NoteCreationViewImpl.class)
public class NoteCreationPresenter extends
		BasePresenter<NoteCreationViewImpl, NoteEventBus> {

	@Inject
	private NoteServiceAsync noteService;

	private WhiteBoardDTO activeWhiteBoard;
	private NoteDTO note;

	public interface NoteCreationView extends IsWidget {
		public void showForCreation();

		public void showForEdit();

		public void hide();

		public String getName();

		public String getColor();

		public void setName(String name);

		public void setColor(String color);

		public HasClickHandlers getCreateButton();

		public HasClickHandlers getAbortButton();
	}

	@Override
	public void bind() {
		view.getCreateButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				String nodeName = view.getName();
				if (!nodeName.isEmpty()) {
					String color = view.getColor();

					if (note == null) {
						// TODO position bestimmen
						int left = (Window.getClientWidth() / 2)
								- (int) (Math.random() * 100);
						int top = (Window.getClientHeight() / 2)
								+ (int) (Math.random() * 100);
						CoordinateDTO position = new CoordinateDTO(left, top);
						CoordinateDTO size = new CoordinateDTO(200, 200);

						noteService.createNote(activeWhiteBoard.getKey(),
								new NoteDTO("", nodeName, "", color, position,
										size),
								new LoadingScreenCallback<NoteDTO>(event) {

									@Override
									protected void success(NoteDTO note) {
										view.hide();
										eventBus.showNote(note);
										// TODO Modell auf Client
										// aktualliesieren
										eventBus.loggedIn(Session
												.getCurrentUser());
									}
								});
					} else {
						note.setTitle(nodeName);
						note.setColor(color);
						UserDTO user = Session.getCurrentUser();
						noteService.updateNote(user.getId(), note,
								new LoadingScreenCallback<Void>(event) {

									@Override
									protected void success(Void result) {
										view.hide();
										eventBus.removeNote(note);
										eventBus.showNote(note);
										note = null;
										// TODO Modell auf Client
										// aktualliesieren
										eventBus.loggedIn(Session
												.getCurrentUser());
									}
								});
					}
				}
			}
		});

		view.getAbortButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				view.hide();
			}
		});
	}

	public void onShowNoteCreationView() {
		if (activeWhiteBoard != null) {
			view.showForCreation();
		}
	}

	public void onShowNoteEditView(NoteDTO note) {
		if (note != null) {
			this.note = note;
			view.setName(note.getTitle());
			view.setColor(note.getColor());
			view.showForEdit();
		}
	}

	public void onSelectWhiteBoard(WhiteBoardDTO selectedWhiteBoard) {
		this.activeWhiteBoard = selectedWhiteBoard;
	}

	public void onSelectGroup(GroupDTO group) {
		this.activeWhiteBoard = null;
	}
}
