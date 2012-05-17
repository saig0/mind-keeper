package de.htwk.openNoteKeeper.client.main.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.htwk.openNoteKeeper.client.i18n.CommonConstants;
import de.htwk.openNoteKeeper.client.i18n.SettingsConstants;
import de.htwk.openNoteKeeper.client.main.presenter.SettingsPresenter.SettingsView;
import de.htwk.openNoteKeeper.client.util.EnterKeyPressHandler;

public class SettingsViewImpl implements SettingsView {

	private final DialogBox popupPanel;
	private Button saveButton;
	private Button abortButton;
	private CheckBox shouldAskBeforeDeleteCheckBox;

	private SettingsConstants settingsConstants = GWT
			.create(SettingsConstants.class);
	private CommonConstants commonConstants = GWT.create(CommonConstants.class);

	public SettingsViewImpl() {
		popupPanel = createLayout();
	}

	private DialogBox createLayout() {
		DialogBox popup = new DialogBox(false, true);
		popup.setAnimationEnabled(true);
		popup.setGlassEnabled(true);

		popup.setText(commonConstants.settings());
		popup.setSize("300px", "100px");

		VerticalPanel layout = new VerticalPanel();
		layout.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		layout.setSize("100%", "100%");

		HorizontalPanel commonSettingsPanel = new HorizontalPanel();
		commonSettingsPanel.setSpacing(5);
		commonSettingsPanel.setSize("100%", "100%");
		Label safelyDeleteLabel = new Label(settingsConstants.safelyDelete());
		safelyDeleteLabel.setTitle(settingsConstants.safelyDeleteDescription());
		commonSettingsPanel.add(safelyDeleteLabel);
		shouldAskBeforeDeleteCheckBox = new CheckBox();
		commonSettingsPanel.add(shouldAskBeforeDeleteCheckBox);
		commonSettingsPanel.setCellHorizontalAlignment(
				shouldAskBeforeDeleteCheckBox,
				HasHorizontalAlignment.ALIGN_RIGHT);
		layout.add(commonSettingsPanel);

		HorizontalPanel control = new HorizontalPanel();
		control.setSpacing(5);
		control.setSize("100%", "100%");
		abortButton = new Button(commonConstants.abort());
		abortButton.setWidth("100%");
		control.add(abortButton);
		saveButton = new Button(commonConstants.save());
		saveButton.setWidth("100%");
		control.add(saveButton);
		control.setCellWidth(saveButton, "65%");

		shouldAskBeforeDeleteCheckBox
				.addKeyPressHandler(new EnterKeyPressHandler(saveButton));

		layout.add(control);
		popup.setWidget(layout);
		return popup;
	}

	public Widget asWidget() {
		return popupPanel;
	}

	public void show() {
		popupPanel.center();
		shouldAskBeforeDeleteCheckBox.setFocus(true);
	}

	public void hide() {
		popupPanel.hide();
		shouldAskBeforeDeleteCheckBox.setText("");
	}

	public HasClickHandlers getSaveButton() {
		return saveButton;
	}

	public HasClickHandlers getAbortButton() {
		return abortButton;
	}

	public void setShouldAskBeforeDelete(boolean shouldAskBeforeDelete) {
		shouldAskBeforeDeleteCheckBox.setValue(shouldAskBeforeDelete);
	}

	public boolean shouldAskBeforeDelete() {
		return shouldAskBeforeDeleteCheckBox.getValue();
	}
}
