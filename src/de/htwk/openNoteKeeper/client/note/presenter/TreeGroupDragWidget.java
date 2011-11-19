package de.htwk.openNoteKeeper.client.note.presenter;

import com.google.gwt.user.client.ui.Image;

import de.htwk.openNoteKeeper.client.util.IconPool;
import de.htwk.openNoteKeeper.shared.GroupDTO;

public class TreeGroupDragWidget extends Image implements DragableWidget {

	private final Image icon = IconPool.Folder.createImage();

	private final GroupDTO group;

	public TreeGroupDragWidget(GroupDTO group) {
		super(IconPool.Folder.getUrl());
		this.group = group;
	}

	public Image getDragIcon() {
		return icon;
	}

	public GroupDTO getGroup() {
		return group;
	}

	@Override
	public TreeGroupDragWidget clone() {
		return new TreeGroupDragWidget(group);
	}
}
