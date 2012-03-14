package de.htwk.openNoteKeeper.client.note.presenter.whiteboard;

import java.util.HashMap;
import java.util.Map;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.DragController;
import com.allen_sauer.gwt.dnd.client.DragEndEvent;
import com.allen_sauer.gwt.dnd.client.DragHandler;
import com.allen_sauer.gwt.dnd.client.DragStartEvent;
import com.allen_sauer.gwt.dnd.client.VetoDragException;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

import de.htwk.openNoteKeeper.client.note.NoteEventBus;
import de.htwk.openNoteKeeper.client.note.service.NoteServiceAsync;
import de.htwk.openNoteKeeper.client.note.view.whiteboard.NoteViewImpl;
import de.htwk.openNoteKeeper.client.util.DragableWidget;
import de.htwk.openNoteKeeper.client.util.PresenterFactory;
import de.htwk.openNoteKeeper.client.util.StatusScreenCallback;
import de.htwk.openNoteKeeper.shared.CoordinateDTO;
import de.htwk.openNoteKeeper.shared.GroupDTO;
import de.htwk.openNoteKeeper.shared.NoteDTO;
import de.htwk.openNoteKeeper.shared.UserDTO;
import de.htwk.openNoteKeeper.shared.WhiteBoardDTO;

@Presenter(view = NoteViewImpl.class)
public class NotePresenter extends BasePresenter<NoteViewImpl, NoteEventBus> {

	@Inject
	private NoteServiceAsync noteService;

	private Map<Widget, NoteDTO> noteWidgets = new HashMap<Widget, NoteDTO>();

	public interface NoteView extends IsWidget {
		public void showNoteWidget(DragableWidget noteWidget, int left, int top);

		public void removeNoteWidget(Widget noteWidget);

		public DragController getDragController();

		public int getWhiteBoardAbsoluteLeft();

		public int getWhiteBoardAbsoluteTop();

		public void showGroupWidget(String groupName);

		public void cleanView();
	}

	@Override
	public void bind() {
		view.getDragController().addDragHandler(new DragHandler() {

			public void onPreviewDragStart(DragStartEvent event)
					throws VetoDragException {
			}

			public void onPreviewDragEnd(DragEndEvent event)
					throws VetoDragException {
			}

			public void onDragStart(DragStartEvent event) {
			}

			public void onDragEnd(DragEndEvent event) {
				NoteDTO note = getDraggedNote(event.getContext());
				if (note != null) {
					Widget noteWidget = event.getContext().draggable;
					int left = noteWidget.getAbsoluteLeft()
							- view.getWhiteBoardAbsoluteLeft();
					int top = noteWidget.getAbsoluteTop()
							- view.getWhiteBoardAbsoluteTop();
					note.setPosition(new CoordinateDTO(left, top));
					noteService
							.updateNote(note, new StatusScreenCallback<Void>(
									"aktualisiere Notiz") {

								@Override
								protected void success(Void result) {
								}
							});
				}
			}

			private NoteDTO getDraggedNote(DragContext context) {
				for (Widget noteWidget : noteWidgets.keySet()) {
					if (noteWidget.equals(context.draggable)) {
						return noteWidgets.get(context.draggable);
					}
				}
				return null;
			}
		});
	}

	public void onLoggedIn(UserDTO user) {
		eventBus.setContent(view.asWidget());
	}

	public void onSelectWhiteBoard(WhiteBoardDTO selectedWhiteBoard) {
		view.cleanView();
		noteWidgets.clear();

		for (NoteDTO note : selectedWhiteBoard.getNotes()) {
			onShowNote(note);
		}
	}

	public void onShowNote(NoteDTO note) {
		SingleNotePresenter presenter = PresenterFactory.createPresenter(
				eventBus, SingleNotePresenter.class);
		DragableWidget noteWidget = presenter.showNote(note);
		view.showNoteWidget(noteWidget, note.getPosition().getX(), note
				.getPosition().getY());
		noteWidgets.put(noteWidget.asWidget(), note);
	}

	public void onRemoveNote(NoteDTO note) {
		for (Widget noteWidget : noteWidgets.keySet()) {
			if (noteWidgets.get(noteWidget).equals(note)) {
				view.removeNoteWidget(noteWidget);
				noteWidgets.remove(noteWidget);
				return;
			}
		}
	}

	public void onSelectGroup(GroupDTO group) {
		view.cleanView();
		noteWidgets.clear();

		view.showGroupWidget(group.getTitle());
	}

}
