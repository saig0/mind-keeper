package de.htwk.openNoteKeeper.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.htwk.openNoteKeeper.client.i18n.CommonConstants;
import de.htwk.openNoteKeeper.client.util.IconPool;

public class InfoPopup {

	private CommonConstants constants = GWT.create(CommonConstants.class);

	private final DialogBox window = new DialogBox(false, false);

	public InfoPopup(String message) {
		window.setAnimationEnabled(true);
		window.setGlassEnabled(true);
		window.setText(constants.info());

		VerticalPanel layout = new VerticalPanel();
		layout.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		layout.setSpacing(5);

		HorizontalPanel content = new HorizontalPanel();
		content.setSpacing(10);

		Image image = IconPool.Info_Big.createImage();
		content.add(image);

		Label errorMessage = new Label(message);
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

	public void show() {
		window.center();
	}

}
