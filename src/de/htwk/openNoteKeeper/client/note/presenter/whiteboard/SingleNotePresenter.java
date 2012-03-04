package de.htwk.openNoteKeeper.client.note.presenter.whiteboard;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

import de.htwk.openNoteKeeper.client.note.NoteEventBus;
import de.htwk.openNoteKeeper.client.note.view.whiteboard.SingleNoteViewImpl;
import de.htwk.openNoteKeeper.shared.NoteDTO;

@Presenter(view = SingleNoteViewImpl.class, multiple = true)
public class SingleNotePresenter extends
		BasePresenter<SingleNoteViewImpl, NoteEventBus> {

	public interface SingleNoteView extends IsWidget {

		public void setSize(int width, int height);
	}

	public Widget showNote(NoteDTO note) {
		// TODO set data
		view.setSize(note.getSize().getX(), note.getSize().getY());
		return view.asWidget();
	}
}
