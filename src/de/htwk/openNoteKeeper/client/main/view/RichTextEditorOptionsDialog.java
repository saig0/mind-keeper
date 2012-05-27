package de.htwk.openNoteKeeper.client.main.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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

	private SettingsConstants settingsConstants = GWT
			.create(SettingsConstants.class);
	private CommonConstants commonConstants = GWT.create(CommonConstants.class);

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

		optionsPanel.setWidget(0, 0, new Label("Option 1"));
		optionsPanel.setWidget(0, 1, new CheckBox());

		optionsPanel.setWidget(1, 0, new Label("Option 2"));
		optionsPanel.setWidget(1, 1, new CheckBox());

		optionsPanel.setWidget(2, 0, new Label("Option 3"));
		optionsPanel.setWidget(2, 1, new CheckBox());

		optionsPanel.setWidget(3, 0, new Label("Option 4"));
		optionsPanel.setWidget(3, 1, new CheckBox());

		optionsPanel.setWidget(4, 0, new Label("Option 5"));
		optionsPanel.setWidget(4, 1, new CheckBox());

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
}