package de.htwk.openNoteKeeper.client.note.view.actionBar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import de.htwk.openNoteKeeper.client.note.i18n.NoteConstants;
import de.htwk.openNoteKeeper.client.note.presenter.actionBar.NoteActionBarPresenter.NoteActionBarView;
import de.htwk.openNoteKeeper.client.util.IconPool;

public class NoteActionBarViewImpl implements NoteActionBarView {

	private NoteConstants constants = GWT.create(NoteConstants.class);

	private HorizontalPanel main;
	private Image noteIcon;

	@Inject
	private NoteFindViewImpl noteFindView;

	public NoteActionBarViewImpl() {
		noteIcon = IconPool.Notice_Big.createImage();
	}

	private HorizontalPanel createLayout() {
		HorizontalPanel main = new HorizontalPanel();
		main.setSize("100%", "100%");
		main.addStyleName("actionBar");
		main.setSpacing(10);
		main.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

		noteIcon.setTitle(constants.newNote());
		noteIcon.setStyleName("clickable");
		main.add(noteIcon);

		main.add(noteFindView);
		main.setCellVerticalAlignment(noteFindView,
				HasVerticalAlignment.ALIGN_MIDDLE);

		return main;
	}

	public Widget asWidget() {
		if (main == null) {
			main = createLayout();
		}
		return main;
	}

	public HasClickHandlers getAddButton() {
		return noteIcon;
	}
}
