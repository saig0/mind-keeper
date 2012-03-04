package de.htwk.openNoteKeeper.client.note.view.whiteboard;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.htwk.openNoteKeeper.client.note.presenter.whiteboard.SingleNotePresenter.SingleNoteView;
import de.htwk.openNoteKeeper.client.util.IconPool;

public class SingleNoteViewImpl implements SingleNoteView {

	private final Widget main;
	private Label titleLabel;
	private boolean isSelected = false;

	public SingleNoteViewImpl() {
		main = createLayout();
	}

	private Widget createLayout() {
		final FocusPanel main = new FocusPanel();
		main.addStyleName("note");

		VerticalPanel panel = new VerticalPanel();
		panel.setSize("100%", "100%");
		panel.setSpacing(5);

		HorizontalPanel header = new HorizontalPanel();
		header.setSize("100%", "100%");

		titleLabel = new Label("");
		titleLabel.addStyleName("noteHeader");
		header.add(titleLabel);

		Image contextImage = IconPool.Settings_Small.createImage();
		contextImage.addStyleName("clickable");

		contextImage.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				PopupPanel popup = new PopupPanel(true, false);
				popup.addStyleName("top");

				HorizontalPanel content = new HorizontalPanel();
				content.add(new Label("context menu coming soon..."));
				popup.setWidget(content);

				popup.setPopupPosition(event.getClientX(), event.getClientY());
				popup.show();
			}
		});

		header.add(contextImage);
		header.setCellHorizontalAlignment(contextImage,
				HasHorizontalAlignment.ALIGN_RIGHT);

		panel.add(header);
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
	}

	public void setTitle(String title) {
		titleLabel.setText(title);
	}

	public void setContent(String content) {
		// TODO Auto-generated method stub

	}

	public Widget getDragHandle() {
		return titleLabel;
	}
}