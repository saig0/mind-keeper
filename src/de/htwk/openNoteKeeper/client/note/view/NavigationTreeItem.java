package de.htwk.openNoteKeeper.client.note.view;

import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;

import de.htwk.openNoteKeeper.client.util.IconPool;

public class NavigationTreeItem extends FocusPanel {

	private TreeItem treeItem;
	private Widget dragWidget;

	private FocusPanel p;

	public NavigationTreeItem(Image icon, final String title, Object userObject) {
		sinkEvents(Event.ONCONTEXTMENU);

		treeItem = createLayout(icon, title, userObject);
	}

	private TreeItem createLayout(Image icon, final String title,
			Object userObject) {
		final HorizontalPanel panel = new HorizontalPanel();
		panel.setSize("100%", "100%");
		icon.setSize("24px", "24px");
		panel.add(icon);
		panel.setCellWidth(icon, "30px");
		panel.add(new Label(title));

		final Image dragIcon = IconPool.Up_And_Down.createImage();
		dragIcon.setSize("24px", "24px");
		dragIcon.setVisible(false);
		panel.add(dragIcon);
		panel.setCellHorizontalAlignment(dragIcon,
				HasHorizontalAlignment.ALIGN_RIGHT);

		dragWidget = dragIcon;

		final TreeItem treeItem = new TreeItem();
		treeItem.setUserObject(userObject);

		p = new FocusPanel(panel);
		p.setSize("100%", "100%");

		p.addMouseOverHandler(new MouseOverHandler() {

			public void onMouseOver(MouseOverEvent event) {
				panel.addStyleName("markedNavigationItem");

				dragIcon.setVisible(true);
			}
		});

		p.addMouseOutHandler(new MouseOutHandler() {

			public void onMouseOut(MouseOutEvent event) {
				panel.removeStyleName("markedNavigationItem");

				dragIcon.setVisible(false);
			}
		});

		add(p);

		return treeItem;
	}

	public Widget getDragHandle() {
		return dragWidget;
	}

	public TreeItem asTreeItem() {
		if (treeItem.getWidget() == null) {
			treeItem.setWidget(this);
		}
		return treeItem;
	}

	public void showContextMenu(int x, int y) {
		PopupPanel popup = new PopupPanel(true, false);
		popup.setWidget(new Label("context menu coming soon..."));
		popup.setPopupPosition(x, y);
		popup.show();
	}

	@Override
	public void onBrowserEvent(Event event) {
		event.stopPropagation();
		event.preventDefault();
		switch (DOM.eventGetType(event)) {
		case Event.ONCONTEXTMENU:
			showContextMenu(event.getClientX(), event.getClientY());
			break;
		default:
			super.onBrowserEvent(event);
			break;
		}
	}
}
