package de.htwk.openNoteKeeper.client.main.view;

import java.util.List;

import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.DropEvent;
import com.smartgwt.client.widgets.events.DropHandler;
import com.smartgwt.client.widgets.events.DropOutEvent;
import com.smartgwt.client.widgets.events.DropOutHandler;
import com.smartgwt.client.widgets.events.DropOverEvent;
import com.smartgwt.client.widgets.events.DropOverHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;

import de.htwk.openNoteKeeper.client.main.presenter.NotePresenter.NoteView;
import de.htwk.openNoteKeeper.client.util.DragableImage;
import de.htwk.openNoteKeeper.client.util.IconPool;
import de.htwk.openNoteKeeper.shared.NoteDTO;

public class NoteViewImpl implements NoteView {

	private Img createButton = new DragableImage("Neue Notiz",
			IconPool.Notice_Big.getUrl(), 64, 64);
	private Img removeButton = new Img(IconPool.Trash_Big.getUrl(), 64, 64);

	private Canvas notePanel = new Canvas();

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
		removeButton.addDropHandler(new DropHandler() {

			public void onDrop(DropEvent event) {

				notePanel.removeChild(notePanel.getChildren()[ch]);
			}
		});
		return control;
	}

	int ch = 0;

	private Canvas createNotePanel() {
		notePanel.setWidth("*");
		notePanel.setHeight100();
		notePanel.setShowEdges(true);
		notePanel.setCanAcceptDrop(true);
		notePanel.setDropTypes("new note");

		notePanel.addDropHandler(new DropHandler() {

			public void onDrop(DropEvent event) {
				Window note = new Window();
				note.setWidth("200px");
				note.setHeight("200px");
				note.setLeft(notePanel.getOffsetX() - 15);
				note.setTop(notePanel.getOffsetY() - 15);
				note.setTitle("Notiz");
				note.setShowCloseButton(false);
				note.setIsModal(false);
				note.setAnimateMinimize(true);
				note.setCanDrag(true);
				note.setCanDrop(true);
				note.setCanDragResize(true);
				note.setDragType("note");

				int c = 0;
				for (Canvas child : notePanel.getChildren())
					if (!child.equals(note))
						c++;
					else
						ch = c;

				notePanel.addChild(note);
			}
		});

		return notePanel;
	}

	public void setNotes(List<NoteDTO> notes) {
		// TODO Auto-generated method stub

	}

}
