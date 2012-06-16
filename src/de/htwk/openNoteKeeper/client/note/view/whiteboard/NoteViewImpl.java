package de.htwk.openNoteKeeper.client.note.view.whiteboard;

import com.allen_sauer.gwt.dnd.client.DragController;
import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.drop.AbsolutePositionDropController;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import de.htwk.openNoteKeeper.client.i18n.CommonConstants;
import de.htwk.openNoteKeeper.client.note.presenter.whiteboard.NotePresenter.NoteView;
import de.htwk.openNoteKeeper.client.note.view.navigation.NavigationTreeViewImpl;
import de.htwk.openNoteKeeper.client.util.DragableWidget;
import de.htwk.openNoteKeeper.client.util.IconPool;
import de.htwk.openNoteKeeper.client.widget.panel.HideablePanel;

public class NoteViewImpl implements NoteView {

	private final class SizeExtenderHandler implements MouseMoveHandler {
		public void onMouseMove(MouseMoveEvent event) {
			// FIXME Notizen können nicht auf erweiterten Bereich verschoben
			// werden
			if (isScrollEnable && false) {
				int spacing = 30;
				int sizeOfScrollPanel = 20;
				int whiteboardWidthArea = (whiteboardPanel.getAbsoluteLeft()
						+ whiteboardPanel.getOffsetWidth() - spacing);
				if (getWhiteBoardHeight() > whiteboardPanel.getOffsetHeight()) {
					whiteboardWidthArea -= sizeOfScrollPanel;
				}
				int whiteboardHeightArea = (whiteboardPanel.getAbsoluteTop()
						+ whiteboardPanel.getOffsetHeight() - spacing);
				if (getWhiteBoardWidth() > whiteboardPanel.getOffsetWidth()) {
					whiteboardHeightArea -= sizeOfScrollPanel;
				}
				if (extendWidthPanel == null
						&& event.getClientX() > whiteboardWidthArea) {
					extendWidthPanel = new VerticalPanel();
					extendWidthPanel.setSize(spacing + "px",
							getWhiteBoardHeight() - spacing + "px");
					extendWidthPanel.addStyleName("sizeExtender");
					Image extendIcon = IconPool.Arrow_Right2.createImage();
					extendIcon.setWidth("25px");
					extendIcon.addStyleName("clickable");
					extendIcon.setTitle(commonConstants.increase());
					extendIcon.addClickHandler(new ClickHandler() {

						public void onClick(ClickEvent event) {
							HTML dummyWidget = createDummyWidget();
							whiteboardPanel.add(dummyWidget,
									(getWhiteBoardWidth() + Window
											.getClientWidth()), 0);
							whiteboardPanel.remove(extendWidthPanel);
							extendWidthPanel = null;
						}

					});

					extendWidthPanel.add(extendIcon);
					extendWidthPanel.setCellVerticalAlignment(extendIcon,
							HasVerticalAlignment.ALIGN_MIDDLE);
					extendWidthPanel.setCellHorizontalAlignment(extendIcon,
							HasHorizontalAlignment.ALIGN_CENTER);
					int left = getWhiteBoardWidth() - spacing - 2;
					if (getWhiteBoardHeight() > whiteboardPanel
							.getOffsetHeight()
							&& getWhiteBoardWidth() <= whiteboardPanel
									.getOffsetWidth()) {
						left -= sizeOfScrollPanel;
					}
					whiteboardPanel.add(extendWidthPanel, left, 0);
				} else if (extendWidthPanel != null
						&& event.getClientX() <= whiteboardWidthArea) {
					whiteboardPanel.remove(extendWidthPanel);
					extendWidthPanel = null;
				}

				if (extendHeightPanel == null
						&& event.getClientY() > whiteboardHeightArea) {
					extendHeightPanel = new VerticalPanel();
					extendHeightPanel.setSize(getWhiteBoardWidth() - spacing
							+ "px", spacing + "px");
					extendHeightPanel.addStyleName("sizeExtender");
					Image extendIcon = IconPool.Arrow_Down.createImage();
					extendIcon.setWidth("25px");
					extendIcon.addStyleName("clickable");
					extendIcon.setTitle(commonConstants.increase());
					extendIcon.addClickHandler(new ClickHandler() {

						public void onClick(ClickEvent event) {
							HTML dummyWidget = createDummyWidget();
							whiteboardPanel.add(dummyWidget, 0,
									(getWhiteBoardHeight() + Window
											.getClientHeight()));
							whiteboardPanel.remove(extendHeightPanel);
							extendHeightPanel = null;
						}
					});

					extendHeightPanel.add(extendIcon);
					extendHeightPanel.setCellVerticalAlignment(extendIcon,
							HasVerticalAlignment.ALIGN_MIDDLE);
					extendHeightPanel.setCellHorizontalAlignment(extendIcon,
							HasHorizontalAlignment.ALIGN_CENTER);
					int top = getWhiteBoardHeight() - spacing - 2;
					if (getWhiteBoardWidth() > whiteboardPanel.getOffsetWidth()
							&& getWhiteBoardHeight() <= whiteboardPanel
									.getOffsetHeight()) {
						top -= sizeOfScrollPanel;
					}
					whiteboardPanel.add(extendHeightPanel, 0, top);
				} else if (extendHeightPanel != null
						&& event.getClientY() <= whiteboardHeightArea) {
					whiteboardPanel.remove(extendHeightPanel);
					extendHeightPanel = null;
				}
			}
		}

