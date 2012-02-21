package de.htwk.openNoteKeeper.client.widget;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.inject.Singleton;

@Singleton
public class StatusArea {

	private final VerticalPanel mainPanel;

	private final PopupPanel popupPanel;

	public StatusArea() {
		mainPanel = createLayout();

		popupPanel = new PopupPanel(false, false);
		popupPanel.addStyleName("statusPanel");
		popupPanel.addStyleName("top");
		popupPanel.setAnimationEnabled(true);
		popupPanel.setVisible(false);
		int width = 300;
		popupPanel.setWidth(width + "px");

		popupPanel.add(mainPanel);

		int x = calculatePosition(Window.getClientWidth() / 2, width);
		int y = 50;
		popupPanel.setPopupPosition(x, y);
		popupPanel.show();
	}

	private int calculatePosition(int absolute, int dimension) {
		return absolute - dimension / 2;
	}

	private VerticalPanel createLayout() {
		VerticalPanel panel = new VerticalPanel();
		panel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		panel.setSpacing(5);

		return panel;
	}

	public StatusPanel addStatusMessage(StatusPanel statusPanel) {
		statusPanel.setStatusArea(this);
		statusPanel.show();
		mainPanel.add(statusPanel);
		if (mainPanel.getWidgetCount() > 0) {
			popupPanel.setVisible(true);
		}

		return statusPanel;
	}

	public void removeStatusPanel(StatusPanel statusPanel) {
		mainPanel.remove(statusPanel);
		if (mainPanel.getWidgetCount() < 1) {
			popupPanel.setVisible(false);
		}
	}

}
