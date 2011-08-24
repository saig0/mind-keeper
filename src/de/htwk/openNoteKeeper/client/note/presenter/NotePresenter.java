package de.htwk.openNoteKeeper.client.note.presenter;

import com.google.gwt.user.client.ui.IsWidget;
import com.mvp4g.client.presenter.BasePresenter;

import de.htwk.openNoteKeeper.client.note.NoteEventBus;
import de.htwk.openNoteKeeper.client.note.view.NoteViewImpl;

public class NotePresenter extends BasePresenter<NoteViewImpl, NoteEventBus> {

	public interface NoteView extends IsWidget {

	}
}
