package de.htwk.openNoteKeeper.client.note.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.htwk.openNoteKeeper.client.note.i18n.NoteConstants;
import de.htwk.openNoteKeeper.client.note.presenter.NavigationInputPresenter.NavigationInputView;
import de.htwk.openNoteKeeper.client.util.EnterKeyPressHandler;

public class NavigationInputViewImpl implements NavigationInputView {

	private NoteConstants constants = GWT.create(NoteConstants.class);

	private TextBox inputNameField;
	private PushButton saveButton;
	private PushButton cancelButton;
	private Image icon = new Image();

	private final TreeItem treeItem;

	public NavigationInputViewImpl() {
		treeItem = createLayout();
	}

	public TreeItem asTreeItem() {
		return treeItem;
	}

	private TreeItem createLayout() {
		HorizontalPanel panel = new HorizontalPanel();
		panel.setSize("100%", "100%");
		icon.setSize("24px", "24px");
		panel.add(icon);
		panel.setCellWidth(icon, "30px");

		VerticalPanel newGroupPanel = new VerticalPanel();
		panel.add(newGroupPanel);
		inputNameField = new TextBox();
		newGroupPanel.add(inputNameField);
		inputNameField.setFocus(true);
		HorizontalPanel buttonPanel = new HorizontalPanel();
		buttonPanel.setSize("100%", "100%");
		newGroupPanel.add(buttonPanel);
		saveButton = new PushButton(constants.save());
		buttonPanel.add(saveButton);
		inputNameField.addKeyPressHandler(new EnterKeyPressHandler(saveButton));
		buttonPanel.setCellWidth(saveButton, "75%");
		cancelButton = new PushButton(constants.abort());
		buttonPanel.add(cancelButton);

		TreeItem inputTreeItem = new TreeItem(panel);
		return inputTreeItem;
	}

	public HasClickHandlers getCancelInputButton() {
		return cancelButton;
	}

	public HasClickHandlers getSaveInputButton() {
		return saveButton;
	}

	public String getNameOfInputField() {
		return inputNameField.getValue();
	}

	public void show(TreeItem selectedGroup) {
		selectedGroup.setState(true, false);
		inputNameField.setFocus(true);
	}

	public void hide() {
		treeItem.remove();
		inputNameField.setText("");
	}

	public Image getIcon() {
		return icon;
	}

}
