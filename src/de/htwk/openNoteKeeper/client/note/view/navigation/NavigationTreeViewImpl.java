package de.htwk.openNoteKeeper.client.note.view.navigation;

import java.util.List;

import com.allen_sauer.gwt.dnd.client.DragController;
import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
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
import de.htwk.openNoteKeeper.client.note.presenter.navigation.HasTreeDropHandler;
import de.htwk.openNoteKeeper.client.note.presenter.navigation.NavigationTreePresenter.NavigationTreeView;
import de.htwk.openNoteKeeper.client.util.IconPool;
import de.htwk.openNoteKeeper.shared.GroupDTO;
import de.htwk.openNoteKeeper.shared.WhiteBoardDTO;

@Singleton
public class NavigationTreeViewImpl implements NavigationTreeView {

	private Tree navigationTree;

	private Image trashIcon;
	private Image groupIcon;
	private Image whiteBoardIcon;

	private TreeItem selectedTreeItem;

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

		trashIcon = IconPool.Trash_Big.createImage();
		trashIcon.setTitle(constants.delete());
		trashIcon.setStyleName("clickable");
		controlPanel.add(trashIcon);

		navigationTree = new DragAndDropTreeAdapter();
		navigationTree.setSize("100%", "100%");
		navigationTree.setAnimationEnabled(true);
		navigationPanel.add(navigationTree);

		navigationTree.addSelectionHandler(new SelectionHandler<TreeItem>() {

			public void onSelection(SelectionEvent<TreeItem> event) {
				TreeItem selectedItem = event.getSelectedItem();

				Object userObject = selectedItem.getUserObject();
				if (selectedItem != null && userObject != null) {
					if (userObject instanceof GroupDTO
							&& !((GroupDTO) userObject).getKey().contains(
									"dummy")) {
						selectedTreeItem = selectedItem;
					} else if (userObject instanceof WhiteBoardDTO
							&& !((WhiteBoardDTO) userObject).getKey().contains(
									"dummy")) {
						selectedTreeItem = selectedItem;
					}
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

		dropController = new TreeDropController(navigationTree);
		dragController.registerDropController(dropController);

		return boundaryPanel;
	}

	public Widget asWidget() {
		return widget;
	}

	public void setGroups(List<TreeItem> groupItems) {
		for (TreeItem groupItem : groupItems) {
			TreeItem oldItem = getExistingTreeItem(groupItem);
			if (oldItem != null) {
				mergeTreeItems(oldItem, groupItem);
			} else {
				navigationTree.addItem(groupItem);
			}
		}
	}

	private TreeItem getExistingTreeItem(TreeItem groupItem) {
		GroupDTO group = getGroupOfTreeItem(groupItem);
		if (group != null) {
			String key = group.getKey();
			for (int i = 0; i < navigationTree.getItemCount(); i++) {
				TreeItem treeItem = navigationTree.getItem(i);
				GroupDTO groupOfItem = getGroupOfTreeItem(treeItem);
				if (groupOfItem != null && key.equals(groupOfItem.getKey())) {
					return treeItem;
				}
			}
		}
		return null;
	}

	private GroupDTO getGroupOfTreeItem(TreeItem item) {
		if (item.getWidget() instanceof TreeItemViewImpl) {
			TreeItemViewImpl navigationTreeItem = (TreeItemViewImpl) item
					.getWidget();
			Object userObject = navigationTreeItem.asTreeItem().getUserObject();
			if (userObject instanceof GroupDTO) {
				GroupDTO group = (GroupDTO) userObject;
				return group;
			}
		}
		return null;
	}

	private WhiteBoardDTO getWhiteBoardOfTreeItem(TreeItem item) {
		if (item.getWidget() instanceof TreeItemViewImpl) {
			TreeItemViewImpl navigationTreeItem = (TreeItemViewImpl) item
					.getWidget();
			Object userObject = navigationTreeItem.asTreeItem().getUserObject();
			if (userObject instanceof WhiteBoardDTO) {
				WhiteBoardDTO whiteboard = (WhiteBoardDTO) userObject;
				return whiteboard;
			}
		}
		return null;
	}

	// TODO Refactoring
	private void mergeTreeItems(TreeItem oldItem, TreeItem newItem) {
		int i = 0;
		int j = 0;
		while (i < newItem.getChildCount()) {
			TreeItem newChildItem = newItem.getChild(i);
			TreeItem oldChildItem = oldItem.getChild(j);
			if (oldChildItem != null) {
				GroupDTO newGroup = getGroupOfTreeItem(newChildItem);
				GroupDTO oldGroup = getGroupOfTreeItem(oldChildItem);
				if (oldGroup != null
						&& newGroup.getKey().equals(oldGroup.getKey())) {
					((TreeItemViewImpl) oldChildItem.getWidget())
							.setText(newGroup.getTitle());

					mergeTreeItems(oldChildItem, newChildItem);
					i += 1;
				} else {
					WhiteBoardDTO newWhiteboard = getWhiteBoardOfTreeItem(newChildItem);
					WhiteBoardDTO oldWhiteboard = getWhiteBoardOfTreeItem(oldChildItem);
					if (newWhiteboard != null
							&& oldWhiteboard != null
							&& newWhiteboard.getKey().equals(
									oldWhiteboard.getKey())) {
						((TreeItemViewImpl) oldChildItem.getWidget())
								.setText(newWhiteboard.getTitle());

						oldChildItem.setUserObject(newWhiteboard);
						i += 1;
					} else {
						oldItem.insertItem(j, newChildItem);
					}
				}
			} else {
				addTreeItemAndSetStyle(oldItem, newChildItem);
			}
			j += 1;
		}
		while (j > i) {
			TreeItem oldChildItem = oldItem.getChild(j);
			oldItem.removeItem(oldChildItem);
			i += 1;
		}
	}

	public void addTreeItemAndSetStyle(TreeItem parent, TreeItem child) {
		addTreeItemToParent(parent, child);
		// TODO bugfix to set full width in tree item parent
		Element div = parent.getElement();
		Element table = DOM.getFirstChild(div);
		Element tbody = DOM.getFirstChild(table);
		Element tr = DOM.getFirstChild(tbody);
		Element td = DOM.getChild(tr, 1);
		td.getStyle().setWidth(100, Unit.PCT);
	}

	public void addTreeItemToParent(TreeItem parent, TreeItem child) {
		if (child.getUserObject() instanceof GroupDTO) {
			int index = getIndexOfLastGroupItem(parent);
			parent.insertItem(index, child);
		} else {
			parent.addItem(child);
		}
	}

	private int getIndexOfLastGroupItem(TreeItem item) {
		for (int i = 0; i < item.getChildCount(); i++) {
			if (!(item.getChild(i).getUserObject() instanceof GroupDTO)) {
				return i;
			}
		}
		return item.getChildCount();
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

	public HasSelectionHandlers<TreeItem> getNavigationTree() {
		return navigationTree;
	}

	public void selectTreeItem(TreeItem item) {
		navigationTree.setSelectedItem(item, true);
	}

	public DragController getDragController() {
		return dragController;
	}
}
