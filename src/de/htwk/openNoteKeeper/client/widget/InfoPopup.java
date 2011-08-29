package de.htwk.openNoteKeeper.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.htwk.openNoteKeeper.client.util.IconPool;

public class InfoPopup {

	public static interface InfoPopupConstants extends Constants {
		String getTitle();
	}

	private final InfoPopupConstants constants = GWT
			.create(InfoPopupConstants.class);

	private final DialogBox window = new DialogBox(false, false);

	public InfoPopup(String message) {
		window.setAnimationEnabled(true);
		window.setGlassEnabled(true);
		window.setText(constants.getTitle());

		VerticalPanel layout = new VerticalPanel();
		layout.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		layout.setSpacing(5);

		HorizontalPanel content = new HorizontalPanel();
		content.setSpacing(10);

		Image image = IconPool.Info_Big.createImage();
		// image.setWidth("50px");
		content.add(image);

		Label errorMessage = new Label(message);
		// errorMessage.setWidth("*");
		content.add(errorMessage);

		Button closeButton = new Button("Schließen");
		closeButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				window.hide();
			}
		});

		layout.add(content);
		layout.add(closeButton);

		window.setWidget(layout);
	}

	public void show() {
		window.center();
	}

}
