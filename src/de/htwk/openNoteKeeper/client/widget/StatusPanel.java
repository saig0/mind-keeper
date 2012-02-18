package de.htwk.openNoteKeeper.client.widget;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class StatusPanel implements IsWidget {

	private final Widget widget;
	private final StatusArea statusArea;

	private final String message;
	private int delayMillis = 500;
	private int autoHideInSeconds = 0;
	private boolean canClose = false;
	private boolean enable = true;

	public StatusPanel(StatusArea statusArea, String message) {
		this(statusArea, message, false, 0);
	}

	public StatusPanel(StatusArea statusArea, String message, boolean canClose,
			int autoHideInSeconds) {
		this.message = message;
		this.canClose = canClose;
		this.autoHideInSeconds = autoHideInSeconds;

		this.statusArea = statusArea;
		this.widget = createLayout();
	}

	private Widget createLayout() {
		HorizontalPanel layout = new HorizontalPanel();
		layout.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		layout.setSpacing(5);
		layout.addStyleName("statusPanel");
		layout.setSize("100%", "100%");

		final Label messagelabel = new Label(message);
		layout.add(messagelabel);

		final Label loadingLabel = new Label();
		loadingLabel.addStyleName("white");
		layout.add(loadingLabel);
		layout.setCellWidth(loadingLabel, "15px");
		layout.setCellHorizontalAlignment(loadingLabel,
				HasHorizontalAlignment.ALIGN_LEFT);

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
		} else if (autoHideInSeconds < 1) {
			new Timer() {

				int step = 0;

				@Override
				public void run() {
					switch (step) {
					case 0:
						loadingLabel.setText(".");
						break;
					case 1:
						loadingLabel.setText("..");
						break;
					case 2:
						loadingLabel.setText("...");
						break;
					case 3:
						loadingLabel.setText("");
						break;
					}
					if (step < 3)
						step++;
					else
						step = 0;
				}
			}.scheduleRepeating(500);
		}
		return layout;
	}

	public Widget asWidget() {
		return widget;
	}

	public void show() {
		new Timer() {
			@Override
			public void run() {
				showAfterDelay();
			}
		}.schedule(delayMillis);
	}

	private void showAfterDelay() {
		if (enable) {
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

	public void hide() {
		enable = false;
		statusArea.removeStatusPanel(this);
	}

	public void setAutoHide(int seconds) {
		this.autoHideInSeconds = seconds;
	}

	public void setCanClose(boolean canClose) {
		this.canClose = canClose;
	}
}