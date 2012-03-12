package de.htwk.openNoteKeeper.client.widget.resize;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ResizeableWidget extends VerticalPanel implements
		HasResizeListener {

	private static final int RESIZE_AREA = 20;
	private boolean bDragDrop = false;
	private boolean move = false;
	private Element movingPanelElement;
	private List<ResizeListener> panelResizedListeners = new ArrayList<ResizeListener>();

	public ResizeableWidget(Widget widget) {
		super();
		widget.addStyleName("resizePanel");

		// listen to mouse-events
		DOM.sinkEvents(this.getElement(), Event.ONMOUSEDOWN | Event.ONMOUSEMOVE
				| Event.ONMOUSEUP | Event.ONMOUSEOVER);

		add(widget);
		setCellWidth(widget, "100%");
		setCellHeight(widget, "100%");
	}

	/**
	 * processes the mouse-events to show cursor or change states - mouseover -
	 * mousedown - mouseup - mousemove
	 */
	@Override
	public void onBrowserEvent(Event event) {
		final int eventType = DOM.eventGetType(event);
		if (Event.ONMOUSEOVER == eventType) {
			// show different cursors
			if (isCursorResize(event)) {
				DOM.setStyleAttribute(this.getElement(), "cursor", "se-resize");
			} else if (isCursorMove(event)) {
				// DOM.setStyleAttribute(this.getElement(), "cursor",
				// "se-resize");
				DOM.setStyleAttribute(this.getElement(), "cursor", "move");
			} else {
				DOM.setStyleAttribute(this.getElement(), "cursor", "default");
			}
		}
		if (Event.ONMOUSEDOWN == eventType) {
			if (isCursorResize(event)) {
				event.preventDefault();
				// enable/disable resize
				if (bDragDrop == false) {
					bDragDrop = true;

					DOM.setCapture(this.getElement());
				}
			} else if (isCursorMove(event)) {
				DOM.setCapture(this.getElement());
				move = true;
			}
		} else if (Event.ONMOUSEMOVE == eventType) {
			// reset cursor-type
			if (!isCursorResize(event) && !isCursorMove(event)) {
				DOM.setStyleAttribute(this.getElement(), "cursor", "default");
			}

			// calculate and set the new size
			if (bDragDrop == true) {
				event.preventDefault();

				int absX = DOM.eventGetClientX(event);
				int absY = DOM.eventGetClientY(event);
				int originalX = DOM.getAbsoluteLeft(this.getElement());
				int originalY = DOM.getAbsoluteTop(this.getElement());

				// do not allow mirror-functionality
				if (absY > originalY && absX > originalX) {
					Integer height = absY - originalY + 2;
					this.setHeight(height + "px");

					Integer width = absX - originalX + 2;
					this.setWidth(width + "px");
					notifyPanelResizedListeners(width, height);
				}
			} else if (move == true) {
				RootPanel.get().setWidgetPosition(this,
						DOM.eventGetClientX(event), DOM.eventGetClientY(event));
			}
		} else if (Event.ONMOUSEUP == eventType) {
			// reset states
			if (move == true) {
				move = false;
				DOM.releaseCapture(this.getElement());
			}
			if (bDragDrop == true) {
				bDragDrop = false;
				DOM.releaseCapture(this.getElement());

				notifyListenerAfterResizeFinished(event);
			}
		}
	}

	private void notifyListenerAfterResizeFinished(Event event) {
		int absX = DOM.eventGetClientX(event);
		int absY = DOM.eventGetClientY(event);
		int originalX = DOM.getAbsoluteLeft(this.getElement());
		int originalY = DOM.getAbsoluteTop(this.getElement());

		// do not allow mirror-functionality
		if (absY > originalY && absX > originalX) {
			Integer height = absY - originalY + 2;
			this.setHeight(height + "px");

			Integer width = absX - originalX + 2;
			this.setWidth(width + "px");

			for (ResizeListener listener : panelResizedListeners) {
				listener.onReleasedResized(width, height);
			}
		}
	}

	/**
	 * returns if mousepointer is in region to show cursor-resize
	 * 
	 * @param event
	 * @return true if in region
	 */
	protected boolean isCursorResize(Event event) {
		int cursorY = DOM.eventGetClientY(event);
		int initialY = this.getAbsoluteTop();
		int height = this.getOffsetHeight();

		int cursorX = DOM.eventGetClientX(event);
		int initialX = this.getAbsoluteLeft();
		int width = this.getOffsetWidth();

		// only in bottom right corner (area of 10 pixels in square)
		if (((initialX + width - RESIZE_AREA) < cursorX && cursorX <= (initialX + width))
				&& ((initialY + height - RESIZE_AREA) < cursorY && cursorY <= (initialY + height)))
			return true;
		else
			return false;
	}

	/**
	 * sets the element in panel
	 * 
	 * @param movingPanelElement
	 */
	public void setMovingPanelElement(Element movingPanelElement) {
		this.movingPanelElement = movingPanelElement;
	}

	/**
	 * is cursor in moving state?
	 * 
	 * @param event
	 *            event to process
	 * @return true if cursor is in movement
	 */
	protected boolean isCursorMove(Event event) {
		if (movingPanelElement != null) {
			int cursorY = DOM.eventGetClientY(event);
			int initialY = movingPanelElement.getAbsoluteTop();
			int cursorX = DOM.eventGetClientX(event);
			int initialX = movingPanelElement.getAbsoluteLeft();

			if (initialY <= cursorY && initialX <= cursorX)
				return true;
			else
				return false;
		} else
			return false;
	}

	/**
	 * Interface function to add a listener to this event
	 * 
	 * @param listener
	 */
	public void addResizedListener(ResizeListener listener) {
		panelResizedListeners.add(listener);
	}

	/**
	 * Interface function to emit signal
	 */
	private void notifyPanelResizedListeners(Integer width, Integer height) {
		for (ResizeListener listener : panelResizedListeners) {
			listener.onResized(width, height);
		}
	}
}
