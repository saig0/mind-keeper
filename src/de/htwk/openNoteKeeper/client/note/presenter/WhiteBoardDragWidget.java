package de.htwk.openNoteKeeper.client.note.presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Image;

import de.htwk.openNoteKeeper.client.note.i18n.NoteConstants;
import de.htwk.openNoteKeeper.client.util.IconPool;

public class WhiteBoardDragWidget extends Image implements DragableWidget {

	private final Image icon = IconPool.Blank_Sheet.createImage();

	private NoteConstants constants = GWT.create(NoteConstants.class);

	public WhiteBoardDragWidget() {
		super(IconPool.Blank_Sheet_Big.getUrl());
		setTitle(constants.newWhiteBoard());
	}

	public Image getDragIcon() {
		return icon;
	}
}
