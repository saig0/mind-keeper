package de.htwk.openNoteKeeper.client.note.presenter.whiteboard;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

import de.htwk.openNoteKeeper.client.note.NoteEventBus;
import de.htwk.openNoteKeeper.client.note.service.NoteServiceAsync;
import de.htwk.openNoteKeeper.client.note.view.whiteboard.NoteViewImpl;
import de.htwk.openNoteKeeper.client.util.PresenterFactory;
import de.htwk.openNoteKeeper.shared.NoteDTO;
import de.htwk.openNoteKeeper.shared.UserDTO;
import de.htwk.openNoteKeeper.shared.WhiteBoardDTO;

@Presenter(view = NoteViewImpl.class)
public class NotePresenter extends BasePresenter<NoteViewImpl, NoteEventBus> {

	@Inject
	private NoteServiceAsync noteService;

	private final PresenterFactory<SingleNotePresenter, NoteEventBus> presenterFactory;

	public interface NoteView extends IsWidget {
		public void showNoteWidget(Widget noteWidget, int left, int top);
	}

	public NotePresenter() {
		presenterFactory = new PresenterFactory<SingleNotePresenter, NoteEventBus>(
				SingleNotePresenter.class);
	}

	public void onLoggedIn(UserDTO user) {
		eventBus.setContent(view.asWidget());
	}

	public void onSelectWhiteBoard(WhiteBoardDTO selectedWhiteBoard) {
		for (NoteDTO note : selectedWhiteBoard.getNotes()) {
			onShowNote(note);
		}
	}

	public void onShowNote(NoteDTO note) {
		SingleNotePresenter presenter = presenterFactory
				.createPresenter(eventBus);
		Widget noteWidget = presenter.showNote(note);
		view.showNoteWidget(noteWidget, note.getPosition().getX(), note
				.getPosition().getY());
	}
}
