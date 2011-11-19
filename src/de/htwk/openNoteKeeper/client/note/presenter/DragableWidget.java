package de.htwk.openNoteKeeper.client.note.presenter;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;

public interface DragableWidget extends IsWidget {

	public Image getDragIcon();

	public DragableWidget clone();
}
