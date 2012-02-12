package de.htwk.openNoteKeeper.client.widget;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;

public class StatusPanel {

	private final String message;
	private int delayMillis = 500;
	private int autoHideInSeconds = 0;
	private boolean canClose = false;
	private boolean enable = true;

	private PopupPanel statusPanel;

	public StatusPanel(String message) {
		this(message, false, 0);
	}

	public StatusPanel(String message, boolean canClose, int autoHideInSeconds) {
		this.message = message;
		this.canClose = canClose;
		this.autoHideInSeconds = autoHideInSeconds;
	}

	public void show() {
		statusPanel = new PopupPanel(false, false);
		statusPanel.addStyleName("statusPanel");
		statusPanel.setAnimationEnabled(true);
		int width = calculateWidth(message.length());
		statusPanel.setWidth(width + "px");

		HorizontalPanel layout = new HorizontalPanel();
		layout.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		layout.setSpacing(5);
		layout.addStyleName("statusPanel");
		statusPanel.add(layout);

		Label messagelabel = new Label(message);
		layout.add(messagelabel);

		if (canClose) {
			Label closeButton = new Label("X");
			closeButton.addStyleName("clickable");
			closeButton.addStyleName("white");
			closeButton.addClickHandler(new ClickHandler() {

				public void onClick(ClickEvent event) {
					hide();
				}
			});

			layout.add(closeButton);
			layout.setCellHorizontalAlignment(closeButton,
					HasHorizontalAlignment.ALIGN_RIGHT);
		}

		int x = calculatePosition(Window.getClientWidth() / 2, width);
		int y = calculatePosition(50, messagelabel.getOffsetHeight());
		statusPanel.setPopupPosition(x, y);

		new Timer() {
			@Override
			public void run() {
				showAfterDelay();
			}
		}.schedule(delayMillis);
	}

	private void showAfterDelay() {
		if (enable) {
			statusPanel.show();

			if (autoHideInSeconds > 0) {
				new Timer() {

					@Override
					public void run() {
						hide();
					}
				}.schedule(autoHideInSeconds * 1000);
			}
		}
	}

	private int calculateWidth(int length) {
		int max = 300;
		int pixelForLetter = 20;
		int width = length * pixelForLetter;
		return width < max ? width : max;
	}

	private int calculatePosition(int absolute, int dimension) {
		return absolute - dimension / 2;
	}

	public void hide() {
		if (statusPanel != null) {
			enable = false;
			statusPanel.hide();
		}
	}

	public void setAutoHide(int seconds) {
		this.autoHideInSeconds = seconds;
	}

	public void setCanClose(boolean canClose) {
		this.canClose = canClose;
	}
}
