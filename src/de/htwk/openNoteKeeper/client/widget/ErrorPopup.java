package de.htwk.openNoteKeeper.client.widget;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.UIObject;

import de.htwk.openNoteKeeper.client.util.IconPool;

public class ErrorPopup {

	// public static interface ErrorPopupConstants extends Constants {
	// String getTitle();
	// }
	//
	// private final ErrorPopupConstants constants = GWT
	// .create(ErrorPopupConstants.class);

	private final PopupPanel window = new PopupPanel(false, true);

	public ErrorPopup(Throwable caught) {
		window.setAnimationEnabled(true);
		window.setGlassEnabled(true);
		// window.setTitle(constants.getTitle());
		window.setTitle("Fehler");
		window.setSize("200px", "100px");

		DockLayoutPanel layout = new DockLayoutPanel(Unit.PX);
		DockLayoutPanel content = new DockLayoutPanel(Unit.PX);

		Image image = IconPool.Alert_Big.createImage();
		content.addWest(image, 75);

		Label errorMessage = new Label(caught.getLocalizedMessage());
		content.add(errorMessage);

		Button closeButton = new Button("Ok");
		closeButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				window.hide();
			}
		});

		layout.addSouth(closeButton, 25);
		layout.add(content);

		window.add(layout);
	}

	public void showRelativeTo(UIObject target) {
		window.showRelativeTo(target);
	}

	public void show() {
		window.center();
	}
}
