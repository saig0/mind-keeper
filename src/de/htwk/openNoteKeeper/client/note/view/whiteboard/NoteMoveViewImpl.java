package de.htwk.openNoteKeeper.client.note.view.whiteboard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.htwk.openNoteKeeper.client.i18n.CommonConstants;
import de.htwk.openNoteKeeper.client.note.i18n.NoteConstants;
import de.htwk.openNoteKeeper.client.note.presenter.whiteboard.NoteMovePresenter.NoteMoveView;
import de.htwk.openNoteKeeper.client.util.IconPool;
import de.htwk.openNoteKeeper.shared.GroupDTO;
import de.htwk.openNoteKeeper.shared.NoteDTO;
import de.htwk.openNoteKeeper.shared.WhiteBoardDTO;

public class NoteMoveViewImpl implements NoteMoveView {

	private NoteConstants noteConstants = GWT.create(NoteConstants.class);
	private CommonConstants commonConstants = GWT.create(CommonConstants.class);

	private final DialogBox popup;

	private Tree tree;
	private Button saveButton;
	private Button abortButton;

	private Map<String, TreeItem> indexedNotes = new HashMap<String, TreeItem>();

	public NoteMoveViewImpl() {
		popup = createWidget();
	}

	private DialogBox createWidget() {
		DialogBox popup = new DialogBox(false, true);
		popup.setAnimationEnabled(true);
		popup.setGlassEnabled(true);

		popup.setText(noteConstants.moveNote());
		popup.setSize("300px", "100px");

		VerticalPanel layout = new VerticalPanel();
		layout.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		layout.setSize("100%", "100%");

		tree = new Tree();
		layout.add(tree);
		layout.setCellHorizontalAlignment(tree,
				HasHorizontalAlignment.ALIGN_LEFT);
		layout.setCellHeight(tree, "150px");

		HorizontalPanel control = new HorizontalPanel();
		control.setSpacing(5);
		control.setSize("100%", "100%");
		abortButton = new Button(commonConstants.abort());
		abortButton.setWidth("100%");
		control.add(abortButton);
		saveButton = new Button(commonConstants.save());
		saveButton.setWidth("100%");
		control.add(saveButton);
		control.setCellWidth(saveButton, "65%");

		layout.add(control);
		popup.setWidget(layout);
		return popup;
	}

	public Widget asWidget() {
		return popup;
	}

	public void setGroups(List<GroupDTO> groups) {
		tree.clear();
		for (GroupDTO group : groups) {
			TreeItem groupItem = createTreeItem(group);
			tree.addItem(groupItem);
		}
	}

	private TreeItem createTreeItem(GroupDTO group) {
		TreeItem parent = createGroupTreeItem(group);
		for (GroupDTO childGroup : group.getSubGroups()) {
			final TreeItem groupItem = createTreeItem(childGroup);
			parent.addItem(groupItem);
		}
		for (WhiteBoardDTO whiteBoard : group.getWhiteBoards()) {
			TreeItem whiteBoardItem = createWhiteBoardTreeItem(whiteBoard);
			parent.addItem(whiteBoardItem);

			for (NoteDTO note : whiteBoard.getNotes()) {
				indexedNotes.put(note.getKey(), whiteBoardItem);
			}
		}
		return parent;
	}

	private TreeItem createGroupTreeItem(GroupDTO group) {
		HorizontalPanel panel = new HorizontalPanel();

		Image icon = IconPool.Folder.createImage();
		icon.setSize("24px", "24px");
		panel.add(icon);

		panel.add(new Label(group.getTitle()));

		TreeItem item = new TreeItem(panel);
		item.setUserObject(group);
		return item;
	}

	private TreeItem createWhiteBoardTreeItem(WhiteBoardDTO whiteBoard) {
		final FocusPanel panel = new FocusPanel();
		HorizontalPanel layout = new HorizontalPanel();
		panel.setWidget(layout);

		Image icon = IconPool.Blank_Sheet.createImage();
		icon.setSize("24px", "24px");
		layout.add(icon);

		layout.add(new Label(whiteBoard.getTitle()));

		panel.addMouseOverHandler(new MouseOverHandler() {

			public void onMouseOver(MouseOverEvent event) {
				panel.addStyleName("markedNavigationItem");
			}
		});

		panel.addMouseOutHandler(new MouseOutHandler() {

			public void onMouseOut(MouseOutEvent event) {
				panel.removeStyleName("markedNavigationItem");
			}
		});

		TreeItem item = new TreeItem(panel);
		item.setUserObject(whiteBoard);
		return item;
	}

	public void show() {
		popup.center();
	}

	public void hide() {
		popup.hide();
	}

	public HasClickHandlers getAbortButton() {
		return abortButton;
	}

	public HasClickHandlers getSaveButton() {
		return saveButton;
	}

	public void expandTree(NoteDTO note) {
		if (indexedNotes.containsKey(note.getKey())) {
			TreeItem item = indexedNotes.get(note.getKey());

			while (item.getParentItem() != null) {
				item = item.getParentItem();
				item.setState(true);
			}
		}
	}

	public WhiteBoardDTO getSelectedWhiteBoard() {
		TreeItem item = tree.getSelectedItem();
		if (item != null && item.getUserObject() != null
				&& item.getUserObject() instanceof WhiteBoardDTO) {
			return (WhiteBoardDTO) item.getUserObject();
		} else {
			return null;
		}
	}
}