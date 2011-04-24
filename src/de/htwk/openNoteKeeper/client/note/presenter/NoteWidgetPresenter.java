package de.htwk.openNoteKeeper.client.note.presenter;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.DragRepositionStopEvent;
import com.smartgwt.client.widgets.events.DragRepositionStopHandler;
import com.smartgwt.client.widgets.events.DragResizeStopEvent;
import com.smartgwt.client.widgets.events.DragResizeStopHandler;
import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.events.HasDragRepositionStopHandlers;
import com.smartgwt.client.widgets.events.HasDragResizeStopHandlers;
import com.smartgwt.client.widgets.events.HasMouseDownHandlers;
import com.smartgwt.client.widgets.events.MouseDownEvent;
import com.smartgwt.client.widgets.events.MouseDownHandler;

import de.htwk.openNoteKeeper.client.note.NoteEventBus;
import de.htwk.openNoteKeeper.client.note.service.NoteServiceAsync;
import de.htwk.openNoteKeeper.client.note.view.NoteWidget;
import de.htwk.openNoteKeeper.client.util.AbstractCallback;
import de.htwk.openNoteKeeper.client.util.LoadingScreenCallback;
import de.htwk.openNoteKeeper.shared.Coordinate;
import de.htwk.openNoteKeeper.shared.NoteDTO;
import de.htwk.openNoteKeeper.shared.UserDTO;

@Presenter(view = NoteWidget.class, multiple = true)
public class NoteWidgetPresenter extends
		BasePresenter<NoteWidget, NoteEventBus> {

	public interface NoteWidgetView extends IsWidget {
		public HasClickHandlers getSaveNoteButton();

		public HasMouseDownHandlers getWidget();

		public HasDragRepositionStopHandlers getDragWidget();

		public HasDragResizeStopHandlers getResizeWidget();

		public NoteDTO getNote();

		public void setNote(NoteDTO note);

		public void setPosition(Coordinate position);

		public Coordinate getPosition();

		public Coordinate getSize();

		public void setSize(Coordinate size);

		public void switchToEditor();

		public void switchToContent();
	}

	@Inject
	private NoteServiceAsync noteService;

	@Override
	public void bind() {
		view.getWidget().addMouseDownHandler(new MouseDownHandler() {

			public void onMouseDown(MouseDownEvent event) {
				eventBus.editNoteWidget(view);
			}
		});
		view.getSaveNoteButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				NoteDTO dto = view.getNote();
				updateNote(event, dto);
			}
		});
		view.getDragWidget().addDragRepositionStopHandler(
				new DragRepositionStopHandler() {

					public void onDragRepositionStop(
							DragRepositionStopEvent event) {
						NoteDTO dto = view.getNote();
						dto.setPosition(view.getPosition());
						updateNote(event, dto);
					}
				});
		view.getResizeWidget().addDragResizeStopHandler(
				new DragResizeStopHandler() {

					public void onDragResizeStop(DragResizeStopEvent event) {
						NoteDTO dto = view.getNote();
						dto.setSize(view.getSize());
						updateNote(event, dto);
					}
				});
	}

	private UserDTO user;

	public void onCreateNewNote(UserDTO user, String title, Coordinate position) {
		this.user = user;

		noteService.createNoteForUser(user.getId(), title, position,
				new Coordinate(200, 200), new AbstractCallback<NoteDTO>() {

					@Override
					protected void success(NoteDTO dto) {
						view.setNote(dto);
						eventBus.newNoteCreated(view);
					}
				});
	}

	public void onShowNote(UserDTO user, NoteDTO dto) {
		this.user = user;

		view.setNote(dto);
		eventBus.noteCreated(view);
	}

	private void updateNote(GwtEvent<?> event, NoteDTO dto) {
		noteService.updateNoteOfUser(user.getId(), dto,
				new LoadingScreenCallback<Void>(event) {

					@Override
					protected void success(Void result) {
						// saved
					}
				});
	}
}