		private HTML createDummyWidget() {
			HTML dummyWidget = new HTML(".");
			dummyWidget.getElement().getStyle().setColor("white");
			return dummyWidget;
		}
	}

	@Inject
	private NavigationTreeViewImpl navigationTreeView;
	@Inject
	private CommonConstants commonConstants;

	private AbsolutePanel whiteboardPanel = new AbsolutePanel();
	private PickupDragController dragController = new PickupDragController(
			whiteboardPanel, true);

	private VerticalPanel extendWidthPanel;
	private VerticalPanel extendHeightPanel;
	private boolean isScrollEnable = false;

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
		whiteboardPanel.getElement().getStyle().setOverflow(Overflow.AUTO);
		whiteboardPanel.addStyleName("whiteBoard");

		whiteboardPanel.addDomHandler(new SizeExtenderHandler(),
				MouseMoveEvent.getType());

		AbsolutePositionDropController dropController = new AbsolutePositionDropController(
				whiteboardPanel);

		dragController.setBehaviorMultipleSelection(false);
		dragController.setBehaviorDragProxy(false);
		// dragController.setBehaviorConstrainedToBoundaryPanel(true);
		// dragController.setConstrainWidgetToBoundaryPanel(true);

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

	public void showGroupWidget(String groupName) {
		HorizontalPanel panel = new HorizontalPanel();
		panel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		panel.setSpacing(10);
		panel.add(IconPool.Folder_Big.createImage());
		Label nameLabel = new Label(groupName);
		nameLabel.addStyleName("big");
		panel.add(nameLabel);
		whiteboardPanel.add(panel, 50, 50);
	}

	public void cleanView() {
		whiteboardPanel.clear();
	}

	private int getWhiteBoardWidth() {
		int width = whiteboardPanel.getOffsetWidth();
		for (int i = 0; i < whiteboardPanel.getWidgetCount(); i++) {
			Widget widget = whiteboardPanel.getWidget(i);
			String left = widget.getElement().getStyle().getLeft();
			int widthOfWidgtet = Integer.valueOf(left.substring(0,
					left.length() - 2))
					+ widget.getOffsetWidth();
			if (widthOfWidgtet > width) {
				width = widthOfWidgtet;
			}
		}
		return width;
	}

	private int getWhiteBoardHeight() {
		int height = whiteboardPanel.getOffsetHeight();
		for (int i = 0; i < whiteboardPanel.getWidgetCount(); i++) {
			Widget widget = whiteboardPanel.getWidget(i);
			String top = widget.getElement().getStyle().getTop();
			int heightOfWidgtet = Integer.valueOf(top.substring(0,
					top.length() - 2)) + widget.getOffsetHeight();
			if (heightOfWidgtet > height) {
				height = heightOfWidgtet;
			}
		}
		return height;
	}

	public void setScrollEnable(boolean enable) {
		isScrollEnable = enable;
	}
}
