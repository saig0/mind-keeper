package de.htwk.openNoteKeeper.client.note.view;

import java.util.List;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.htwk.openNoteKeeper.client.note.i18n.NoteConstants;
import de.htwk.openNoteKeeper.client.note.presenter.NavigationTreePresenter.NavigationTreeView;
import de.htwk.openNoteKeeper.client.util.EnterKeyPressHandler;
import de.htwk.openNoteKeeper.client.util.IconPool;
import de.htwk.openNoteKeeper.shared.GroupDTO;
import de.htwk.openNoteKeeper.shared.WhiteBoardDTO;

@Singleton
public class NavigationTreeViewImpl implements NavigationTreeView {

	private Tree navigationTree;
	private Image groupIcon;
	private Image whiteboardIcon;
	private Image noteIcon;
	private Image trashIcon;
	private TextBox inputNameField;
	private TreeItem inputTreeItem;
	private PushButton saveButton;
	private PushButton cancelButton;
	private TreeItem selectedGroup;

	@Inject
	private NoteConstants constants;

	public Widget asWidget() {
		VerticalPanel navigationPanel = new VerticalPanel();
		navigationPanel.setSize("100%", "100%");
		navigationPanel
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

		HorizontalPanel controlPanel = new HorizontalPanel();
		controlPanel.setSize("100%", "100%");
		navigationPanel.add(controlPanel);

		groupIcon = IconPool.Folder_Big.createImage();
		groupIcon.setTitle(constants.newGroup());
		groupIcon.setStyleName("clickable");
		controlPanel.add(groupIcon);

		whiteboardIcon = IconPool.Blank_Sheet_Big.createImage();
		whiteboardIcon.setTitle(constants.newWhiteBoard());
		whiteboardIcon.setStyleName("clickable");
		controlPanel.add(whiteboardIcon);

		noteIcon = IconPool.Notice_Big.createImage();
		noteIcon.setTitle(constants.newNote());
		noteIcon.setStyleName("clickable");
		controlPanel.add(noteIcon);

		trashIcon = IconPool.Trash_Big.createImage();
		trashIcon.setTitle(constants.delete());
		trashIcon.setStyleName("clickable");
		controlPanel.add(trashIcon);

		navigationTree = new Tree();
		navigationTree.setSize("100%", "100%");
		navigationPanel.add(navigationTree);

		return navigationPanel;
	}

	public void setGroups(List<GroupDTO> groups) {
		navigationTree.clear();
		for (GroupDTO group : groups) {
			TreeItem groupItem = createGroupTreeItem(group);
			createTreeItem(group.getSubGroups(), groupItem);
			navigationTree.addItem(groupItem);
		}
	}

	private void createTreeItem(List<GroupDTO> groups, TreeItem parent) {
		for (GroupDTO group : groups) {
			TreeItem groupItem = createGroupTreeItem(group);
			createTreeItem(group.getSubGroups(), groupItem);
			parent.addItem(groupItem);

			for (WhiteBoardDTO whiteBoard : group.getWhiteBoards()) {
				TreeItem whiteBoardItem = createWhiteBoardTreeItem(whiteBoard);
				parent.addItem(whiteBoardItem);
			}
		}
	}

	private TreeItem createGroupTreeItem(GroupDTO group) {
		return createTreeItem(IconPool.Folder.createImage(), group.getTitle(),
				group);
	}

	private TreeItem createTreeItem(Image icon, String title, Object userObject) {
		HorizontalPanel panel = new HorizontalPanel();
		panel.setSize("100%", "100%");
		icon.setSize("24px", "24px");
		panel.add(icon);
		panel.setCellWidth(icon, "30px");
		panel.add(new Label(title));
		TreeItem groupItem = new TreeItem(panel);
		groupItem.setUserObject(userObject);
		return groupItem;
	}

	private TreeItem createWhiteBoardTreeItem(WhiteBoardDTO whiteBoard) {
		return createTreeItem(IconPool.Blank_Sheet.createImage(),
				whiteBoard.getTitle(), whiteBoard);
	}

	public HasClickHandlers getCreateNewGroupButton() {
		return groupIcon;
	}

	public boolean hasSelectedGroup() {
		TreeItem selectedItem = navigationTree.getSelectedItem();
		return selectedItem != null
				&& selectedItem.getUserObject() instanceof GroupDTO;
	}

	public GroupDTO getSelectedGroup() {
		TreeItem selectedGroup = navigationTree.getSelectedItem();
		return (GroupDTO) selectedGroup.getUserObject();
	}

	public void removeSelectedGroup() {
		TreeItem selectedGroup = navigationTree.getSelectedItem();
		selectedGroup.remove();
	}

	public void showNewGroupInputField() {
		if (inputTreeItem == null)
			createNewInputFieldInTree(IconPool.Folder.createImage());
	}

	private void createNewInputFieldInTree(Image icon) {
		selectedGroup = navigationTree.getSelectedItem();

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

		inputTreeItem = new TreeItem(panel);
		selectedGroup.addItem(inputTreeItem);

		selectedGroup.setState(true, false);
		inputNameField.setFocus(true);
	}

	public void showNewWhiteBoardInputField() {
		if (inputTreeItem == null)
			createNewInputFieldInTree(IconPool.Blank_Sheet.createImage());
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

	public void addGroupToSelectedGroup(GroupDTO group) {
		selectedGroup.addItem(createGroupTreeItem(group));
	}

	public void addWhiteBoardToSelectedGroup(WhiteBoardDTO whiteboard) {
		selectedGroup.addItem(createWhiteBoardTreeItem(whiteboard));
	}

	public void hideInputField() {
		if (inputTreeItem != null)
			inputTreeItem.remove();
		inputTreeItem = null;
	}

	public HasClickHandlers getRemoveButton() {
		return trashIcon;
	}

	public void removeSelectedWhiteBoard() {
		TreeItem selectedWhiteBoard = navigationTree.getSelectedItem();
		selectedWhiteBoard.remove();
	}

	public HasClickHandlers getCreateNewWhiteBoardButton() {
		return whiteboardIcon;
	}

	public boolean hasSelectedWhiteBoard() {
		TreeItem selectedItem = navigationTree.getSelectedItem();
		return selectedItem != null
				&& selectedItem.getUserObject() instanceof WhiteBoardDTO;
	}

	public WhiteBoardDTO getSelectedWhiteBoard() {
		TreeItem selectedItem = navigationTree.getSelectedItem();
		return (WhiteBoardDTO) selectedItem.getUserObject();
	}

}
