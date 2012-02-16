package de.htwk.openNoteKeeper.client.note.view;

import java.util.List;

import com.allen_sauer.gwt.dnd.client.DragEndEvent;
import com.allen_sauer.gwt.dnd.client.DragHandler;
import com.allen_sauer.gwt.dnd.client.DragStartEvent;
import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.VetoDragException;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
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
import de.htwk.openNoteKeeper.client.note.presenter.DragAndDropTree;
import de.htwk.openNoteKeeper.client.note.presenter.HasTreeDropHandler;
import de.htwk.openNoteKeeper.client.note.presenter.NavigationTreePresenter.NavigationTreeView;
import de.htwk.openNoteKeeper.client.note.presenter.TreeGroupDragWidget;
import de.htwk.openNoteKeeper.client.note.presenter.TreeWhiteBoardDragWidget;
import de.htwk.openNoteKeeper.client.util.IconPool;
import de.htwk.openNoteKeeper.shared.GroupDTO;
import de.htwk.openNoteKeeper.shared.WhiteBoardDTO;

@Singleton
public class NavigationTreeViewImpl implements NavigationTreeView {

	private Tree navigationTree;
	private Image noteIcon;
	private Image trashIcon;
	private Image groupIcon;
	private Image whiteBoardIcon;

	private TreeItem selectedTreeItem;

	private TreeItem dragWidget;

	private PickupDragController dragController;
	private TreeDropController dropController;

	private NoteConstants constants = GWT.create(NoteConstants.class);

	private final Widget widget;

	public NavigationTreeViewImpl() {
		widget = createLayout();
	}

	public Widget createLayout() {
		VerticalPanel navigationPanel = new VerticalPanel();
		navigationPanel.addStyleName("navigation");
		navigationPanel.setSize("100%", "100%");
		navigationPanel
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

		final HorizontalPanel controlPanel = new HorizontalPanel();
		controlPanel.setSize("100%", "100%");
		navigationPanel.add(controlPanel);
		navigationPanel.setCellHeight(controlPanel, "10%");

		groupIcon = IconPool.Folder_Big.createImage();
		groupIcon.setTitle(constants.newGroup());
		groupIcon.setStyleName("clickable");
		controlPanel.add(groupIcon);

		whiteBoardIcon = IconPool.Blank_Sheet_Big.createImage();
		whiteBoardIcon.setTitle(constants.newWhiteBoard());
		whiteBoardIcon.setStyleName("clickable");
		controlPanel.add(whiteBoardIcon);

		noteIcon = IconPool.Notice_Big.createImage();
		noteIcon.setTitle(constants.newNote());
		noteIcon.setStyleName("clickable");
		controlPanel.add(noteIcon);

		trashIcon = IconPool.Trash_Big.createImage();
		trashIcon.setTitle(constants.delete());
		trashIcon.setStyleName("clickable");
		controlPanel.add(trashIcon);

		navigationTree = new DragAndDropTree();
		navigationTree.setSize("100%", "100%");
		navigationTree.setAnimationEnabled(true);
		navigationPanel.add(navigationTree);

		navigationTree.addSelectionHandler(new SelectionHandler<TreeItem>() {

			public void onSelection(SelectionEvent<TreeItem> event) {
				TreeItem selectedItem = event.getSelectedItem();

				Object userObject = selectedItem.getUserObject();
				if (selectedItem != null
						&& (userObject instanceof GroupDTO || userObject instanceof WhiteBoardDTO)) {
					selectedTreeItem = selectedItem;
				}
			}
		});

		AbsolutePanel boundaryPanel = new AbsolutePanel();
		boundaryPanel.setSize("100%", "100%");
		boundaryPanel.add(navigationPanel);
		dragController = new PickupDragController(boundaryPanel, true);
		dragController.setBehaviorMultipleSelection(false);
		dragController.setBehaviorDragProxy(false);
		dragController.setBehaviorConstrainedToBoundaryPanel(true);
		dragController.setConstrainWidgetToBoundaryPanel(true);

		dragController.addDragHandler(new DragHandler() {

			public void onPreviewDragStart(DragStartEvent event)
					throws VetoDragException {

			}

			public void onPreviewDragEnd(DragEndEvent event)
					throws VetoDragException {

			}

			public void onDragStart(DragStartEvent event) {
			}

			public void onDragEnd(DragEndEvent event) {

			}
		});

		dropController = new TreeDropController(navigationTree);
		dragController.registerDropController(dropController);

		return boundaryPanel;
	}

