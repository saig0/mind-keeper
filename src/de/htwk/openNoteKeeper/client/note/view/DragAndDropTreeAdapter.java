package de.htwk.openNoteKeeper.client.note.view;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.google.gwt.user.client.ui.InsertPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;

public class DragAndDropTreeAdapter extends Tree implements InsertPanel {

	private Map<Integer, TreeItem> indexedTreeItems = new HashMap<Integer, TreeItem>();

	private void insertTreeItem(TreeItem item, int index) {
		if (indexedTreeItems.containsKey(index)) {
			for (int i : new HashSet<Integer>(indexedTreeItems.keySet())) {
				if (i >= index) {
					TreeItem treeItem = indexedTreeItems.get(i);
					indexedTreeItems.put(i + 1, treeItem);
				}
			}
		}
		indexedTreeItems.put(index, item);
	}

	private void removeTreeItem(int index) {
		if (indexedTreeItems.containsKey(index)) {
			TreeItem item = indexedTreeItems.get(index);
			indexedTreeItems.remove(item);

			for (int i : new HashSet<Integer>(indexedTreeItems.keySet())) {
				if (i > index) {
					TreeItem treeItem = indexedTreeItems.get(i);
					indexedTreeItems.put(i - 1, treeItem);
				}
			}
		}
	}

	@Override
	public void add(Widget w) {
		super.add(w);
	}

	public void insert(Widget w, int beforeIndex) {
		TreeItem item = getTreeItemForIndex(beforeIndex);
		if (item != null) {
			int childIndex = item.getParentItem().getChildIndex(item);
			TreeItem newItem = item.getParentItem().insertItem(childIndex, w);
			insertTreeItem(newItem, beforeIndex);
		} else {
			throw new IllegalArgumentException("no tree item found for index: "
					+ beforeIndex);
		}

	}

	public TreeItem getTreeItemForIndex(int index) {
		if (indexedTreeItems.containsKey(index)) {
			return indexedTreeItems.get(index);
		} else {
			TreeItem item = getTreeItemForIndex(-1, index, getItem(0));
			insertTreeItem(item, index);
			return item;
		}
	}

	private TreeItem getTreeItemForIndex(int prefix, int index, TreeItem tree) {
		for (int i = 0; i < tree.getChildCount(); i++) {
			prefix += 1;
			TreeItem item = tree.getChild(i);
			if (prefix == index)
				return item;
			else if (item.getChildCount() > 0) {
				TreeItem child = getTreeItemForIndex(prefix, index, item);
				if (child != null)
					return child;
			}
		}
		return null;
	}

	public Widget getWidget(int index) {
		return getTreeItemForIndex(index).getWidget();
	}

	public int getWidgetCount() {
		return getTreeItemCount(-1, getItem(0));
	}

	private int getTreeItemCount(int count, TreeItem tree) {
		for (int i = 0; i < tree.getChildCount(); i++) {
			count += 1;
			TreeItem item = tree.getChild(i);
			if (item.getChildCount() > 0) {
				count += getTreeItemCount(count, item);
			}
		}
		return count;
	}

	public int getWidgetIndex(Widget child) {
		return getWidgetIndex(child, getItem(0));
	}

	private int getWidgetIndex(Widget child, TreeItem tree) {
		int index = 0;
		for (int i = 0; i < tree.getChildCount(); i++) {
			index += 1;
			TreeItem item = tree.getChild(i);
			if (item.getWidget() != null && item.getWidget().equals(child))
				return index;
			else if (item.getChildCount() > 0) {
				int widgetIndex = getWidgetIndex(child, item);
				if (widgetIndex < item.getChildCount())
					return index + widgetIndex;
				else
					index += widgetIndex;
			}
		}
		return index;
	}

	public boolean remove(int index) {
		TreeItem item = getTreeItemForIndex(index);
		if (item != null) {
			removeTreeItem(index);
			return true;
		} else
			return false;
	}
}