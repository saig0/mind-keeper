package de.htwk.openNoteKeeper.client.note.presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Image;

import de.htwk.openNoteKeeper.client.note.i18n.NoteConstants;
import de.htwk.openNoteKeeper.client.util.IconPool;

public class GroupDragWidget extends Image implements DragableWidget {

	private final Image icon = IconPool.Folder.createImage();

	private NoteConstants constants = GWT.create(NoteConstants.class);

	public GroupDragWidget() {
		super(IconPool.Folder_Big.getUrl());
		setTitle(constants.newGroup());
	}

	public Image getDragIcon() {
		return icon;
	}

}
