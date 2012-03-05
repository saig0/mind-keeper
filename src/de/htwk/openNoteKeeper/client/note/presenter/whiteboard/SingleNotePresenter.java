package de.htwk.openNoteKeeper.client.note.presenter.whiteboard;

import com.google.inject.Inject;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

import de.htwk.openNoteKeeper.client.note.NoteEventBus;
import de.htwk.openNoteKeeper.client.note.service.NoteServiceAsync;
import de.htwk.openNoteKeeper.client.note.view.whiteboard.SingleNoteViewImpl;
import de.htwk.openNoteKeeper.client.util.DragableWidget;
import de.htwk.openNoteKeeper.client.util.StatusScreenCallback;
import de.htwk.openNoteKeeper.client.widget.resize.HasResizeListener;
import de.htwk.openNoteKeeper.client.widget.resize.ResizeListener;
import de.htwk.openNoteKeeper.shared.CoordinateDTO;
import de.htwk.openNoteKeeper.shared.NoteDTO;

@Presenter(view = SingleNoteViewImpl.class, multiple = true)
public class SingleNotePresenter extends
		BasePresenter<SingleNoteViewImpl, NoteEventBus> {

	public interface SingleNoteView extends DragableWidget {
		public void setTitle(String title);

		public void setContent(String content);

		public void setSize(int width, int height);

		public HasResizeListener getResizableWidget();
	}

	@Inject
	private NoteServiceAsync noteService;

	private NoteDTO note;

	@Override
	public void bind() {
		view.getResizableWidget().addResizedListener(new ResizeListener() {

			public void onResized(Integer width, Integer height) {
			}

			public void onReleasedResized(Integer width, Integer height) {
				note.setSize(new CoordinateDTO(width, height));
				noteService.updateNote(note, new StatusScreenCallback<Void>(
						"aktuallisiere Notiz") {

					@Override
					protected void success(Void result) {
						// just background
					}
				});
			}
		});
	}

	public DragableWidget showNote(NoteDTO note) {
		this.note = note;
		// TODO set data
		view.setTitle(note.getTitle());
		view.setContent(note.getContent());
		view.setSize(note.getSize().getX(), note.getSize().getY());
		return view;
	}
}
