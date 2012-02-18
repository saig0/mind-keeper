package de.htwk.openNoteKeeper.client.note.presenter;

import java.util.Map;

import com.google.gwt.dev.util.collect.HashMap;
import com.google.gwt.user.client.ui.InsertPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;

public class DragAndDropTree extends Tree implements InsertPanel {

	private Map<Integer, Widget> indexedWidgets = new HashMap<Integer, Widget>();

	@Override
	public void add(Widget w) {
		super.add(w);

		indexedWidgets.put(getWidgetIndex(w), w);
	}

	public void insert(Widget w, int beforeIndex) {
		TreeItem item = getTreeItemForIndex(beforeIndex);
		if (item != null) {
			int childIndex = item.getParentItem().getChildIndex(item);
			item.getParentItem().insertItem(childIndex, w);
		} else {

			// TODO insert as last tree item
			item = getTreeItemForIndex(getWidgetCount() - 2);
			int childIndex = item.getParentItem().getChildIndex(item);
			item.getParentItem().insertItem(childIndex, w);
		}

	}

	public TreeItem getTreeItemForIndex(int index) {
		return getTreeItemForIndex(-1, index);
	}

	private TreeItem getTreeItemForIndex(int prefix, int index) {
		for (int i = 0; i < getItemCount(); i++) {
			prefix += 1;
			TreeItem item = getItem(i);
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
		return getTreeItemCount(-1);
	}

	private int getTreeItemCount(int count) {
		for (int i = 0; i < getItemCount(); i++) {
			count += 1;
			TreeItem item = getItem(i);
			if (item.getChildCount() > 0) {
				count = getTreeItemCount(count, item);
			}
		}
		return count;
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
		int index = -1;
		for (int i = 0; i < getItemCount(); i++) {
			index += 1;
			TreeItem item = getItem(i);
			if (item.getWidget().equals(child))
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
			item.remove();
			return true;
		} else
			return false;
	}
}