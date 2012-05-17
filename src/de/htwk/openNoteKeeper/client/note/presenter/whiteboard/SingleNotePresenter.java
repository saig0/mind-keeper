package de.htwk.openNoteKeeper.client.note.presenter.whiteboard;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.inject.Inject;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

import de.htwk.openNoteKeeper.client.main.presenter.Session;
import de.htwk.openNoteKeeper.client.note.NoteEventBus;
import de.htwk.openNoteKeeper.client.note.service.NoteServiceAsync;
import de.htwk.openNoteKeeper.client.note.view.whiteboard.SingleNoteViewImpl;
import de.htwk.openNoteKeeper.client.util.DragableWidget;
import de.htwk.openNoteKeeper.client.util.PresenterFactory;
import de.htwk.openNoteKeeper.client.util.StatusScreenCallback;
import de.htwk.openNoteKeeper.client.widget.ConfirmationPopup;
import de.htwk.openNoteKeeper.client.widget.ConfirmationPopup.SaveAction;
import de.htwk.openNoteKeeper.client.widget.resize.HasResizeListener;
import de.htwk.openNoteKeeper.client.widget.resize.ResizeListener;
import de.htwk.openNoteKeeper.shared.CoordinateDTO;
import de.htwk.openNoteKeeper.shared.NoteDTO;
import de.htwk.openNoteKeeper.shared.UserDTO;

@Presenter(view = SingleNoteViewImpl.class, multiple = true)
public class SingleNotePresenter extends
		BasePresenter<SingleNoteViewImpl, NoteEventBus> {

	public interface SingleNoteView extends DragableWidget {
		public void setTitle(String title);

		public void setContent(String content);

		public void setSize(int width, int height);

		public HasResizeListener getResizableWidget();

		public HasClickHandlers getDeleteButton();

		public HasClickHandlers getEditButton();

		public HasClickHandlers getMoveButton();

		public void hide();

		public HasClickHandlers getSaveButton();

		public String getContentOfEditor();

		public void setColor(String color);

		public HasClickHandlers getEditorButton();

		public void showEditor();

		public void hideEditor();

		public boolean isEditorVisible();
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
				UserDTO user = Session.getCurrentUser();
				noteService.updateNote(user.getId(), note,
						new StatusScreenCallback<Void>("aktuallisiere Notiz") {

							@Override
							protected void success(Void result) {
								// just background
								// TODO Modell auf Client aktualliesieren
								eventBus.loggedIn(Session.getCurrentUser());
							}
						});
			}
		});

		view.getDeleteButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				view.hide();
				new ConfirmationPopup("Notiz " + note.getTitle() + " löschen?",
						new SaveAction() {

							public void run() {
								noteService.removeNote(note.getKey(),
										new StatusScreenCallback<Void>(
												"lösche Notiz") {

											@Override
											protected void success(Void result) {
												view.hide();
												eventBus.removeNote(note);
												// TODO Modell auf Client
												// aktualliesieren
												eventBus.loggedIn(Session
														.getCurrentUser());
												destroy();
											}
										});
							}
						}).show();
			}
		});

		view.getEditButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				view.hide();
				eventBus.showNoteEditView(note);
			}
		});

		view.getMoveButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				view.hide();
				eventBus.showNoteMoveView(note);
			}
		});

		view.getEditorButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				eventBus.hideEditor();
				view.showEditor();
			}
		});

		view.getSaveButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				updateNote();
			}
		});
	}

	private void updateNote() {
		final String newContent = view.getContentOfEditor();
		if (newContent != null) {
			UserDTO user = Session.getCurrentUser();
			note.setContent(newContent);
			noteService.updateNote(user.getId(), note,
					new StatusScreenCallback<Void>("aktuallisiere Notiz") {

						@Override
						protected void success(Void result) {
							// TODO Modell auf Client aktualliesieren
							eventBus.loggedIn(Session.getCurrentUser());

							view.setContent(newContent);
							view.hideEditor();
						}
					});
		}
	}

	public DragableWidget showNote(NoteDTO note) {
		this.note = note;
		view.setTitle(note.getTitle());
		view.setContent(note.getContent());
		view.setColor(note.getColor());
		view.setSize(note.getSize().getX(), note.getSize().getY());
		return view;
	}

	private void destroy() {
		PresenterFactory.destroyPresenter(eventBus, this);
	}

	public void onHideEditor() {
		if (view.isEditorVisible()) {
			updateNote();
		} else {
			view.hideEditor();
		}
	}
}
