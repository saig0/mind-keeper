package de.htwk.openNoteKeeper.client.note.view.whiteboard;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import de.htwk.openNoteKeeper.client.note.presenter.whiteboard.SingleNotePresenter.SingleNoteView;

public class SingleNoteViewImpl implements SingleNoteView {

	private final Widget main;
	private Image backgroundImage;
	private Label titleLabel;
	private boolean isSelected = false;

	public SingleNoteViewImpl() {
		main = createLayout();
	}

	private Widget createLayout() {
		final FocusPanel main = new FocusPanel();
		main.addStyleName("note");

		HorizontalPanel panel = new HorizontalPanel();
		panel.setSize("100%", "100%");
		panel.setSpacing(5);

		// backgroundImage = IconPool.PostIt.createImage();
		// panel.add(backgroundImage);

		titleLabel = new Label("");
		titleLabel.addStyleName("noteHeader");
		panel.add(titleLabel);
		main.setWidget(panel);

		main.addMouseOverHandler(new MouseOverHandler() {

			public void onMouseOver(MouseOverEvent event) {
				main.addStyleName("activeNote");
			}
		});

		main.addMouseOutHandler(new MouseOutHandler() {

			public void onMouseOut(MouseOutEvent event) {
				if (!isSelected) {
					main.removeStyleName("activeNote");
				}
			}
		});

		main.addFocusHandler(new FocusHandler() {

			public void onFocus(FocusEvent event) {
				main.addStyleName("activeNote");
				isSelected = true;
			}
		});

		main.addBlurHandler(new BlurHandler() {

			public void onBlur(BlurEvent event) {
				main.removeStyleName("activeNote");
				isSelected = false;
			}
		});

		return main;
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