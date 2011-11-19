package de.htwk.openNoteKeeper.client.note.presenter;

import com.google.gwt.user.client.ui.Image;

import de.htwk.openNoteKeeper.client.util.IconPool;
import de.htwk.openNoteKeeper.shared.WhiteBoardDTO;

public class TreeWhiteBoardDragWidget extends Image implements DragableWidget {

	private final Image icon = IconPool.Blank_Sheet.createImage();

	private final WhiteBoardDTO whiteBoard;

	public TreeWhiteBoardDragWidget(WhiteBoardDTO whiteBoard) {
		super(IconPool.Blank_Sheet.getUrl());
		this.whiteBoard = whiteBoard;
	}

	public Image getDragIcon() {
		return icon;
	}

	public WhiteBoardDTO getWhiteBoard() {
		return whiteBoard;
	}

	@Override
	public TreeWhiteBoardDragWidget clone() {
		return new TreeWhiteBoardDragWidget(whiteBoard);
	}
}
