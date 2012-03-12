package de.htwk.openNoteKeeper.client.note.view.whiteboard;

import com.allen_sauer.gwt.dnd.client.DragController;
import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.drop.AbsolutePositionDropController;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import de.htwk.openNoteKeeper.client.note.presenter.whiteboard.NotePresenter.NoteView;
import de.htwk.openNoteKeeper.client.note.view.navigation.NavigationTreeViewImpl;
import de.htwk.openNoteKeeper.client.util.DragableWidget;
import de.htwk.openNoteKeeper.client.widget.panel.HideablePanel;

public class NoteViewImpl implements NoteView {

	@Inject
	private NavigationTreeViewImpl navigationTreeView;

	private AbsolutePanel whiteboardPanel = new AbsolutePanel();
	private PickupDragController dragController = new PickupDragController(
			whiteboardPanel, true);

	private Widget main;

	public NoteViewImpl() {
	}

	public Widget asWidget() {
		// lazy, weil es sonst Probleme mit der NestedView gibt
		if (main == null)
			main = createLayout();
		return main;
	}

	private Widget createLayout() {
		HorizontalPanel main = new HorizontalPanel();
		main.setSize("100%", "100%");

		whiteboardPanel.setHeight("100%");
		whiteboardPanel.addStyleName("whiteBoard");

		AbsolutePositionDropController dropController = new AbsolutePositionDropController(
				whiteboardPanel);

		dragController.setBehaviorMultipleSelection(false);
		dragController.setBehaviorDragProxy(false);
		dragController.setBehaviorConstrainedToBoundaryPanel(true);
		dragController.setConstrainWidgetToBoundaryPanel(true);

		dragController.registerDropController(dropController);

		HideablePanel hideableNavigation = new HideablePanel(
				navigationTreeView.asWidget(), whiteboardPanel, 25);
		main.add(hideableNavigation);
		main.setCellHeight(hideableNavigation, "100%");

		return main;
	}

	public void showNoteWidget(DragableWidget noteWidget, int left, int top) {
		whiteboardPanel.add(noteWidget, left, top);
		dragController.makeDraggable(noteWidget.asWidget(),
				noteWidget.getDragHandle());
	}

	public void removeNoteWidget(Widget noteWidget) {
		whiteboardPanel.remove(noteWidget);
	}

	public DragController getDragController() {
		return dragController;
	}

	public int getWhiteBoardAbsoluteLeft() {
		return whiteboardPanel.getAbsoluteLeft();
	}

	public int getWhiteBoardAbsoluteTop() {
		return whiteboardPanel.getAbsoluteTop();
	}
}
