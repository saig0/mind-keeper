package de.htwk.openNoteKeeper.client.note.view.actionBar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

import de.htwk.openNoteKeeper.client.note.i18n.NoteConstants;
import de.htwk.openNoteKeeper.client.note.presenter.actionBar.NoteActionBarPresenter.NoteActionBarView;
import de.htwk.openNoteKeeper.client.util.IconPool;

public class NoteActionBarViewImpl implements NoteActionBarView {

	private NoteConstants constants = GWT.create(NoteConstants.class);

	private Image noteIcon;

	public Widget asWidget() {
		HorizontalPanel main = new HorizontalPanel();
		main.setSize("100%", "100%");
		main.addStyleName("actionBar");

		noteIcon = IconPool.Notice_Big.createImage();
		noteIcon.setTitle(constants.newNote());
		noteIcon.setStyleName("clickable");
		main.add(noteIcon);

		return main;
	}
}
