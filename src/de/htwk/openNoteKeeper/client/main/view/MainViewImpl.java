package de.htwk.openNoteKeeper.client.main.view;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import de.htwk.openNoteKeeper.client.main.presenter.MainPresenter.MainView;
import de.htwk.openNoteKeeper.client.note.view.NoteViewImpl;
import de.htwk.openNoteKeeper.client.util.IconPool;

public class MainViewImpl implements MainView {

	@Inject
	private UserViewImpl userView;
	@Inject
	private LanguageChooserViewImpl languageChooser;
	@Inject
	private NoteViewImpl noteView;

	private ScrollPanel content = new ScrollPanel();

	public Widget asWidget() {
		DockLayoutPanel main = new DockLayoutPanel(Unit.PX);
		main.setSize("100%", "100%");

		DockLayoutPanel top = new DockLayoutPanel(Unit.PX);
		top.setHeight("25");
		top.setWidth("100%");

		top.addEast(languageChooser, 150);

		DockLayoutPanel header = new DockLayoutPanel(Unit.PX);
		header.setHeight("100");
		header.setWidth("100%");

		header.addWest(IconPool.Logo.createImage(), 600);
		header.addEast(userView, 200);

		main.addNorth(top, 25);
		main.addNorth(header, 100);
		main.addEast(noteView, 50);
		main.add(content);

		return main;
	}

	public void setContent(Widget widget) {
		content.clear();
		content.add(widget);
	}

}
