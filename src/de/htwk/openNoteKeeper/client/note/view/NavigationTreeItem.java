package de.htwk.openNoteKeeper.client.note.view;

import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TreeItem;

public class NavigationTreeItem {

	private final TreeItem treeItem;

	public NavigationTreeItem(Image icon, final String title, Object userObject) {
		treeItem = createLayout(icon, title, userObject);
	}

	private TreeItem createLayout(Image icon, final String title,
			Object userObject) {
		HorizontalPanel panel = new HorizontalPanel();
		panel.setSize("100%", "100%");
		icon.setSize("24px", "24px");
		panel.add(icon);
		panel.setCellWidth(icon, "30px");
		panel.add(new Label(title));
		final TreeItem groupItem = new TreeItem(panel);
		groupItem.setUserObject(userObject);

		panel.addDomHandler(new MouseOverHandler() {

			public void onMouseOver(MouseOverEvent event) {
			}
		}, MouseOverEvent.getType());

		panel.addDomHandler(new MouseOutHandler() {

			public void onMouseOut(MouseOutEvent event) {
			}
		}, MouseOutEvent.getType());

		return groupItem;
	}

	public TreeItem asWidget() {
		return treeItem;
	}
}
