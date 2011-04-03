package de.htwk.openNoteKeeper.client.note.presenter;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.events.HasMouseDownHandlers;
import com.smartgwt.client.widgets.events.MouseDownEvent;
import com.smartgwt.client.widgets.events.MouseDownHandler;

import de.htwk.openNoteKeeper.client.note.NoteEventBus;
import de.htwk.openNoteKeeper.client.note.service.NoteServiceAsync;
import de.htwk.openNoteKeeper.client.note.view.NoteWidget;
import de.htwk.openNoteKeeper.client.util.AbstractCallback;
import de.htwk.openNoteKeeper.client.util.LoadingScreenCallback;
import de.htwk.openNoteKeeper.shared.NoteDTO;
import de.htwk.openNoteKeeper.shared.UserDTO;

@Presenter(view = NoteWidget.class, multiple = true)
public class NoteWidgetPresenter extends
		BasePresenter<NoteWidget, NoteEventBus> {

	public interface NoteWidgetView extends IsWidget {
		public HasClickHandlers getSaveNoteButton();

		public HasMouseDownHandlers getWidget();

		public NoteDTO getNote();

		public void setNote(NoteDTO note);

		public void setPosition(int left, int top);

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
				noteService.updateNoteOfUser(user.getId(), dto,
						new LoadingScreenCallback<Void>(event) {

							@Override
							protected void success(Void result) {
								// saved
							}
						});
			}
		});
	}

	private UserDTO user;

	public void onCreateNewNote(UserDTO user, String title) {
		this.user = user;

		noteService.createNoteForUser(user.getId(), title,
				new AbstractCallback<NoteDTO>() {

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
}