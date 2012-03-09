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
	private Button editButton;

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
		content.setSpacing(5);
		content.setSize("100%", "100%");

		Image deleteIcon = IconPool.Trash.createImage();
		deleteIcon.setSize("16px", "16px");
		deleteButton = new IconButton(deleteIcon, "löschen");
		deleteButton.setWidth("100%");
		content.add(deleteButton);

		Image editIcon = IconPool.Settings_Small_2.createImage();
		editIcon.setSize("16px", "16px");
		editButton = new IconButton(editIcon, "ändern");
		editButton.setWidth("100%");
		content.add(editButton);

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

	public HasClickHandlers getEditButton() {
		return editButton;
	}
}