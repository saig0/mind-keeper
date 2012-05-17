package de.htwk.openNoteKeeper.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.htwk.openNoteKeeper.client.i18n.CommonConstants;
import de.htwk.openNoteKeeper.client.main.presenter.Session;
import de.htwk.openNoteKeeper.client.util.IconPool;

public class ConfirmationPopup {

	public interface SaveAction {
		public void run();
	}

	private CommonConstants constants = GWT.create(CommonConstants.class);

	private final DialogBox window = new DialogBox(false, false);
	private final SaveAction saveAction;

	public ConfirmationPopup(String message, final SaveAction saveAction) {
		this.saveAction = saveAction;
		window.setAnimationEnabled(true);
		window.setGlassEnabled(true);
		window.setText(constants.info());
		window.setSize("300px", "100px");

		VerticalPanel layout = new VerticalPanel();
		layout.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		layout.setSize("100%", "100%");
		layout.setSpacing(5);

		HorizontalPanel content = new HorizontalPanel();
		content.setSize("100%", "100%");
		content.setSpacing(10);

		Image image = IconPool.Info_Big.createImage();
		content.add(image);

		Label errorMessage = new Label(message);
		content.add(errorMessage);

		HorizontalPanel controlPannel = new HorizontalPanel();
		controlPannel.setSpacing(5);
		controlPannel.setSize("100%", "100%");
		Button abortButton = new Button(constants.abort());
		abortButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				window.hide();
			}
		});

		abortButton.setWidth("100%");
		controlPannel.add(abortButton);
		Button confirmButton = new Button(constants.confirm());
		confirmButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				window.hide();
				saveAction.run();
			}
		});

		confirmButton.setWidth("100%");
		controlPannel.add(confirmButton);
		controlPannel.setCellWidth(confirmButton, "65%");

		layout.add(content);
		layout.add(controlPannel);

		window.setWidget(layout);
	}

	public void showRelativeTo(UIObject target) {
		window.showRelativeTo(target);
	}

	public void show() {
		if (Session.getSettings().shouldAskBeforeDelete()) {
			window.center();
		} else {
			saveAction.run();
		}
	}
}
