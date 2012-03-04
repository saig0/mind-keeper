package de.htwk.openNoteKeeper.client.note.view.actionBar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.htwk.openNoteKeeper.client.i18n.CommonConstants;
import de.htwk.openNoteKeeper.client.note.i18n.NoteConstants;
import de.htwk.openNoteKeeper.client.note.presenter.actionBar.NoteCreationPresenter.NoteCreationView;
import de.htwk.openNoteKeeper.client.util.EnterKeyPressHandler;

public class NoteCreationViewImpl implements NoteCreationView {

	private DialogBox popupPanel;
	private Button createButton;
	private Button abortButton;
	private TextBox noteNameTextBox;

	private NoteConstants noteConstants = GWT.create(NoteConstants.class);
	private CommonConstants commonConstants = GWT.create(CommonConstants.class);

	public NoteCreationViewImpl() {
		popupPanel = createLayout();
	}

	private DialogBox createLayout() {
		DialogBox popup = new DialogBox(false, true);
		popup.setAnimationEnabled(true);
		popup.setGlassEnabled(true);

		popup.setText(noteConstants.newNote());
		popup.setSize("300px", "100px");

		VerticalPanel layout = new VerticalPanel();
		layout.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		// layout.setSpacing(5);
		layout.setSize("100%", "100%");

		HorizontalPanel content = new HorizontalPanel();
		content.setSpacing(5);
		content.setSize("100%", "100%");
		content.add(new Label(noteConstants.noteName()));
		noteNameTextBox = new TextBox();
		// TODO width fix
		noteNameTextBox.setWidth("95%");
		content.add(noteNameTextBox);
		content.setCellWidth(noteNameTextBox, "65%");

		HorizontalPanel control = new HorizontalPanel();
		control.setSpacing(5);
		control.setSize("100%", "100%");
		abortButton = new Button(commonConstants.abort());
		abortButton.setWidth("100%");
		control.add(abortButton);
		createButton = new Button(commonConstants.create());
		createButton.setWidth("100%");
		control.add(createButton);
		control.setCellWidth(createButton, "65%");

		noteNameTextBox.addKeyPressHandler(new EnterKeyPressHandler(
				createButton));

		layout.add(content);
		layout.add(control);
		popup.setWidget(layout);
		return popup;
	}

	public Widget asWidget() {
		return popupPanel;
	}

	public void show() {
		popupPanel.center();
		noteNameTextBox.setFocus(true);
	}

	public void hide() {
		popupPanel.hide();
		noteNameTextBox.setText("");
	}

	public String getName() {
		return noteNameTextBox.getText();
	}

	public HasClickHandlers getCreateButton() {
		return createButton;
	}

	public HasClickHandlers getAbortButton() {
		return abortButton;
	}

}