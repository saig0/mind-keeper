package de.htwk.openNoteKeeper.client.note.presenter.whiteboard;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

import de.htwk.openNoteKeeper.client.note.NoteEventBus;
import de.htwk.openNoteKeeper.client.note.service.NoteServiceAsync;
import de.htwk.openNoteKeeper.client.note.view.whiteboard.NoteViewImpl;
import de.htwk.openNoteKeeper.shared.UserDTO;

@Presenter(view = NoteViewImpl.class)
public class NotePresenter extends BasePresenter<NoteViewImpl, NoteEventBus> {

	@Inject
	private NoteServiceAsync noteService;

	public interface NoteView extends IsWidget {

	}

	public void onLoggedIn(UserDTO user) {
		eventBus.setContent(view.asWidget());
	}
}
