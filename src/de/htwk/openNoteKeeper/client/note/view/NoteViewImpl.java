package de.htwk.openNoteKeeper.client.note.view;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import de.htwk.openNoteKeeper.client.note.presenter.NotePresenter.NoteView;
import de.htwk.openNoteKeeper.client.widget.HideablePanel;

public class NoteViewImpl implements NoteView {

	@Inject
	private NavigationTreeViewImpl navigationTreeView;

	public Widget asWidget() {
		HorizontalPanel main = new HorizontalPanel();
		main.setSize("100%", "100%");

		AbsolutePanel panel = new AbsolutePanel();
		panel.setHeight("100%");
		panel.addStyleName("whiteBoard");

		HideablePanel hideableNavigation = new HideablePanel(
				navigationTreeView.asWidget(), panel, 25);
		main.add(hideableNavigation);

		return main;
	}
}
