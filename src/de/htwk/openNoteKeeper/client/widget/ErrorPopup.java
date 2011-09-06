package de.htwk.openNoteKeeper.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.htwk.openNoteKeeper.client.i18n.ErrorPopupConstants;
import de.htwk.openNoteKeeper.client.util.IconPool;

public class ErrorPopup {

	private ErrorPopupConstants constants = GWT
			.create(ErrorPopupConstants.class);

	private final DialogBox window = new DialogBox(false, false);

	public ErrorPopup(Throwable caught) {
		window.setAnimationEnabled(true);
		window.setGlassEnabled(true);
		window.setText(constants.title());
		window.setSize("300px", "100px");

		VerticalPanel layout = new VerticalPanel();
		layout.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		layout.setSpacing(5);

		HorizontalPanel content = new HorizontalPanel();
		content.setSpacing(10);

		Image image = IconPool.Alert_Big.createImage();
		content.add(image);

		Label errorMessage = new Label(caught.getLocalizedMessage());
		content.add(errorMessage);

		Button closeButton = new Button(constants.close());
		closeButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				window.hide();
			}
		});

		layout.add(content);
		layout.add(closeButton);

		window.setWidget(layout);
	}

	public void showRelativeTo(UIObject target) {
		window.showRelativeTo(target);
	}

	public void show() {
		window.center();
	}
}
