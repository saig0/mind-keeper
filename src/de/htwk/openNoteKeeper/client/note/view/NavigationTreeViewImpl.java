package de.htwk.openNoteKeeper.client.note.view;

import java.util.List;

import com.google.gwt.event.dom.client.HasBlurHandlers;
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
import com.google.inject.Singleton;

import de.htwk.openNoteKeeper.client.note.presenter.NavigationTreePresenter.NavigationTreeView;
import de.htwk.openNoteKeeper.client.util.BlurToClickHandler;
import de.htwk.openNoteKeeper.client.util.EnterKeyPressHandler;
import de.htwk.openNoteKeeper.client.util.IconPool;
import de.htwk.openNoteKeeper.shared.GroupDTO;

@Singleton
public class NavigationTreeViewImpl implements NavigationTreeView {

	private Tree navigationTree;
	private PushButton newGroupButton;
	private PushButton trashButton;
	private TextBox newGroupNameField;
	private TreeItem newGroupTreeItem;
	private PushButton saveButton;
	private PushButton cancelButton;

	public Widget asWidget() {
		VerticalPanel navigationPanel = new VerticalPanel();
		navigationPanel.setSize("100%", "100%");
		navigationPanel
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

		HorizontalPanel controlPanel = new HorizontalPanel();
		controlPanel.setSize("100%", "100%");
		navigationPanel.add(controlPanel);

		newGroupButton = new PushButton(IconPool.Folder_Big.createImage());
		controlPanel.add(newGroupButton);

		PushButton newWhiteBoardButton = new PushButton(
				IconPool.Blank_Sheet_Big.createImage());
		controlPanel.add(newWhiteBoardButton);

		trashButton = new PushButton(IconPool.Trash_Big.createImage());
		controlPanel.add(trashButton);

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
		}
	}

	private TreeItem createGroupTreeItem(GroupDTO group) {
		HorizontalPanel panel = new HorizontalPanel();
		panel.setSize("100%", "100%");
		Image icon = IconPool.Folder.createImage();
		icon.setSize("24px", "24px");
		panel.add(icon);
		panel.setCellWidth(icon, "30px");
		panel.add(new Label(group.getTitle()));
		TreeItem groupItem = new TreeItem(panel);
		groupItem.setUserObject(group);
		return groupItem;
	}

	public HasClickHandlers getCreateNewGroupButton() {
		return newGroupButton;
	}

	public boolean hasSelectedGroup() {
		return navigationTree.getSelectedItem() != null;
	}

	public void showNewGroupNameField() {
		TreeItem selectedGroup = navigationTree.getSelectedItem();

		HorizontalPanel panel = new HorizontalPanel();
		panel.setSize("100%", "100%");
		Image icon = IconPool.Folder.createImage();
		icon.setSize("24px", "24px");
		panel.add(icon);
		panel.setCellWidth(icon, "30px");

		VerticalPanel newGroupPanel = new VerticalPanel();
		panel.add(newGroupPanel);
		newGroupNameField = new TextBox();
		newGroupPanel.add(newGroupNameField);
		HorizontalPanel buttonPanel = new HorizontalPanel();
		buttonPanel.setSize("100%", "100%");
		newGroupPanel.add(buttonPanel);
		saveButton = new PushButton("save");
		buttonPanel.add(saveButton);
		newGroupNameField.addKeyPressHandler(new EnterKeyPressHandler(
				saveButton));
		buttonPanel.setCellWidth(saveButton, "75%");
		cancelButton = new PushButton("cancel");
		buttonPanel.add(cancelButton);
		newGroupNameField.addBlurHandler(new BlurToClickHandler(cancelButton));

		newGroupTreeItem = new TreeItem(panel);
		selectedGroup.addItem(newGroupTreeItem);

		selectedGroup.setState(true, false);
		newGroupNameField.setFocus(true);
	}

	public HasBlurHandlers getNewGroupNameField() {
		return newGroupNameField;
	}

	public String getNewGroupName() {
		return newGroupNameField.getValue();
	}

	public GroupDTO getSelectedGroup() {
		TreeItem selectedGroup = navigationTree.getSelectedItem();
		return (GroupDTO) selectedGroup.getUserObject();
	}

	public void addGroupToSelected(GroupDTO group) {
		TreeItem selectedGroup = navigationTree.getSelectedItem();
		selectedGroup.addItem(createGroupTreeItem(group));
	}

	public void hideNewGroupField() {
		newGroupTreeItem.remove();
	}

	public HasClickHandlers getCancelNewGroupButton() {
		return cancelButton;
	}

	public HasClickHandlers getSaveNewGroupButton() {
		return saveButton;
	}

	public HasClickHandlers getRemoveGroupButton() {
		return trashButton;
	}

	public void removeSelectedGroup() {
		TreeItem selectedGroup = navigationTree.getSelectedItem();
		selectedGroup.remove();
	}

}
