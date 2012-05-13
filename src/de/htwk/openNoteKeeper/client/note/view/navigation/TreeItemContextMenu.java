package de.htwk.openNoteKeeper.client.note.view.navigation;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.htwk.openNoteKeeper.client.i18n.CommonConstants;
import de.htwk.openNoteKeeper.client.util.IconPool;
import de.htwk.openNoteKeeper.client.widget.IconButton;

public class TreeItemContextMenu implements IsWidget {

	private CommonConstants commonConstants = GWT.create(CommonConstants.class);

	private final PopupPanel popup;

	private Button deleteButton;
	private Button editButton;
	private Button moveButton;

	public TreeItemContextMenu() {
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

		Image editIcon = IconPool.Settings_Small_2.createImage();
		editIcon.setSize("16px", "16px");
		editButton = new IconButton(editIcon, commonConstants.edit());
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

	public HasClickHandlers getEditButton() {
		return editButton;
	}

}