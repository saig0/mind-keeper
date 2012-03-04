package de.htwk.openNoteKeeper.client.note.view.whiteboard;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

import de.htwk.openNoteKeeper.client.note.presenter.whiteboard.SingleNotePresenter.SingleNoteView;
import de.htwk.openNoteKeeper.client.util.IconPool;

public class SingleNoteViewImpl implements SingleNoteView {

	private final Widget main;
	private Image backgroundImage;

	public SingleNoteViewImpl() {
		main = createLayout();
	}

	private Widget createLayout() {
		HorizontalPanel panel = new HorizontalPanel();

		backgroundImage = IconPool.PostIt.createImage();
		panel.add(backgroundImage);

		return panel;
	}

	public Widget asWidget() {
		return main;
	}

	public void setSize(int width, int height) {
		main.setSize(width + "px", height + "px");
		backgroundImage.setSize(width + "px", height + "px");
	}
}