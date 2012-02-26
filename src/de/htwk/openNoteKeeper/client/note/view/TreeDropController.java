package de.htwk.openNoteKeeper.client.note.view;

import java.util.LinkedList;
import java.util.List;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.VetoDragException;
import com.allen_sauer.gwt.dnd.client.drop.SimpleDropController;
import com.allen_sauer.gwt.dnd.client.util.CoordinateLocation;
import com.allen_sauer.gwt.dnd.client.util.Location;
import com.allen_sauer.gwt.dnd.client.util.WidgetArea;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;

import de.htwk.openNoteKeeper.client.note.presenter.HasTreeDropHandler;
import de.htwk.openNoteKeeper.shared.GroupDTO;
import de.htwk.openNoteKeeper.shared.WhiteBoardDTO;

public class TreeDropController extends SimpleDropController implements
		HasTreeDropHandler {

	private List<TreeDropHandler> treeDropHandlers = new LinkedList<TreeDropHandler>();

	private final Tree tree;

	private TreeItem dragTreeItem;

	public TreeDropController(Tree tree) {
		super(tree);
		this.tree = tree;
	}

	@Override
	public void onDrop(DragContext context) {
		TreeItem treeItem = findDragTarget(tree.getItem(0), context);
		if (treeItem != null) {
			TreeItem sourceItem = ((NavigationTreeItem) context.draggable)
					.asTreeItem();
			sourceItem.remove();
			for (TreeDropHandler handler : treeDropHandlers) {
				handler.onTreeDrop(treeItem, sourceItem);
			}
		}
		super.onDrop(context);
	}

	private TreeItem cloneTreeItem(DragContext context) {
		Widget dragWidget = context.draggable;
		if (dragWidget instanceof NavigationTreeItem) {
			TreeItem treeItem = ((NavigationTreeItem) dragWidget).asTreeItem();
			TreeItem clone = new TreeItem(dragWidget);
			clone.setUserObject(treeItem.getUserObject());
			if (treeItem.getChildCount() > 0) {
				for (int i = 0; i < treeItem.getChildCount(); i++) {
					clone.addItem(treeItem.getChild(i));
				}
			}
			return clone;
		}
		throw new IllegalArgumentException("unknown drag widget found: "
				+ dragWidget);
	}

	@Override
	public void onMove(DragContext context) {
		removeDragWidget();

		TreeItem targetTreeItem = findDragTarget(tree.getItem(0), context);
		if (targetTreeItem != null) {
			openTreeItem(targetTreeItem);
			if (targetTreeItem.getUserObject() instanceof GroupDTO
					|| targetTreeItem.getUserObject() instanceof WhiteBoardDTO) {
				dragTreeItem = cloneTreeItem(context);
				addDragWidgetToTree(targetTreeItem);
			}
		}
		super.onMove(context);
	}

	private void openTreeItem(TreeItem targetTreeItem) {
		if (targetTreeItem.getChildCount() > 0 && !targetTreeItem.getState()) {
			targetTreeItem.setState(true);
		}
	}

	private void removeDragWidget() {
		if (dragTreeItem != null) {
			dragTreeItem.remove();
		}
	}

	private void addDragWidgetToTree(TreeItem targetTreeItem) {
		TreeItem parentItem = targetTreeItem.getParentItem();
		if (parentItem != null) {
			int index = parentItem.getChildIndex(targetTreeItem) + 1;
			parentItem.insertItem(index, dragTreeItem);
		} else {
			Tree parentTree = targetTreeItem.getTree();
			if (parentTree != null) {
				int index = foundIndexOfTreeItem(targetTreeItem, parentTree);
				parentTree.insertItem(index + 1, dragTreeItem);
			}
		}
	}

	private int foundIndexOfTreeItem(TreeItem targetTreeItem, Tree parentTree) {
		for (int index = 0; index < parentTree.getItemCount(); index++) {
			if (parentTree.getItem(index).equals(targetTreeItem))
				return index;
		}
		return 0;
	};

	private TreeItem findDragTarget(TreeItem root, DragContext context) {
		if (isLocationInWidget(root, context)) {
			return root;
		}
		for (int i = root.getChildCount() - 1; i >= 0; i--) {
			TreeItem item = findDragTarget(root.getChild(i), context);
			if (item != null)
				return item;
		}
		return null;
	}

	private boolean isLocationInWidget(TreeItem root, DragContext context) {
		if (root.getWidget() != null) {
			WidgetArea midArea = new WidgetArea(root.getWidget(), null);
			Location location = new CoordinateLocation(context.mouseX,
					context.mouseY);
			return midArea.getBottom() > location.getTop()
					&& midArea.getTop() < location.getTop()
					&& midArea.getRight() > location.getLeft()
					&& midArea.getLeft() < location.getLeft();
		} else
			return false;
	}

	@Override
	public void onLeave(DragContext context) {
		removeDragWidget();
		super.onLeave(context);
	}

	@Override
	public void onPreviewDrop(DragContext context) throws VetoDragException {
		super.onPreviewDrop(context);
		TreeItem targetTreeItem = findDragTarget(tree.getItem(0), context);
		if (targetTreeItem == null
				|| !(targetTreeItem.getUserObject() instanceof GroupDTO)) {
			throw new VetoDragException();
		} else if (targetTreeItem.getParentItem() == null
				|| !(targetTreeItem.getParentItem().getUserObject() instanceof GroupDTO)) {
			throw new VetoDragException();
		}
	}

	public void addTreeDropHandler(TreeDropHandler treeDropHandler) {
		treeDropHandlers.add(treeDropHandler);
	}

}
