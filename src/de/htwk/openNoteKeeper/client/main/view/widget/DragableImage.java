package de.htwk.openNoteKeeper.client.main.view.widget;

import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.types.DragAppearance;
import com.smartgwt.client.util.EventHandler;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Img;

public class DragableImage extends Img {

	public DragableImage(String toolTipp, String url, int width, int height) {
		super(url, width, height);
		setTooltip(toolTipp);
		setCursor(Cursor.MOVE);
		setCanDrag(true);
		setCanDrop(true);
		setDragAppearance(DragAppearance.TRACKER);
		setDragType("new note");
	}

	@Override
	protected boolean setDragTracker() {
		EventHandler.setDragTracker(Canvas.imgHTML(getSrc(), 24, 24), 24, 24,
				15, 15);
		return false;
	}
}
