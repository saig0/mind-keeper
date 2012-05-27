package de.htwk.openNoteKeeper.client.main.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.htwk.openNoteKeeper.client.i18n.CommonConstants;
import de.htwk.openNoteKeeper.client.i18n.SettingsConstants;
import de.htwk.openNoteKeeper.client.main.presenter.SettingsPresenter.SettingsView;
import de.htwk.openNoteKeeper.client.util.EnterKeyPressHandler;
import de.htwk.openNoteKeeper.client.widget.ColorPicker;

public class SettingsViewImpl implements SettingsView {

	private final DialogBox popupPanel;
	private Button saveButton;
	private Button abortButton;
	private CheckBox shouldAskBeforeDeleteCheckBox;
	private ColorPicker defaultNoteColorPicker;
	private CheckBox shouldUseRichTextEditorCheckBox;
	private Button openRichTextEditorOptionsDialogButton;
	private RichTextEditorOptionsDialog richTextEditorOptionsDialog;

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

		FlexTable settingsPanel = new FlexTable();
		settingsPanel.setSize("100%", "100%");
		settingsPanel.getCellFormatter().setWidth(0, 0, "60%");

		Label safelyDeleteLabel = new Label(settingsConstants.safelyDelete());
		safelyDeleteLabel.setTitle(settingsConstants.safelyDeleteDescription());
		settingsPanel.setWidget(0, 0, safelyDeleteLabel);

		shouldAskBeforeDeleteCheckBox = new CheckBox();
		settingsPanel.setWidget(0, 1, shouldAskBeforeDeleteCheckBox);

		Label defaultNoteColorLabel = new Label(
				settingsConstants.defaultNoteColor());
		defaultNoteColorLabel.setTitle(settingsConstants
				.defaultNoteColorDescription());
		settingsPanel.setWidget(1, 0, defaultNoteColorLabel);

		defaultNoteColorPicker = new ColorPicker("#ffffff");
		settingsPanel.setWidget(1, 1, defaultNoteColorPicker);

		Label shouldUseRichTextEditorLabel = new Label(
				settingsConstants.richTextEditor());
		shouldUseRichTextEditorLabel.setTitle(settingsConstants
				.richTextEditorDescription());
		settingsPanel.setWidget(2, 0, shouldUseRichTextEditorLabel);

		shouldUseRichTextEditorCheckBox = new CheckBox();
		shouldUseRichTextEditorCheckBox
				.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

					public void onValueChange(ValueChangeEvent<Boolean> event) {
						if (shouldUseRichTextEditorCheckBox.getValue()) {
							openRichTextEditorOptionsDialogButton
									.setEnabled(true);
						} else {
							openRichTextEditorOptionsDialogButton
									.setEnabled(false);
						}
					}
				});
		settingsPanel.setWidget(2, 1, shouldUseRichTextEditorCheckBox);

		Label richTextEditorOptionsLabel = new Label(
				settingsConstants.richTextEditorOptions());
		richTextEditorOptionsLabel.setTitle(settingsConstants
				.richTextEditorOptionsDescription());
		settingsPanel.setWidget(3, 0, richTextEditorOptionsLabel);

		richTextEditorOptionsDialog = new RichTextEditorOptionsDialog();
		openRichTextEditorOptionsDialogButton = new Button(
				commonConstants.open());
		openRichTextEditorOptionsDialogButton.setWidth("100%");
		openRichTextEditorOptionsDialogButton.setEnabled(false);
		openRichTextEditorOptionsDialogButton
				.addClickHandler(new ClickHandler() {

					public void onClick(ClickEvent event) {
						richTextEditorOptionsDialog.show(event.getClientX(),
								event.getClientY());
					}
				});
		settingsPanel.setWidget(3, 1, openRichTextEditorOptionsDialogButton);

		layout.add(settingsPanel);

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

	public void setDefaultNoteColor(String color) {
		defaultNoteColorPicker.setColor(color);
	}

	public String getDefaultNoteColor() {
		return defaultNoteColorPicker.getColor();
	}

	public void setShouldUseRichTextEditor(boolean shouldUseRichTextEditor) {
		shouldUseRichTextEditorCheckBox.setValue(shouldUseRichTextEditor, true);
	}

	public boolean shouldUseRichTextEditor() {
		return shouldUseRichTextEditorCheckBox.getValue();
	}
}
