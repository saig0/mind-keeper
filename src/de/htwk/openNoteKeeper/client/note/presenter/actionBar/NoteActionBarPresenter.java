package de.htwk.openNoteKeeper.client.note.presenter.actionBar;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
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

		public HasClickHandlers getAddButton();

		public HasClickHandlers getSettingsButton();
	}

	@Override
	public void bind() {
		view.getAddButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				eventBus.hideEditor();
				eventBus.showNoteCreationView();
			}
		});

		view.getSettingsButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				eventBus.showSettings();
			}
		});
	}

	public void onLoggedIn(UserDTO user) {
		eventBus.setActionBar(view.asWidget());
	}

}
