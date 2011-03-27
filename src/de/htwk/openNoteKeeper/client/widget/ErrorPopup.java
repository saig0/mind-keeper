package de.htwk.openNoteKeeper.client.widget;

import com.google.gwt.user.client.ui.Image;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.layout.HLayout;

import de.htwk.openNoteKeeper.client.util.IconPool;

public class ErrorPopup {

	private final Window window = new Window();

	public ErrorPopup(Throwable caught) {
		window.setAutoSize(true);
		window.setShowMinimizeButton(false);
		window.setIsModal(true);
		window.setShowModalMask(true);
		window.setTitle("Fehler");

		HLayout layout = new HLayout();
		layout.setWidth(250);
		layout.setHeight(50);
		layout.setMembersMargin(15);
		layout.setLayoutMargin(10);

		Image image = IconPool.Alert_Big.createImage();
		image.setWidth("50px");
		layout.addMember(image);

		Label errorMessage = new Label(caught.getLocalizedMessage());
		errorMessage.setWidth("*");
		layout.addMember(errorMessage);

		window.addItem(layout);
	}

	public void showRelativeTo(Canvas target) {
		window.showNextTo(target);
	}

	public void show() {
		window.show();
	}
}
