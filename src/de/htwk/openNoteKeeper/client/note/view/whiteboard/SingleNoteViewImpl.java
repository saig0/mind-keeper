package de.htwk.openNoteKeeper.client.note.view.whiteboard;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import de.htwk.openNoteKeeper.client.note.presenter.whiteboard.SingleNotePresenter.SingleNoteView;

public class SingleNoteViewImpl implements SingleNoteView {

	private final Widget main;
	private Image backgroundImage;
	private Label titleLabel;

	public SingleNoteViewImpl() {
		main = createLayout();
	}

	private Widget createLayout() {
		HorizontalPanel panel = new HorizontalPanel();
		panel.setSpacing(5);
		panel.addStyleName("note");

		// backgroundImage = IconPool.PostIt.createImage();
		// panel.add(backgroundImage);

		titleLabel = new Label("");
		titleLabel.addStyleName("noteHeader");
		panel.add(titleLabel);

		return panel;
	}

	public Widget asWidget() {
		return main;
	}

	public void setSize(int width, int height) {
		main.setSize(width + "px", height + "px");
		// backgroundImage.setSize(width + "px", height + "px");
	}

	public void setTitle(String title) {
		titleLabel.setText(title);
	}

	public void setContent(String content) {
		// TODO Auto-generated method stub

	}
}