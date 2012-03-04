package de.htwk.openNoteKeeper.client.note.presenter.actionBar;

import com.google.gwt.user.client.ui.IsWidget;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

import de.htwk.openNoteKeeper.client.note.NoteEventBus;
import de.htwk.openNoteKeeper.client.note.view.actionBar.NoteActionBarViewImpl;
import de.htwk.openNoteKeeper.shared.UserDTO;

@Presenter(view = NoteActionBarViewImpl.class)
public class NoteActionBarPresenter extends
		BasePresenter<NoteActionBarViewImpl, NoteEventBus> {

	public interface NoteActionBarView extends IsWidget {

	}

	public void onLoggedIn(UserDTO user) {
		eventBus.setActionBar(view.asWidget());
	}

}
