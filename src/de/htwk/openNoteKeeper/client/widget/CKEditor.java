package de.htwk.openNoteKeeper.client.widget;

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;

public class CKEditor extends Canvas {
	{
		setOverflow(Overflow.VISIBLE);
		setCanDragResize(false);
		setRedrawOnResize(false);
		setZIndex(0);
	}
	private boolean loaded = false;

	public CKEditor(String id) {
		super(id);
		addDrawHandler(new DrawHandler() {

			public void onDraw(DrawEvent event) {
				loadCKEditor();
			}
		});
	}

	@Override
	public String getInnerHTML() {
		if (this.getContents() != null) {
			return "<textarea style='' id=" + this.getID() + "_ta=''>"
					+ getContents() + "</textarea>";
		}
		return "<textarea style='' id=" + this.getID() + "_ta=''></textarea>";
	}

	// @Override
	// public void draw() {
	// super.draw();
	// loadCKEditor();
	// }

	protected native void loadCKEditor()/*-{
		if (!this.@de.htwk.openNoteKeeper.client.widget.CKEditor::loaded) {
			var ckEditorId = this.@de.htwk.openNoteKeeper.client.widget.CKEditor::getID()()
					+ "_ta";
			$wnd.CKEDITOR.replace(ckEditorId);
			this.@de.htwk.openNoteKeeper.client.widget.CKEditor::loaded = true;
		}
	}-*/;

	public boolean isLoaded() {
		return loaded;
	}
}
