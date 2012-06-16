package de.htwk.openNoteKeeper.client.note.view.navigation;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;

import de.htwk.openNoteKeeper.client.note.presenter.navigation.TreeItemPresenter.NavigationTreeItemView;
import de.htwk.openNoteKeeper.client.util.IconPool;

public class TreeItemViewImpl extends FocusPanel implements
		NavigationTreeItemView {

	private TreeItem treeItem;
	private Widget dragWidget;
	private TreeItemContextMenu contextMenu;
	private Label textLabel;
	private Image icon;

	private FocusPanel p;

	public TreeItemViewImpl() {
		treeItem = createLayout();
	}

	private TreeItem createLayout() {
		final HorizontalPanel panel = new HorizontalPanel();
		panel.setSize("100%", "100%");
		icon = new Image();
		icon.setSize("24px", "24px");
		panel.add(icon);
		panel.setCellWidth(icon, "30px");
		textLabel = new Label();
		panel.add(textLabel);

		final Image settingsIcon = IconPool.Settings.createImage();
		settingsIcon.setSize("24px", "24px");
		settingsIcon.setVisible(false);
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		panel.add(settingsIcon);
		panel.setCellWidth(settingsIcon, "30px");

		contextMenu = new TreeItemContextMenu();
		settingsIcon.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				contextMenu.show(event.getClientX(), event.getClientY());
			}
		});

		final Image dragIcon = IconPool.Up_And_Down.createImage();
		dragIcon.setSize("24px", "24px");
		dragIcon.setVisible(false);
		panel.add(dragIcon);
		panel.setCellWidth(dragIcon, "30px");

		dragWidget = dragIcon;

		final TreeItem treeItem = new TreeItem();

		p = new FocusPanel(panel);
		p.setSize("100%", "100%");

		p.addMouseOverHandler(new MouseOverHandler() {

			public void onMouseOver(MouseOverEvent event) {
				panel.addStyleName("markedNavigationItem");

				settingsIcon.setVisible(true);
				dragIcon.setVisible(true);
			}
		});

		p.addMouseOutHandler(new MouseOutHandler() {

			public void onMouseOut(MouseOutEvent event) {
				panel.removeStyleName("markedNavigationItem");

				settingsIcon.setVisible(false);
				dragIcon.setVisible(false);
			}
		});

		p.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				treeItem.setState(true);
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

	public void hideContextMenu() {
		contextMenu.hide();
	}

	public void setIcon(String url) {
		icon.setUrl(url);
	}

	public void setText(String text) {
		textLabel.setText(text);
	}

	public void setUserObject(Object userObject) {
		treeItem.setUserObject(userObject);
	}

	public HasClickHandlers getEditButton() {
		return contextMenu.getEditButton();
	}
}
