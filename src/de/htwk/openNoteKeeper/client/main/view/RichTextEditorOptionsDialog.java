package de.htwk.openNoteKeeper.client.main.view;

import java.util.LinkedList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.htwk.openNoteKeeper.client.i18n.CommonConstants;
import de.htwk.openNoteKeeper.client.i18n.SettingsConstants;

public class RichTextEditorOptionsDialog implements IsWidget {

	private final DialogBox popupDialog;

	private CheckBox boldCheckBox;
	private CheckBox italicCheckBox;
	private CheckBox underlineCheckBox;
	private CheckBox strikeCheckBox;
	private CheckBox fontCheckBox;
	private CheckBox fontSizeCheckBox;
	private CheckBox textColorCheckBox;
	private CheckBox numberedListCheckBox;
	private CheckBox bulletedListCheckBox;
	private CheckBox outdentCheckBox;
	private CheckBox indentCheckBox;
	private CheckBox linkCheckBox;
	private CheckBox imageCheckBox;
	private CheckBox tableCheckBox;

	private SettingsConstants settingsConstants = GWT
			.create(SettingsConstants.class);
	private CommonConstants commonConstants = GWT.create(CommonConstants.class);

	private List<String> editorOptions = new LinkedList<String>();

	public RichTextEditorOptionsDialog() {
		popupDialog = createLayout();
	}

