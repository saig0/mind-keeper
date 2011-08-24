package de.htwk.openNoteKeeper.client.note.view;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

import de.htwk.openNoteKeeper.client.note.presenter.NotePresenter.NoteView;

public class NoteViewImpl implements NoteView {

	public Widget asWidget() {
		DockLayoutPanel main = new DockLayoutPanel(Unit.EM);
		main.setSize("100%", "100%");
		return main;
	}

}
