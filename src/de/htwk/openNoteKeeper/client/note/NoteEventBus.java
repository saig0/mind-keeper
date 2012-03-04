package de.htwk.openNoteKeeper.client.note;

import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.view.NoStartView;

import de.htwk.openNoteKeeper.client.note.presenter.actionBar.NoteActionBarPresenter;
import de.htwk.openNoteKeeper.client.note.presenter.navigation.NavigationInputPresenter;
import de.htwk.openNoteKeeper.client.note.presenter.navigation.NavigationTreePresenter;
import de.htwk.openNoteKeeper.client.note.presenter.navigation.NavigationTreePresenter.NavigationTreeView;
import de.htwk.openNoteKeeper.client.note.presenter.whiteboard.NotePresenter;
import de.htwk.openNoteKeeper.shared.GroupDTO;
import de.htwk.openNoteKeeper.shared.UserDTO;

@Events(startView = NoStartView.class, module = NoteModule.class)
public interface NoteEventBus extends EventBus {

	@Event(forwardToParent = true)
	public void setContent(Widget content);

	@Event(forwardToParent = true)
	public void setActionBar(Widget actionWidget);

	@Event(handlers = { NotePresenter.class, NavigationTreePresenter.class,
			NoteActionBarPresenter.class })
	public void loggedIn(UserDTO user);

	@Event(handlers = NavigationInputPresenter.class)
	public void showInputFieldForNewGroup(
			NavigationTreeView navigationTreeView, GroupDTO selectedGroup);

	@Event(handlers = NavigationInputPresenter.class)
	public void showInputFieldForNewWhiteBoard(
			NavigationTreeView navigationTreeView, GroupDTO selectedGroup);
}
