package de.htwk.openNoteKeeper.client.note.view.whiteboard;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.htwk.openNoteKeeper.client.util.IconPool;
import de.htwk.openNoteKeeper.client.widget.IconButton;

public class NoteContextMenu implements IsWidget {

	private final PopupPanel popup;

	private Button deleteButton;

	public NoteContextMenu() {
		this.popup = createLayout();
	}

	public Widget asWidget() {
		return popup;
	}

	private PopupPanel createLayout() {
		PopupPanel popup = new PopupPanel(true, false);
		popup.addStyleName("top");

		VerticalPanel content = new VerticalPanel();
		content.setSize("100%", "100%");

		Image deleteIcon = IconPool.Trash.createImage();
		deleteIcon.setSize("16px", "16px");
		deleteButton = new IconButton(deleteIcon, "löschen");
		content.add(deleteButton);

		popup.setWidget(content);

		return popup;
	}

	public void show(int x, int y) {
		popup.setPopupPosition(x, y);
		popup.show();
	}

	public void hide() {
		popup.hide();
	}

	public HasClickHandlers getDeleteButton() {
		return deleteButton;
	}
}