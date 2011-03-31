package de.htwk.openNoteKeeper.client.main.view.widget;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DragAppearance;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HeaderControl;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.RichTextEditor;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.MouseDownEvent;
import com.smartgwt.client.widgets.events.MouseDownHandler;
import com.smartgwt.client.widgets.events.MouseUpEvent;
import com.smartgwt.client.widgets.events.MouseUpHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;

import de.htwk.openNoteKeeper.client.main.view.NoteViewImpl;

public class NoteWidget extends Window {

	private NoteViewImpl noteView;

	private Layout contentLayout = new VLayout();
	private Canvas content = new Canvas();

	private Layout editorLayout = new VLayout();
	private RichTextEditor editor = new RichTextEditor();

	private String text = "";

	private int width = 200;
	private int height = 200;

	public NoteWidget(String title) {
		super();

		setWidth(width);
		setHeight(height);
		setTitle(title);
		setIsModal(false);
		setAnimateMinimize(true);

		setDrapSettings();
		setHeader();

		addItem(createContentLayout());
		addItem(createEditorLayout());

		hideContent();
		showEditor();
	}

	private void setDrapSettings() {
		setCanDrag(true);
		setCanDrop(true);
		setCanDragResize(true);
		setDragType("note");
		setKeepInParentRect(true);
		setDragAppearance(DragAppearance.TARGET);
		setShowDragShadow(true);
		addDragHander();
	}

	private void addDragHander() {
		addMouseDownHandler(new MouseDownHandler() {

			public void onMouseDown(MouseDownEvent event) {
				if (noteView != null)
					registerAsDroppedNote();
			}
		});

		addMouseUpHandler(new MouseUpHandler() {

			public void onMouseUp(MouseUpEvent event) {
				unregisterAsDroppedNote();
			}
		});
	}

	private void setHeader() {
		HeaderControl editHeaderControl = new HeaderControl(
				HeaderControl.SETTINGS);
		editHeaderControl.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				hideContent();
				showEditor();
			}
		});
		setHeaderControls(editHeaderControl);

		setShowCloseButton(false);
		setShowMinimizeButton(true);
	}

	private Layout createContentLayout() {
		contentLayout.setMembersMargin(5);
		contentLayout.setLayoutMargin(10);
		contentLayout.setLayoutAlign(Alignment.CENTER);

		content.setContents("");
		contentLayout.addMember(content);

		return contentLayout;
	}

	private Layout createEditorLayout() {
		editorLayout.setMembersMargin(5);
		editorLayout.setLayoutMargin(10);
		editorLayout.setLayoutAlign(Alignment.CENTER);

		editor.setShowEdges(true);
		editorLayout.addMember(editor);

		Layout controlLayout = new HLayout();
		controlLayout.setMembersMargin(5);
		controlLayout.setLayoutAlign(Alignment.CENTER);
		controlLayout.setAutoHeight();

		IButton saveButton = new IButton("Speichern");
		saveButton.setWidth("50%");
		saveButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				text = editor.getValue();
				hideEditor();
				showContent();
			}
		});
		controlLayout.addMember(saveButton);

		IButton cancelButton = new IButton("Abbrechen");
		cancelButton.setWidth("50%");
		cancelButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				hideEditor();
				showContent();
			}
		});
		controlLayout.addMember(cancelButton);
		editorLayout.addMember(controlLayout);

		return editorLayout;
	}

	private void showContent() {
		content.setContents(text);
		contentLayout.setVisible(true);
	}

	private void hideContent() {
		contentLayout.setVisible(false);
	}

	private void showEditor() {
		width = getOffsetWidth();
		height = getOffsetHeight();

		if (width < 550)
			setWidth(550);
		if (height < 250)
			setHeight(250);

		editor.setContents(text);
		editorLayout.setVisible(true);
	}

	private void hideEditor() {
		editorLayout.setVisible(false);

		setWidth(width);
		setHeight(height);
	}

	public void setPosition(int left, int top) {
		setLeft(left - getOffsetWidth() / 2);
		setTop(top - getOffsetHeight() / 2);
	}

	private void registerAsDroppedNote() {
		noteView.setActiveNote(this);
	}

	private void unregisterAsDroppedNote() {
		noteView.setActiveNote(null);
	}

	public void setNoteView(NoteViewImpl noteView) {
		this.noteView = noteView;
	}

}
