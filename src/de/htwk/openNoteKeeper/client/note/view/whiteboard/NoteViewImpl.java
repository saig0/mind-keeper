package de.htwk.openNoteKeeper.client.note.view.whiteboard;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import de.htwk.openNoteKeeper.client.note.presenter.whiteboard.NotePresenter.NoteView;
import de.htwk.openNoteKeeper.client.note.view.navigation.NavigationTreeViewImpl;
import de.htwk.openNoteKeeper.client.widget.HideablePanel;

public class NoteViewImpl implements NoteView {

	@Inject
	private NavigationTreeViewImpl navigationTreeView;

	private AbsolutePanel whiteboardPanel;

	public Widget asWidget() {
		HorizontalPanel main = new HorizontalPanel();
		main.setSize("100%", "100%");

		whiteboardPanel = new AbsolutePanel();
		whiteboardPanel.setHeight("100%");
		whiteboardPanel.addStyleName("whiteBoard");

		HideablePanel hideableNavigation = new HideablePanel(
				navigationTreeView.asWidget(), whiteboardPanel, 25);
		main.add(hideableNavigation);

		return main;
	}

	public void showNoteWidget(Widget noteWidget, int left, int top) {
		whiteboardPanel.add(noteWidget, left, top);
	}

	public void removeNoteWidget(Widget noteWidget) {
		whiteboardPanel.remove(noteWidget);
	}
}
