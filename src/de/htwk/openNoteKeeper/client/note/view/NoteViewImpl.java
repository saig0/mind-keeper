package de.htwk.openNoteKeeper.client.note.view;

import java.util.Random;

import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.events.DropOutEvent;
import com.smartgwt.client.widgets.events.DropOutHandler;
import com.smartgwt.client.widgets.events.DropOverEvent;
import com.smartgwt.client.widgets.events.DropOverHandler;
import com.smartgwt.client.widgets.events.HasDropHandlers;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;

import de.htwk.openNoteKeeper.client.note.presenter.NotePresenter.NoteView;
import de.htwk.openNoteKeeper.client.note.presenter.NoteWidgetPresenter.NoteWidgetView;
import de.htwk.openNoteKeeper.client.util.IconPool;

public class NoteViewImpl implements NoteView {

	private Img createButton = new DragableImage("Neue Notiz",
			IconPool.Notice_Big.getUrl(), 64, 64);
	private Img removeButton = new Img(IconPool.Trash_Big.getUrl(), 64, 64);

	private Canvas notePanel = new Canvas();
	private Random random = new Random();

	public Widget asWidget() {
		Layout main = new HLayout();
		main.setWidth100();
		main.setHeight100();
		main.setMembersMargin(10);

		Layout control = createControlPanel();
		control.addMember(removeButton);
		main.addMember(control);

		Canvas notePanel = createNotePanel();
		main.addMember(notePanel);

		return main;
	}

	private Layout createControlPanel() {
		Layout control = new VLayout();
		control.setWidth("50px");
		control.setAutoHeight();
		control.setMembersMargin(20);
		control.setLayoutMargin(10);
		control.setShowEdges(true);

		control.addMember(createButton);

		removeButton.setTooltip("Notiz löschen");
		removeButton.setCursor(Cursor.HAND);
		removeButton.setCanAcceptDrop(true);
		removeButton.setDropTypes("note");
		removeButton.addDropOverHandler(new DropOverHandler() {

			public void onDropOver(DropOverEvent event) {
				removeButton.setSrc(IconPool.Trash_Full_Big.getUrl());
			}
		});
		removeButton.addDropOutHandler(new DropOutHandler() {

			public void onDropOut(DropOutEvent event) {
				removeButton.setSrc(IconPool.Trash_Big.getUrl());
			}
		});
		return control;
	}

	private Canvas createNotePanel() {
		notePanel.setWidth("*");
		notePanel.setHeight100();
		notePanel.setShowEdges(true);
		notePanel.setCanAcceptDrop(true);
		notePanel.setDropTypes("new note");
		return notePanel;
	}

	public HasDropHandlers getDeleteNoteButton() {
		return removeButton;
	}

	public void addNewNoteWidget(NoteWidgetView noteWidget) {
		noteWidget.setPosition(notePanel.getOffsetX(), notePanel.getOffsetY());
		noteWidget.switchToEditor();
		notePanel.addChild((Canvas) noteWidget.asWidget());
	}

	public void addNoteWidget(NoteWidgetView noteWidget) {
		int left = notePanel.getLeft() + notePanel.getWidth() / 2;
		int top = notePanel.getTop() + notePanel.getHeight() / 2;
		noteWidget.setPosition(left, top);
		noteWidget.switchToContent();
		notePanel.addChild((Canvas) noteWidget.asWidget());
	}

	public void removeNoteWidget(NoteWidgetView noteWidget) {
		notePanel.removeChild((Canvas) noteWidget.asWidget());
	}

	public HasDropHandlers getCreateNewNoteButton() {
		return notePanel;
	}
}
