package de.htwk.openNoteKeeper.client.note.view;

import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import de.htwk.openNoteKeeper.client.note.presenter.NotePresenter.NoteView;
import de.htwk.openNoteKeeper.client.util.IconPool;

public class NoteViewImpl implements NoteView {

	@Inject
	private NavigationTreeViewImpl navigationTreeView;

	public Widget asWidget() {
		HorizontalPanel main = new HorizontalPanel();
		main.setSize("100%", "100%");

		VerticalPanel controlPanel = new VerticalPanel();
		controlPanel.setSize("25%", "100%");
		controlPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);

		HorizontalPanel noteControlPanel = new HorizontalPanel();
		noteControlPanel.setSize("100%", "100%");
		noteControlPanel
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		noteControlPanel.add(IconPool.Trash_Big.createImage());
		noteControlPanel.add(IconPool.Notice_Big.createImage());
		controlPanel.add(noteControlPanel);

		controlPanel.add(navigationTreeView);

		main.add(controlPanel);

		DecoratorPanel panel = new DecoratorPanel();
		panel.setSize("100%", "100%");
		ScrollPanel whiteBoardPanel = new ScrollPanel(panel);
		whiteBoardPanel.setSize("100%", "100%");

		main.add(whiteBoardPanel);

		return main;
	}
}
