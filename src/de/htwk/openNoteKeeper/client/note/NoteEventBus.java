package de.htwk.openNoteKeeper.client.note;

import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.view.NoStartView;

import de.htwk.openNoteKeeper.client.note.presenter.NoteConfigurationPresenter;
import de.htwk.openNoteKeeper.client.note.presenter.NotePresenter;
import de.htwk.openNoteKeeper.client.note.presenter.NoteWidgetPresenter;
import de.htwk.openNoteKeeper.client.note.presenter.NoteWidgetPresenter.NoteWidgetView;
import de.htwk.openNoteKeeper.shared.NoteDTO;
import de.htwk.openNoteKeeper.shared.UserDTO;

@Events(startView = NoStartView.class, module = NoteModule.class)
public interface NoteEventBus extends EventBus {

	@Event(handlers = { NotePresenter.class, NoteConfigurationPresenter.class })
	public void loggedIn(UserDTO user);

	@Event(forwardToParent = true)
	public void setContent(Widget content);

	@Event(handlers = NoteConfigurationPresenter.class)
	public void showConfigurationNoteView(int left, int top);

	@Event(handlers = NoteWidgetPresenter.class)
	public void createNewNote(UserDTO user, String title);

	@Event(handlers = NoteWidgetPresenter.class)
	public void showNote(UserDTO user, NoteDTO note);

	@Event(handlers = NotePresenter.class)
	public void newNoteCreated(NoteWidgetView noteWidget);

	@Event(handlers = NotePresenter.class)
	public void noteCreated(NoteWidgetView noteWidget);

	@Event(handlers = NotePresenter.class)
	public void editNoteWidget(NoteWidgetView noteWidget);
}
