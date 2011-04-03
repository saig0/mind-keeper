package de.htwk.openNoteKeeper.client.note.presenter;

import com.google.gwt.user.client.ui.IsWidget;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.HasClickHandlers;

import de.htwk.openNoteKeeper.client.note.NoteEventBus;
import de.htwk.openNoteKeeper.client.note.view.ConfigureNoteWidget;
import de.htwk.openNoteKeeper.shared.UserDTO;

@Presenter(view = ConfigureNoteWidget.class)
public class NoteConfigurationPresenter extends
		BasePresenter<ConfigureNoteWidget, NoteEventBus> {

	public interface NoteConfigurationView extends IsWidget {
		public HasClickHandlers getCreateNewNoteButton();

		public String getNoteTitle();

		public void showOnPosition(int left, int top);

		public void hide();
	}

	@Override
	public void bind() {
		view.getCreateNewNoteButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				String title = view.getNoteTitle();
				view.hide();

				NoteWidgetPresenter presenter = NoteWidgetPresenterFactory
						.createPresenter(eventBus);
				presenter.onCreateNewNote(user, title);
			}
		});
	}

	private UserDTO user;

	public void onLoggedIn(UserDTO user) {
		this.user = user;
	}

	public void onShowConfigurationNoteView(int left, int top) {
		view.showOnPosition(left, top);
		view.asWidget();
	}

}