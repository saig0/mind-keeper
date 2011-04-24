package de.htwk.openNoteKeeper.client.note.presenter;

import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.smartgwt.client.widgets.events.DropEvent;
import com.smartgwt.client.widgets.events.DropHandler;
import com.smartgwt.client.widgets.events.HasDropHandlers;

import de.htwk.openNoteKeeper.client.note.NoteEventBus;
import de.htwk.openNoteKeeper.client.note.presenter.NoteWidgetPresenter.NoteWidgetView;
import de.htwk.openNoteKeeper.client.note.service.NoteServiceAsync;
import de.htwk.openNoteKeeper.client.note.view.NoteViewImpl;
import de.htwk.openNoteKeeper.client.util.AbstractCallback;
import de.htwk.openNoteKeeper.shared.NoteDTO;
import de.htwk.openNoteKeeper.shared.UserDTO;

@Presenter(view = NoteViewImpl.class)
public class NotePresenter extends BasePresenter<NoteViewImpl, NoteEventBus> {

	public interface NoteView extends IsWidget {
		public HasDropHandlers getDeleteNoteButton();

		public HasDropHandlers getCreateNewNoteButton();

		public void addNewNoteWidget(NoteWidgetView noteWidget);

		public void addNoteWidget(NoteWidgetView noteWidget);

		public void removeNoteWidget(NoteWidgetView noteWidget);
	}

	@Inject
	private NoteServiceAsync noteService;

	private NoteWidgetView noteWidget;

	@Override
	public void bind() {
		view.getDeleteNoteButton().addDropHandler(new DropHandler() {

			public void onDrop(DropEvent event) {
				view.removeNoteWidget(noteWidget);
				NoteWidgetPresenterFactory.destroyPresenter(noteWidget);

				noteService.removeNoteOfUser(user.getId(),
						noteWidget.getNote(), new AbstractCallback<Void>() {

							@Override
							protected void success(Void result) {
								// removed
							}
						});
			}
		});

		view.getCreateNewNoteButton().addDropHandler(new DropHandler() {

			public void onDrop(DropEvent event) {
				eventBus.showConfigurationNoteView(event.getX(), event.getY());
			}
		});
	}

	private UserDTO user;

	public void onLoggedIn(UserDTO user) {
		this.user = user;

		eventBus.setContent(view.asWidget());

		loadNotesFromUser();
	}

	private void loadNotesFromUser() {
		noteService.getAllNotesForUser(user.getId(),
				new AbstractCallback<List<NoteDTO>>() {

					@Override
					protected void success(List<NoteDTO> notes) {
						for (NoteDTO note : notes) {
							NoteWidgetPresenter presenter = NoteWidgetPresenterFactory
									.createPresenter(eventBus);
							presenter.onShowNote(user, note);
						}
					}
				});
	}

	public void onNewNoteCreated(NoteWidgetView noteWidget) {
		view.addNewNoteWidget(noteWidget);
	}

	public void onNoteCreated(NoteWidgetView noteWidget) {
		view.addNoteWidget(noteWidget);
	}

	public void onEditNoteWidget(NoteWidgetView noteWidget) {
		this.noteWidget = noteWidget;
	}

}