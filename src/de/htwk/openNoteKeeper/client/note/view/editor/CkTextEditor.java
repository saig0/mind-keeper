package de.htwk.openNoteKeeper.client.note.view.editor;

import com.axeiya.gwtckeditor.client.CKConfig;
import com.axeiya.gwtckeditor.client.CKConfig.PRESET_TOOLBAR;
import com.axeiya.gwtckeditor.client.CKConfig.TOOLBAR_OPTIONS;
import com.axeiya.gwtckeditor.client.CKEditor;
import com.axeiya.gwtckeditor.client.Toolbar;
import com.axeiya.gwtckeditor.client.ToolbarLine;
import com.axeiya.gwtckeditor.client.event.SaveEvent;
import com.axeiya.gwtckeditor.client.event.SaveHandler;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.htwk.openNoteKeeper.client.main.presenter.Session;
import de.htwk.openNoteKeeper.client.util.IconPool;

public class CkTextEditor implements TextEditor {

	private CKEditor editor;
	private Label dummyEditor;

	private VerticalPanel parentPanel;
	private int height;
	private String color;
	private String text;

	public CkTextEditor() {
		dummyEditor = new Label();
	}

	public void show(String text) {
		this.text = text;

		final Image loadingWidget = IconPool.Loading.createImage();
		parentPanel.add(loadingWidget);
		parentPanel.setCellHorizontalAlignment(loadingWidget,
				HasHorizontalAlignment.ALIGN_CENTER);
		parentPanel.setCellVerticalAlignment(loadingWidget,
				HasVerticalAlignment.ALIGN_MIDDLE);

		new Timer() {

			@Override
			public void run() {
				if (!Session.isEditorVisable()) {
					createAndInitEditor(loadingWidget);
					this.cancel();
				}
			}
		}.scheduleRepeating(500);
	}

	private void createAndInitEditor(final Image loadingWidget) {
		Session.setEditorIsVisable(true);
		editor = createEditor(height);
		editor.setData(text);

		removeLoadingWidgetWhenEditorIsVisable(loadingWidget);
		parentPanel.add(editor);

		editor.addSaveHandler(new SaveHandler<CKEditor>() {

			public void onSave(SaveEvent<CKEditor> event) {
				NativeEvent clickEvent = Document.get().createClickEvent(0, 0,
						0, 0, 0, false, false, false, false);
				DomEvent.fireNativeEvent(clickEvent, dummyEditor);
			}
		});
	}

	private CKEditor createEditor(int height) {
		CKConfig ckConfig = new CKConfig(PRESET_TOOLBAR.BASIC);
		ckConfig.setUiColor(color);
		ckConfig.setWidth("95%");
		// ckConfig.setHeight("100%");
		ckConfig.setHeight(height + "px");

		ckConfig.setResizeEnabled(false);
		ckConfig.setFocusOnStartup(true);

		ToolbarLine line = new ToolbarLine();
		// Define the first line
		TOOLBAR_OPTIONS[] t1 = { TOOLBAR_OPTIONS.Save, TOOLBAR_OPTIONS.Undo,
				TOOLBAR_OPTIONS.Redo };
		line.addAll(t1);

		// Define the second line
		ToolbarLine line2 = new ToolbarLine();
		TOOLBAR_OPTIONS[] t2 = { TOOLBAR_OPTIONS.Bold, TOOLBAR_OPTIONS.Italic,
				TOOLBAR_OPTIONS.Underline, TOOLBAR_OPTIONS.Strike,
				TOOLBAR_OPTIONS._, TOOLBAR_OPTIONS.FontSize,
				TOOLBAR_OPTIONS.TextColor, TOOLBAR_OPTIONS.NumberedList,
				TOOLBAR_OPTIONS.BulletedList, TOOLBAR_OPTIONS._,
				TOOLBAR_OPTIONS.Outdent, TOOLBAR_OPTIONS.Indent,
				TOOLBAR_OPTIONS._, TOOLBAR_OPTIONS._, TOOLBAR_OPTIONS.Image,
				TOOLBAR_OPTIONS.Table };
		line2.addAll(t2);

		// Creates the toolbar
		Toolbar toolbar = new Toolbar();
		toolbar.add(line);
		toolbar.add(line2);
		ckConfig.setToolbar(toolbar);

		CKEditor ckEditor = new CKEditor(ckConfig);
		return ckEditor;
	}

	private void removeLoadingWidgetWhenEditorIsVisable(
			final Image loadingWidget) {
		if (isEditorVisable()) {
			parentPanel.remove(loadingWidget);
		} else {
			new Timer() {

				@Override
				public void run() {
					if (isEditorVisable()) {
						parentPanel.remove(loadingWidget);
						this.cancel();
					}
				}

			}.scheduleRepeating(100);
		}
	}

	private boolean isEditorVisable() {
		try {
			Element e = parentPanel.getElement();
			Element tbody = assertNotNull(DOM.getFirstChild(e));
			Element tr = assertNotNull(DOM.getChild(tbody,
					tbody.getChildCount() - 1));
			Element td = assertNotNull(DOM.getFirstChild(tr));
			Element form = assertNotNull(DOM.getFirstChild(td));
			Element div = assertNotNull(DOM.getFirstChild(form));
			Element span = assertNotNull(DOM.getChild(div, 1));
			Element span2 = assertNotNull(DOM.getChild(span, 1));
			Element span3 = assertNotNull(DOM.getFirstChild(span2));
			Element table = assertNotNull(DOM.getFirstChild(span3));
			String cssClass = table.getClassName();
			if ("cke_editor".equals(cssClass)) {
				return true;
			} else {
				return false;
			}
		} catch (NullPointerException ex) {
			return false;
		}
	}

	private Element assertNotNull(Element e) throws NullPointerException {
		if (e == null)
			throw new NullPointerException();
		else
			return e;
	}

	public void hide() {
		if (editor != null) {
			editor.setDisabled(true);
			parentPanel.remove(editor);
			editor = null;

			new Timer() {

				@Override
				public void run() {
					if (!isEditorVisable()) {
						Session.setEditorIsVisable(false);
						this.cancel();
					}
				}

			}.scheduleRepeating(500);
		}
	}

	public HasClickHandlers getSaveButton() {
		return dummyEditor;
	}

	public void setParentPanel(VerticalPanel panel) {
		this.parentPanel = panel;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getContentOfEditor() {
		if (editor != null)
			return editor.getHTML();
		else
			return null;
	}
}
