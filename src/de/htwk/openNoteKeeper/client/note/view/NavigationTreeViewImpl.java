package de.htwk.openNoteKeeper.client.note.view;

import java.util.List;

import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;

import de.htwk.openNoteKeeper.client.note.presenter.NavigationTreePresenter.NavigationTreeView;
import de.htwk.openNoteKeeper.client.util.IconPool;
import de.htwk.openNoteKeeper.shared.GroupDTO;

@Singleton
public class NavigationTreeViewImpl implements NavigationTreeView {

	private Tree navigationTree;

	public Widget asWidget() {
		VerticalPanel navigationPanel = new VerticalPanel();
		navigationPanel.setSize("100%", "100%");
		navigationPanel
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		HorizontalPanel controlPanel = new HorizontalPanel();
		controlPanel.setSize("100%", "100%");
		navigationPanel.add(controlPanel);

		PushButton newGroupButton = new PushButton(
				IconPool.Folder_Big.createImage());
		controlPanel.add(newGroupButton);

		PushButton newWhiteBoardButton = new PushButton(
				IconPool.Blank_Sheet_Big.createImage());
		controlPanel.add(newWhiteBoardButton);

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

	public void createTreeItem(List<GroupDTO> groups, TreeItem parent) {
		for (GroupDTO group : groups) {
			TreeItem groupItem = createGroupTreeItem(group);
			createTreeItem(group.getSubGroups(), groupItem);
			parent.addItem(groupItem);
		}
	}

	public TreeItem createGroupTreeItem(GroupDTO group) {
		HorizontalPanel panel = new HorizontalPanel();
		panel.setSize("100%", "100%");
		Image icon = IconPool.Folder.createImage();
		icon.setSize("24px", "24px");
		panel.add(icon);
		panel.setCellWidth(icon, "30px");
		panel.add(new Label(group.getTitle()));
		TreeItem groupItem = new TreeItem(panel);
		return groupItem;
	}

}