	public Widget asWidget() {
		return widget;
	}

	public void setGroups(List<GroupDTO> groups) {
		navigationTree.clear();
		for (GroupDTO group : groups) {
			TreeItem groupItem = createTreeItem(group);
			navigationTree.addItem(groupItem);
		}
	}

	private TreeItem createTreeItem(GroupDTO group) {
		TreeItem parent = createGroupTreeItem(group);
		for (GroupDTO childGroup : group.getSubGroups()) {
			final TreeItem groupItem = createTreeItem(childGroup);
			addTreeItemAndSetStyle(parent, groupItem);
		}
		for (WhiteBoardDTO whiteBoard : group.getWhiteBoards()) {
			TreeItem whiteBoardItem = createWhiteBoardTreeItem(whiteBoard);
			addTreeItemAndSetStyle(parent, whiteBoardItem);
		}
		return parent;
	}

	private void addTreeItemAndSetStyle(TreeItem parent, TreeItem child) {
		parent.addItem(child);

		// TODO bugfix to set full width in tree item parent
		Element div = parent.getElement();
		Element table = DOM.getFirstChild(div);
		Element tbody = DOM.getFirstChild(table);
		Element tr = DOM.getFirstChild(tbody);
		Element td = DOM.getChild(tr, 1);
		td.getStyle().setWidth(100, Unit.PCT);
	}

	private TreeItem createGroupTreeItem(GroupDTO group) {
		NavigationTreeItem navigationTreeItem = new NavigationTreeItem(
				new TreeGroupDragWidget(group), group.getTitle(), group);
		TreeItem item = navigationTreeItem.asTreeItem();
		dragController.makeDraggable(navigationTreeItem.asWidget(),
				navigationTreeItem.getDragHandle());
		return item;
	}

	private TreeItem createWhiteBoardTreeItem(WhiteBoardDTO whiteBoard) {
		NavigationTreeItem navigationTreeItem = new NavigationTreeItem(
				new TreeWhiteBoardDragWidget(whiteBoard),
				whiteBoard.getTitle(), whiteBoard);
		dragController.makeDraggable(navigationTreeItem.asWidget(),
				navigationTreeItem.getDragHandle());
		return navigationTreeItem.asTreeItem();
	}

	public boolean hasSelectedGroup() {
		TreeItem selectedItem = getSelectedTreeItem();
		return selectedItem != null
				&& selectedItem.getUserObject() instanceof GroupDTO;
	}

	public GroupDTO getSelectedGroup() {
		TreeItem selectedGroup = getSelectedTreeItem();
		return (GroupDTO) selectedGroup.getUserObject();
	}

	public void removeSelectedGroup() {
		TreeItem selectedGroup = getSelectedTreeItem();
		selectedGroup.remove();
	}

	public void removeSelectedWhiteBoard() {
		TreeItem selectedWhiteBoard = getSelectedTreeItem();
		selectedWhiteBoard.remove();
	}

	public boolean hasSelectedWhiteBoard() {
		TreeItem selectedItem = getSelectedTreeItem();
		return selectedItem != null
				&& selectedItem.getUserObject() instanceof WhiteBoardDTO;
	}

	public WhiteBoardDTO getSelectedWhiteBoard() {
		TreeItem selectedItem = getSelectedTreeItem();
		return (WhiteBoardDTO) selectedItem.getUserObject();
	}

	public HasClickHandlers getRemoveButton() {
		return trashIcon;
	}

	public HasTreeDropHandler getDropHandler() {
		return dropController;
	}

	public void addGroupToTree(GroupDTO group) {
		if (hasSelectedGroup()) {
			TreeItem selectedGroup = getSelectedTreeItem();
			addTreeItemAndSetStyle(selectedGroup, createGroupTreeItem(group));
		}
	}

	public TreeItem getDragWidget() {
		return dragWidget;
	}

	public void addWhiteBoardToGroup(WhiteBoardDTO whiteboard) {
		if (hasSelectedGroup()) {
			TreeItem selectedGroup = getSelectedTreeItem();
			addTreeItemAndSetStyle(selectedGroup,
					createWhiteBoardTreeItem(whiteboard));
		}
	}

	public HasClickHandlers getAddGroupButton() {
		return groupIcon;
	}

	public HasClickHandlers getAddWhiteBoardButton() {
		return whiteBoardIcon;
	}

	public TreeItem getSelectedTreeItem() {
		if (navigationTree.getSelectedItem() != null)
			return navigationTree.getSelectedItem();
		else
			return selectedTreeItem;
	}
}
