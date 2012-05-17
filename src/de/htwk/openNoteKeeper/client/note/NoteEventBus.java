package de.htwk.openNoteKeeper.client.note;

import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.view.NoStartView;

import de.htwk.openNoteKeeper.client.note.presenter.actionBar.NoteActionBarPresenter;
import de.htwk.openNoteKeeper.client.note.presenter.actionBar.NoteCreationPresenter;
import de.htwk.openNoteKeeper.client.note.presenter.navigation.NavigationInputPresenter;
import de.htwk.openNoteKeeper.client.note.presenter.navigation.NavigationTreePresenter;
import de.htwk.openNoteKeeper.client.note.presenter.navigation.NavigationTreePresenter.NavigationTreeView;
import de.htwk.openNoteKeeper.client.note.presenter.navigation.TreeItemCreationPresenter;
import de.htwk.openNoteKeeper.client.note.presenter.whiteboard.NoteMovePresenter;
import de.htwk.openNoteKeeper.client.note.presenter.whiteboard.NotePresenter;
import de.htwk.openNoteKeeper.client.note.presenter.whiteboard.SingleNotePresenter;
import de.htwk.openNoteKeeper.shared.GroupDTO;
import de.htwk.openNoteKeeper.shared.NoteDTO;
import de.htwk.openNoteKeeper.shared.UserDTO;
import de.htwk.openNoteKeeper.shared.WhiteBoardDTO;

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

	@Event(handlers = { NotePresenter.class, NoteCreationPresenter.class,
			NoteMovePresenter.class })
	public void selectWhiteBoard(WhiteBoardDTO selectedWhiteBoard);

	@Event(handlers = { NotePresenter.class, NoteCreationPresenter.class })
	public void selectGroup(GroupDTO group);

	@Event(handlers = NoteCreationPresenter.class)
	public void showNoteCreationView();

	@Event(handlers = NoteCreationPresenter.class)
	public void showNoteEditView(NoteDTO note);

	@Event(handlers = NotePresenter.class)
	public void showNote(NoteDTO note);

	@Event(handlers = NotePresenter.class)
	public void removeNote(NoteDTO note);

	@Event(handlers = NoteMovePresenter.class)
	public void showNoteMoveView(NoteDTO note);

	@Event(handlers = SingleNotePresenter.class)
	public void hideEditor();

	@Event(handlers = TreeItemCreationPresenter.class)
	public void showGroupEditView(GroupDTO group);

	@Event(handlers = TreeItemCreationPresenter.class)
	public void showWhiteboardEditView(WhiteBoardDTO whiteBoard);

	@Event(forwardToParent = true)
	public void showSettings();
}
