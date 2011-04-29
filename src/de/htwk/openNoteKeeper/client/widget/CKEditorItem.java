package de.htwk.openNoteKeeper.client.widget;

import com.smartgwt.client.widgets.form.fields.CanvasItem;

public class CKEditorItem extends CanvasItem {
	private CKEditor ckeCanvas;

	public CKEditorItem(String name) {
		super(name);
		ckeCanvas = new CKEditor(name);
		this.setCanvas(ckeCanvas);
	}

	@Override
	public void setWidth(String width) {
		super.setWidth(width);
		ckeCanvas.setWidth(width);
	}

	@Override
	public void setWidth(int width) {
		super.setWidth(width);
		ckeCanvas.setWidth(width);
	}

	@Override
	public void setHeight(String height) {
		super.setHeight(height);
		ckeCanvas.setHeight(height);
	}

	@Override
	public void setHeight(int height) {
		super.setHeight(height);
		ckeCanvas.setHeight(height);
	}

	@Override
	public Object getValue() {
		return getCKEditorValue(ckeCanvas.getID() + "_ta");
	}

	private native String getCKEditorValue(String ckEditorId)/*-{
		if ($wnd.CKEDITOR.instances[ckEditorId]) {
			return $wnd.CKEDITOR.instances[ckEditorId].getData();
		}
		return null;
	}-*/;

	@Override
	public void setValue(String value) {
		super.setValue(value);
		if (ckeCanvas.isLoaded())
			setCKEditorValue(ckeCanvas.getID() + "_ta", value);
		else
			ckeCanvas.setContents(value);
	}

	private native void setCKEditorValue(String ckEditorId, String value)/*-{
		if ($wnd.CKEDITOR.instances[ckEditorId]) {
			$wnd.CKEDITOR.instances[ckEditorId].setData(value);
		}
	}-*/;
}
