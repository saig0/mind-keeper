package de.htwk.openNoteKeeper.client.note.view;

import java.util.List;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.DragEndEvent;
import com.allen_sauer.gwt.dnd.client.DragHandler;
import com.allen_sauer.gwt.dnd.client.DragStartEvent;
import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.VetoDragException;
import com.allen_sauer.gwt.dnd.client.drop.SimpleDropController;
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
import de.htwk.openNoteKeeper.client.note.presenter.DragableWidget;
import de.htwk.openNoteKeeper.client.note.presenter.GroupDragWidget;
import de.htwk.openNoteKeeper.client.note.presenter.HasTreeDropHandler;
import de.htwk.openNoteKeeper.client.note.presenter.NavigationTreePresenter.NavigationTreeView;
import de.htwk.openNoteKeeper.client.note.presenter.TreeGroupDragWidget;
import de.htwk.openNoteKeeper.client.note.presenter.TreeWhiteBoardDragWidget;
import de.htwk.openNoteKeeper.client.note.presenter.WhiteBoardDragWidget;
import de.htwk.openNoteKeeper.client.util.IconPool;
import de.htwk.openNoteKeeper.shared.GroupDTO;
import de.htwk.openNoteKeeper.shared.WhiteBoardDTO;

@Singleton
public class NavigationTreeViewImpl implements NavigationTreeView {

	private Tree navigationTree;
	private Image noteIcon;
	private Image trashIcon;

	private DragableWidget dragWidget;

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

		Widget groupIcon = new GroupDragWidget();
		controlPanel.add(groupIcon);

		Widget whiteboardIcon = new WhiteBoardDragWidget();
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

		AbsolutePanel boundaryPanel = new AbsolutePanel();
		boundaryPanel.setSize("100%", "100%");
		boundaryPanel.add(navigationPanel);
		dragController = new PickupDragController(boundaryPanel, true);
		dragController.setBehaviorMultipleSelection(false);
		dragController.setBehaviorDragProxy(false);
		dragController.setBehaviorConstrainedToBoundaryPanel(true);

		dragController.addDragHandler(new DragHandler() {

			public void onPreviewDragStart(DragStartEvent event)
					throws VetoDragException {
				Image dragWidget = (Image) event.getContext().draggable;
				dragWidget.setSize("32px", "32px");
				int i = controlPanel.getWidgetIndex(dragWidget);
				if (i != -1) {
					Widget clone = cloneDragableWidget(event.getContext())
							.asWidget();
					dragController.makeDraggable(clone);
					controlPanel.insert(clone, i);
				} else if (dragWidget instanceof TreeGroupDragWidget
						|| dragWidget instanceof TreeWhiteBoardDragWidget) {
					// TODO
				}
			}

			public void onPreviewDragEnd(DragEndEvent event)
					throws VetoDragException {
				dragWidget = cloneDragableWidget(event.getContext());
			}

			private DragableWidget cloneDragableWidget(DragContext context)
					throws VetoDragException {
				if (context.draggable instanceof DragableWidget) {
					DragableWidget draggable = (DragableWidget) context.draggable;
					return draggable.clone();
				} else
					throw new VetoDragException();
			}

			public void onDragStart(DragStartEvent event) {

			}

			public void onDragEnd(DragEndEvent event) {
				if (dragWidget != null) {
					event.getContext().draggable.removeFromParent();
				}
			}
		});

		dropController = new TreeDropController(navigationTree);
		dragController.registerDropController(dropController);
		dragController.makeDraggable(groupIcon);
		dragController.makeDraggable(whiteboardIcon);

		dragController.registerDropController(new SimpleDropController(
				trashIcon) {
			@Override
			public void onDrop(DragContext context) {
				if (dragWidget instanceof TreeGroupDragWidget) {
					System.out.println("drop group");
				} else if (dragWidget instanceof TreeWhiteBoardDragWidget) {
					System.out.println("drop whiteboard");
				} else
					System.out.println("drop unknown");
			}

			@Override
			public void onEnter(DragContext context) {
				trashIcon.setUrl(IconPool.Trash_Full_Big.getUrl());
			}

			@Override
			public void onLeave(DragContext context) {
				trashIcon.setUrl(IconPool.Trash_Big.getUrl());
			}
		});

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
			parent.addItem(groupItem);
		}
		for (WhiteBoardDTO whiteBoard : group.getWhiteBoards()) {
			TreeItem whiteBoardItem = createWhiteBoardTreeItem(whiteBoard);
			parent.addItem(whiteBoardItem);
		}
		return parent;
	}

	private TreeItem createGroupTreeItem(GroupDTO group) {
		NavigationTreeItem navigationTreeItem = new NavigationTreeItem(
				new TreeGroupDragWidget(group), group.getTitle(), group);
		dragController.makeDraggable(navigationTreeItem.asWidget());
		return navigationTreeItem.asTreeItem();
	}

	private TreeItem createWhiteBoardTreeItem(WhiteBoardDTO whiteBoard) {
		NavigationTreeItem navigationTreeItem = new NavigationTreeItem(
				new TreeWhiteBoardDragWidget(whiteBoard),
				whiteBoard.getTitle(), whiteBoard);
		dragController.makeDraggable(navigationTreeItem.asWidget());
		return navigationTreeItem.asTreeItem();
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

	public boolean hasSelectedWhiteBoard() {
		TreeItem selectedItem = navigationTree.getSelectedItem();
		return selectedItem != null
				&& selectedItem.getUserObject() instanceof WhiteBoardDTO;
	}

	public WhiteBoardDTO getSelectedWhiteBoard() {
		TreeItem selectedItem = navigationTree.getSelectedItem();
		return (WhiteBoardDTO) selectedItem.getUserObject();
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

	public DragableWidget getDragWidget() {
		return dragWidget;
	}

	public void addWhiteBoardToGroup(TreeItem treeItem, WhiteBoardDTO whiteboard) {
		treeItem.addItem(createWhiteBoardTreeItem(whiteboard));
	}

}