	private DialogBox createLayout() {
		DialogBox popup = new DialogBox(true, true);
		popup.setAnimationEnabled(true);
		popup.setGlassEnabled(true);

		popup.setText(settingsConstants.richTextEditorOptions());
		popup.setSize("200px", "100px");

		VerticalPanel layout = new VerticalPanel();
		layout.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		layout.setSize("100%", "100%");

		FlexTable optionsPanel = new FlexTable();
		optionsPanel.setSize("100%", "100%");
		optionsPanel.getCellFormatter().setWidth(0, 0, "90%");

		optionsPanel.setWidget(0, 0, new Label(settingsConstants.bold()));
		boldCheckBox = new CheckBox();
		boldCheckBox.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

			public void onValueChange(ValueChangeEvent<Boolean> event) {
				updateTextEditorOptions();
			}
		});
		optionsPanel.setWidget(0, 1, boldCheckBox);

		optionsPanel.setWidget(1, 0, new Label(settingsConstants.italic()));
		italicCheckBox = new CheckBox();
		italicCheckBox.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

			public void onValueChange(ValueChangeEvent<Boolean> event) {
				updateTextEditorOptions();
			}
		});
		optionsPanel.setWidget(1, 1, italicCheckBox);

		optionsPanel.setWidget(2, 0, new Label(settingsConstants.underline()));
		underlineCheckBox = new CheckBox();
		underlineCheckBox
				.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

					public void onValueChange(ValueChangeEvent<Boolean> event) {
						updateTextEditorOptions();
					}
				});
		optionsPanel.setWidget(2, 1, underlineCheckBox);

		optionsPanel.setWidget(3, 0, new Label(settingsConstants.strike()));
		strikeCheckBox = new CheckBox();
		strikeCheckBox.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

			public void onValueChange(ValueChangeEvent<Boolean> event) {
				updateTextEditorOptions();
			}
		});
		optionsPanel.setWidget(3, 1, strikeCheckBox);

		optionsPanel.setWidget(4, 0, new Label(settingsConstants.font()));
		fontCheckBox = new CheckBox();
		fontCheckBox.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

			public void onValueChange(ValueChangeEvent<Boolean> event) {
				updateTextEditorOptions();
			}
		});
		optionsPanel.setWidget(4, 1, fontCheckBox);

		optionsPanel.setWidget(5, 0, new Label(settingsConstants.fontSize()));
		fontSizeCheckBox = new CheckBox();
		fontSizeCheckBox
				.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

					public void onValueChange(ValueChangeEvent<Boolean> event) {
						updateTextEditorOptions();
					}
				});
		optionsPanel.setWidget(5, 1, fontSizeCheckBox);

		optionsPanel.setWidget(6, 0, new Label(settingsConstants.textColor()));
		textColorCheckBox = new CheckBox();
		textColorCheckBox
				.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

					public void onValueChange(ValueChangeEvent<Boolean> event) {
						updateTextEditorOptions();
					}
				});
		optionsPanel.setWidget(6, 1, textColorCheckBox);

		optionsPanel.setWidget(7, 0,
				new Label(settingsConstants.numberedList()));
		numberedListCheckBox = new CheckBox();
		numberedListCheckBox
				.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

					public void onValueChange(ValueChangeEvent<Boolean> event) {
						updateTextEditorOptions();
					}
				});
		optionsPanel.setWidget(7, 1, numberedListCheckBox);

		optionsPanel.setWidget(8, 0,
				new Label(settingsConstants.bulletedList()));
		bulletedListCheckBox = new CheckBox();
		bulletedListCheckBox
				.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

					public void onValueChange(ValueChangeEvent<Boolean> event) {
						updateTextEditorOptions();
					}
				});
		optionsPanel.setWidget(8, 1, bulletedListCheckBox);

		optionsPanel.setWidget(9, 0, new Label(settingsConstants.outdent()));
		outdentCheckBox = new CheckBox();
		outdentCheckBox
				.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

					public void onValueChange(ValueChangeEvent<Boolean> event) {
						updateTextEditorOptions();
					}
				});
		optionsPanel.setWidget(9, 1, outdentCheckBox);

		optionsPanel.setWidget(10, 0, new Label(settingsConstants.indent()));
		indentCheckBox = new CheckBox();
		indentCheckBox.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

			public void onValueChange(ValueChangeEvent<Boolean> event) {
				updateTextEditorOptions();
			}
		});
		optionsPanel.setWidget(10, 1, indentCheckBox);

		optionsPanel.setWidget(11, 0, new Label(settingsConstants.link()));
		linkCheckBox = new CheckBox();
		linkCheckBox.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

			public void onValueChange(ValueChangeEvent<Boolean> event) {
				updateTextEditorOptions();
			}
		});
		optionsPanel.setWidget(11, 1, linkCheckBox);

		optionsPanel.setWidget(12, 0, new Label(settingsConstants.image()));
		imageCheckBox = new CheckBox();
		imageCheckBox.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

			public void onValueChange(ValueChangeEvent<Boolean> event) {
				updateTextEditorOptions();
			}
		});
		optionsPanel.setWidget(12, 1, imageCheckBox);

		optionsPanel.setWidget(13, 0, new Label(settingsConstants.table()));
		tableCheckBox = new CheckBox();
		tableCheckBox.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

			public void onValueChange(ValueChangeEvent<Boolean> event) {
				updateTextEditorOptions();
			}
		});
		optionsPanel.setWidget(13, 1, tableCheckBox);

		layout.add(optionsPanel);

		Button closeButton = new Button(commonConstants.close());
		closeButton.setWidth("100%");
		closeButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				popupDialog.hide();
			}
		});

		layout.add(closeButton);
		popup.setWidget(layout);
		return popup;
	}

	public Widget asWidget() {
		return popupDialog;
	}

	public void show(int x, int y) {
		popupDialog.setPopupPosition(x, y);
		popupDialog.show();
	}

	public void setTextEditorOptions(List<String> editorOptions) {
		boldCheckBox.setValue(editorOptions.contains("Bold"), true);
		italicCheckBox.setValue(editorOptions.contains("Italic"), true);
		underlineCheckBox.setValue(editorOptions.contains("Underline"), true);
		strikeCheckBox.setValue(editorOptions.contains("Strike"), true);
		fontCheckBox.setValue(editorOptions.contains("Font"), true);
		fontSizeCheckBox.setValue(editorOptions.contains("FontSize"), true);
		textColorCheckBox.setValue(editorOptions.contains("TextColor"), true);
		numberedListCheckBox.setValue(editorOptions.contains("NumberedList"),
				true);
		bulletedListCheckBox.setValue(editorOptions.contains("BulletedList"),
				true);
		outdentCheckBox.setValue(editorOptions.contains("Outdent"), true);
		indentCheckBox.setValue(editorOptions.contains("Indent"), true);
		linkCheckBox.setValue(editorOptions.contains("Link"), true);
		imageCheckBox.setValue(editorOptions.contains("Image"), true);
		tableCheckBox.setValue(editorOptions.contains("Table"), true);
	}

	public List<String> getTextEditorOptions() {
		return editorOptions;
	}

	private void updateTextEditorOptions() {
		editorOptions.clear();
		if (boldCheckBox.getValue()) {
			editorOptions.add("Bold");
		}
		if (italicCheckBox.getValue()) {
			editorOptions.add("Italic");
		}
		if (underlineCheckBox.getValue()) {
			editorOptions.add("Underline");
		}
		if (strikeCheckBox.getValue()) {
			editorOptions.add("Strike");
			editorOptions.add("_");
		}
		if (fontCheckBox.getValue()) {
			editorOptions.add("Font");
		}
		if (fontSizeCheckBox.getValue()) {
			editorOptions.add("FontSize");
		}
		if (textColorCheckBox.getValue()) {
			editorOptions.add("TextColor");
			editorOptions.add("_");
		}
		if (numberedListCheckBox.getValue()) {
			editorOptions.add("NumberedList");
		}
		if (bulletedListCheckBox.getValue()) {
			editorOptions.add("BulletedList");
		}
		if (outdentCheckBox.getValue()) {
			editorOptions.add("Outdent");
		}
		if (indentCheckBox.getValue()) {
			editorOptions.add("Indent");
			editorOptions.add("_");
		}
		if (linkCheckBox.getValue()) {
			editorOptions.add("Link");
		}
		if (imageCheckBox.getValue()) {
			editorOptions.add("Image");
		}
		if (tableCheckBox.getValue()) {
			editorOptions.add("Table");
		}
	}
}