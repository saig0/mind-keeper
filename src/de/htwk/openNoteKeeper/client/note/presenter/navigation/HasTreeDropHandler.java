package de.htwk.openNoteKeeper.client.note.presenter.navigation;

import com.google.gwt.user.client.ui.TreeItem;

public interface HasTreeDropHandler {

	public interface TreeDropHandler {
		public void onTreeDrop(TreeItem dropTarget, TreeItem sourceItem);
	}

	public void addTreeDropHandler(TreeDropHandler treeDropHandler);
}
