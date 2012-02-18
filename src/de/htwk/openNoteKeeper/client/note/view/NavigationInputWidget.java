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
import de.htwk.openNoteKeeper.client.note.presenter.NavigationTreePresenter.NavigationInputView;
import de.htwk.openNoteKeeper.client.util.EnterKeyPressHandler;
import de.htwk.openNoteKeeper.shared.GroupDTO;

public class NavigationInputWidget implements NavigationInputView {

	private NoteConstants constants = GWT.create(NoteConstants.class);

	private TextBox inputNameField;
	private PushButton saveButton;
	private PushButton cancelButton;

	private final TreeItem treeItem;

	public NavigationInputWidget(Image icon) {
		treeItem = createLayout(icon);
	}

	private TreeItem createLayout(Image icon) {
		HorizontalPanel panel = new HorizontalPanel();
		panel.setSize("100%", "100%");
		icon.setSize("24px", "24px");
		panel.add(icon);
		panel.setCellWidth(icon, "30px");

		VerticalPanel newGroupPanel = new VerticalPanel();
		panel.add(newGroupPanel);
		inputNameField = new TextBox();
		newGroupPanel.add(inputNameField);
		HorizontalPanel buttonPanel = new HorizontalPanel();
		buttonPanel.setSize("100%", "100%");
		newGroupPanel.add(buttonPanel);
		saveButton = new PushButton(constants.save());
		buttonPanel.add(saveButton);
		inputNameField.addKeyPressHandler(new EnterKeyPressHandler(saveButton));
		buttonPanel.setCellWidth(saveButton, "75%");
		cancelButton = new PushButton(constants.abort());
		buttonPanel.add(cancelButton);
		// inputNameField.addBlurHandler(new BlurToClickHandler(cancelButton));

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
		addTreeItem(selectedGroup, treeItem);

		selectedGroup.setState(true, false);
		inputNameField.setFocus(true);
	}

	// TODO DRY verletzt!
	private void addTreeItem(TreeItem parent, TreeItem child) {
		if (parent.getUserObject() instanceof GroupDTO) {
			int index = getIndexOfLastGroupItem(parent);
			parent.insertItem(index, child);
		} else {
			parent.addItem(child);
		}
	}

	private int getIndexOfLastGroupItem(TreeItem item) {
		int index = 0;
		for (int i = 0; i < item.getChildCount(); i++) {
			if (item.getChild(i).getUserObject() instanceof GroupDTO) {
				index = i;
			}
		}
		return index + 1;
	}

	public void hide() {
		treeItem.remove();
		inputNameField.setText("");
	}

}
