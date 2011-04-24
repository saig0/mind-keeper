package de.htwk.openNoteKeeper.client.note.view;

import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.types.DragAppearance;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HeaderControl;
import com.smartgwt.client.widgets.RichTextEditor;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.DragResizeStopEvent;
import com.smartgwt.client.widgets.events.DragResizeStopHandler;
import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.events.HasDragRepositionStopHandlers;
import com.smartgwt.client.widgets.events.HasDragResizeStopHandlers;
import com.smartgwt.client.widgets.events.HasMouseDownHandlers;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;

import de.htwk.openNoteKeeper.client.note.presenter.NoteWidgetPresenter.NoteWidgetView;
import de.htwk.openNoteKeeper.shared.Coordinate;
import de.htwk.openNoteKeeper.shared.NoteDTO;

public class NoteWidget implements NoteWidgetView {

	private Window widget = new Window();

	private NoteDTO note;

	private Layout contentLayout = new VLayout();
	private Canvas content = new Canvas();

	private Layout editorLayout = new VLayout();
	private RichTextEditor editor = new RichTextEditor();

	private int width = 200;
	private int height = 200;

	private HeaderControl saveHeaderControl = new HeaderControl(
			HeaderControl.SAVE);

	private HeaderControl cancelHeaderControl = new HeaderControl(
			HeaderControl.CLOSE);

	private HeaderControl editHeaderControl = new HeaderControl(
			HeaderControl.SETTINGS);

	private HeaderControls minimizeHeaderControl = HeaderControls.MINIMIZE_BUTTON;

	public NoteWidget() {
		super();

		widget.setWidth(width);
		widget.setHeight(height);
		widget.setAnimateMinimize(true);
		widget.setIsModal(false);

		setDrapSettings();

		widget.addItem(createContentLayout());
		widget.addItem(createEditorLayout());

		setHeader();
	}

	private void setDrapSettings() {
		widget.setCanDrag(true);
		widget.setCanDrop(true);
		widget.setCanDragResize(true);
		widget.setDragType("note");
		widget.setKeepInParentRect(true);
		widget.setDragAppearance(DragAppearance.TARGET);
		widget.setShowDragShadow(true);

		widget.addDragResizeStopHandler(new DragResizeStopHandler() {

			public void onDragResizeStop(DragResizeStopEvent event) {
				width = widget.getWidth();
				height = widget.getHeight();
			}
		});
	}

	public void switchToEditor() {
		hideContent();
		setEditorHeader();
		showEditor();
	}

	private void setHeader() {
		saveHeaderControl.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				note.setContent(editor.getValue());
				switchToContent();
			}
		});
		cancelHeaderControl.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				switchToContent();
			}
		});
		editHeaderControl.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				switchToEditor();
			}
		});

		widget.setHeaderControls(HeaderControls.HEADER_LABEL,
				saveHeaderControl, cancelHeaderControl, editHeaderControl,
				minimizeHeaderControl);
	}

	private void setEditorHeader() {

		editHeaderControl.disable();
		saveHeaderControl.enable();
		cancelHeaderControl.enable();
	}

	public void switchToContent() {
		hideEditor();
		setContentHeader();
		showContent();
	}

	private void setContentHeader() {
		editHeaderControl.enable();
		saveHeaderControl.disable();
		cancelHeaderControl.disable();
	}

	private Layout createContentLayout() {
		contentLayout.setLayoutMargin(10);
		content.setContents("");
		contentLayout.addMember(content);
		return contentLayout;
	}

	private Layout createEditorLayout() {
		editor.setShowEdges(true);
		editorLayout.addMember(editor);
		return editorLayout;
	}

	private void showContent() {
		content.setContents(note.getContent());
		contentLayout.setVisible(true);
	}

	private void hideContent() {
		contentLayout.setVisible(false);
	}

	private void showEditor() {
		width = widget.getOffsetWidth();
		height = widget.getOffsetHeight();

		if (width < 550)
			widget.setWidth(550);
		if (height < 250)
			widget.setHeight(250);

		editor.setContents(note.getContent());
		editorLayout.setVisible(true);
	}

	private void hideEditor() {
		editorLayout.setVisible(false);

		widget.setWidth(width);
		widget.setHeight(height);
	}

	public void setPosition(Coordinate coordinate) {
		widget.setLeft(coordinate.getX() - widget.getOffsetWidth() / 2);
		widget.setTop(coordinate.getY() - widget.getOffsetHeight() / 2);
	}

	public HasClickHandlers getSaveNoteButton() {
		return saveHeaderControl;
	}

	public NoteDTO getNote() {
		return note;
	}

	public HasMouseDownHandlers getWidget() {
		return widget;
	}

	public void setNote(NoteDTO note) {
		this.note = note;
		widget.setTitle(note.getTitle());
	}

	public Widget asWidget() {
		return widget;
	}

	public Coordinate getPosition() {
		return new Coordinate(widget.getAbsoluteLeft(), widget.getAbsoluteTop());
	}

	public Coordinate getSize() {
		return new Coordinate(width, height);
	}

	public HasDragRepositionStopHandlers getDragWidget() {
		return widget;
	}

	public HasDragResizeStopHandlers getResizeWidget() {
		return widget;
	}

	public void setSize(Coordinate size) {
		widget.setWidth(size.getX());
		widget.setHeight(size.getY());
	}

}
