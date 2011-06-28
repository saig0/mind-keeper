package de.htwk.openNoteKeeper.client.main.view;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import de.htwk.openNoteKeeper.client.main.presenter.MainPresenter.MainView;
import de.htwk.openNoteKeeper.client.main.presenter.UserPresenter.UserView;
import de.htwk.openNoteKeeper.client.note.presenter.NotePresenter.NoteView;
import de.htwk.openNoteKeeper.client.util.IconPool;

public class MainViewImpl implements MainView {

	@Inject
	private UserView userView;
	@Inject
	private NoteView noteView;

	private ScrollPanel content = new ScrollPanel();

	public Widget asWidget() {
		DockLayoutPanel main = new DockLayoutPanel(Unit.EM);
		main.setSize("100%", "100%");

		DockLayoutPanel header = new DockLayoutPanel(Unit.EM);
		header.setHeight("10");
		header.setWidth("100%");

		header.addWest(IconPool.Logo.createImage(), 10);
		header.addEast(userView.asWidget(), 10);

		main.addNorth(header, 10);
		main.addEast(noteView, 5);
		main.add(content);

		return main;
	}

	public void setContent(Widget widget) {
		content.clear();
		content.add(widget);
	}

}
