package de.htwk.openNoteKeeper.client.main.presenter;

import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

import de.htwk.openNoteKeeper.client.main.MainEventBus;
import de.htwk.openNoteKeeper.client.main.service.NoteServiceAsync;
import de.htwk.openNoteKeeper.client.main.view.NoteViewImpl;
import de.htwk.openNoteKeeper.client.util.AbstractCallback;
import de.htwk.openNoteKeeper.shared.NoteDTO;
import de.htwk.openNoteKeeper.shared.UserDTO;

@Presenter(view = NoteViewImpl.class)
public class NotePresenter extends BasePresenter<NoteViewImpl, MainEventBus> {

	public interface NoteView extends IsWidget {
		public void setNotes(List<NoteDTO> notes);
	}

	@Inject
	private NoteServiceAsync noteService;

	@Override
	public void bind() {

	}

	private UserDTO user;

	public void onLoggedIn(UserDTO user) {
		this.user = user;
		// initView(user);
		eventBus.setContent(view.asWidget());
	}

	private void initView(UserDTO user) {
		noteService.getAllNotesForUser(user.getId(),
				new AbstractCallback<List<NoteDTO>>() {

					@Override
					protected void success(List<NoteDTO> notes) {
						view.setNotes(notes);
					}
				});
	}

}