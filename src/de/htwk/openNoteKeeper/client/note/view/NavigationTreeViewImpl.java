package de.htwk.openNoteKeeper.client.note.view;

import java.util.List;

import com.allen_sauer.gwt.dnd.client.DragEndEvent;
import com.allen_sauer.gwt.dnd.client.DragHandler;
import com.allen_sauer.gwt.dnd.client.DragStartEvent;
import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.VetoDragException;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;

import de.htwk.openNoteKeeper.client.note.i18n.NoteConstants;
import de.htwk.openNoteKeeper.client.note.presenter.HasTreeDropHandler;
import de.htwk.openNoteKeeper.client.note.presenter.NavigationTreePresenter.NavigationTreeView;
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

	private TreeDropController dropController;

	private NoteConstants constants = GWT.create(NoteConstants.class);

	private Widget dragWidget;

	private final Widget widget;

	public NavigationTreeViewImpl() {
		widget = createLayout();
	}

	public Widget createLayout() {
		VerticalPanel navigationPanel = new VerticalPanel();
		navigationPanel.setSize("100%", "100%");
		navigationPanel
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

		final HorizontalPanel controlPanel = new HorizontalPanel();
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
		navigationTree.setAnimationEnabled(true);
		navigationPanel.add(navigationTree);

		AbsolutePanel p = new AbsolutePanel();
		p.setSize("100%", "100%");
		p.add(navigationPanel);
		final PickupDragController dragController = new PickupDragController(p,
				true);
		dragController.setBehaviorMultipleSelection(false);
		dragController.setBehaviorDragProxy(false);
		dragController.setBehaviorConstrainedToBoundaryPanel(true);

		dragController.addDragHandler(new DragHandler() {

			public void onPreviewDragStart(DragStartEvent event)
					throws VetoDragException {
				dragWidget = event.getContext().draggable;
				int i = controlPanel.getWidgetIndex(dragWidget);
				if (i != -1) {
					Widget clone = IconPool.Folder_Big.createImage();
					dragController.makeDraggable(clone);
					controlPanel.insert(clone, i);
				}
			}

			public void onPreviewDragEnd(DragEndEvent event)
					throws VetoDragException {
			}

			public void onDragStart(DragStartEvent event) {
			}

			public void onDragEnd(DragEndEvent event) {
				event.getContext().draggable.removeFromParent();
			}
		});

		dropController = new TreeDropController(navigationTree);
		dragController.registerDropController(dropController);
		dragController.makeDraggable(groupIcon);

		return p;
	}

	public Widget asWidget() {
		return widget;
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
			final TreeItem groupItem = createGroupTreeItem(group);
			createTreeItem(group.getSubGroups(), groupItem);
			parent.addItem(groupItem);

			for (WhiteBoardDTO whiteBoard : group.getWhiteBoards()) {
				TreeItem whiteBoardItem = createWhiteBoardTreeItem(whiteBoard);
				parent.addItem(whiteBoardItem);
			}
		}
	}

	private TreeItem createGroupTreeItem(GroupDTO group) {
		return new NavigationTreeItem(IconPool.Folder.createImage(),
				group.getTitle(), group).asWidget();
	}

	private TreeItem createWhiteBoardTreeItem(WhiteBoardDTO whiteBoard) {
		return new NavigationTreeItem(IconPool.Blank_Sheet.createImage(),
				whiteBoard.getTitle(), whiteBoard).asWidget();
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

	public void addGroupToSelectedGroup(GroupDTO group) {
		// TODO Auto-generated method stub

	}

	public void addWhiteBoardToSelectedGroup(WhiteBoardDTO whiteboard) {
		// TODO Auto-generated method stub

	}

	public void hideInputField() {
		// TODO Auto-generated method stub

	}

	public HasClickHandlers getRemoveButton() {
		return trashIcon;
	}

	public HasTreeDropHandler getDropHandler() {
		return dropController;
	}

	public void addGroupToTree(TreeItem treeItem, GroupDTO group) {
		treeItem.addItem(createGroupTreeItem(group));

	}

}
