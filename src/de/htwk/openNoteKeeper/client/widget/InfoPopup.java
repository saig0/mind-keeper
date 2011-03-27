package de.htwk.openNoteKeeper.client.widget;

import com.google.gwt.user.client.ui.Image;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.layout.HLayout;

import de.htwk.openNoteKeeper.client.util.IconPool;

public class InfoPopup {

	private final Window window = new Window();

	public InfoPopup(String message) {
		window.setAutoSize(true);
		window.setAutoCenter(true);
		window.setShowMinimizeButton(false);
		window.setIsModal(true);
		window.setShowModalMask(true);
		window.setTitle("Info");

		HLayout layout = new HLayout();
		layout.setWidth(250);
		layout.setHeight(50);
		layout.setMembersMargin(15);
		layout.setLayoutMargin(10);

		Image image = IconPool.Info_Big.createImage();
		image.setWidth("50px");
		layout.addMember(image);

		Label errorMessage = new Label(message);
		errorMessage.setWidth("*");
		layout.addMember(errorMessage);

		window.addItem(layout);
	}

	public void show() {
		window.show();
	}

}
