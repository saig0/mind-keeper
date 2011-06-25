package de.htwk.openNoteKeeper.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;

import de.htwk.openNoteKeeper.client.util.IconPool;

public class InfoPopup {

	public static interface InfoPopupConstants extends Constants {
		String getTitle();
	}

	private final InfoPopupConstants constants = GWT
			.create(InfoPopupConstants.class);

	private final PopupPanel window = new PopupPanel(false, true);

	public InfoPopup(String message) {
		window.setAnimationEnabled(true);
		window.setGlassEnabled(true);
		window.setTitle(constants.getTitle());

		DockLayoutPanel layout = new DockLayoutPanel(Unit.EM);

		Image image = IconPool.Info_Big.createImage();
		// image.setWidth("50px");
		layout.addWest(image, 10);

		Label errorMessage = new Label(message);
		// errorMessage.setWidth("*");
		layout.add(errorMessage);

		window.add(layout);
	}

	public void show() {
		window.center();
	}

}
