package de.htwk.openNoteKeeper.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.UIObject;

import de.htwk.openNoteKeeper.client.util.IconPool;

public class ErrorPopup {

	public static interface ErrorPopupConstants extends Constants {
		String getTitle();
	}

	private final ErrorPopupConstants constants = GWT
			.create(ErrorPopupConstants.class);

	private final PopupPanel window = new PopupPanel(false, true);

	public ErrorPopup(Throwable caught) {
		window.setAnimationEnabled(true);
		window.setGlassEnabled(true);
		window.setTitle(constants.getTitle());

		DockLayoutPanel layout = new DockLayoutPanel(Unit.EM);

		Image image = IconPool.Alert_Big.createImage();
		// image.setWidth("50px");
		layout.addWest(image, 10);

		Label errorMessage = new Label(caught.getLocalizedMessage());
		// errorMessage.setWidth("*");
		layout.add(errorMessage);

		window.add(layout);
	}

	public void showRelativeTo(UIObject target) {
		window.showRelativeTo(target);
	}

	public void show() {
		window.center();
	}
}
