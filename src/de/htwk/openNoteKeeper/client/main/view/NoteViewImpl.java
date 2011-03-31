package de.htwk.openNoteKeeper.client.main.view;

import java.util.List;

import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Img;
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
import de.htwk.openNoteKeeper.client.main.view.widget.ConfigureNoteWidget;
import de.htwk.openNoteKeeper.client.main.view.widget.DragableImage;
import de.htwk.openNoteKeeper.client.main.view.widget.NoteWidget;
import de.htwk.openNoteKeeper.client.util.IconPool;
import de.htwk.openNoteKeeper.shared.NoteDTO;

public class NoteViewImpl implements NoteView {

	private Img createButton = new DragableImage("Neue Notiz",
			IconPool.Notice_Big.getUrl(), 64, 64);
	private Img removeButton = new Img(IconPool.Trash_Big.getUrl(), 64, 64);

	private Canvas notePanel = new Canvas();
	private Canvas activeNote = null;

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
				if (activeNote != null)
					notePanel.removeChild(activeNote);
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

		notePanel.addDropHandler(new DropHandler() {

			public void onDrop(DropEvent event) {
				createNewNoteConfigureWidget(event);
			}
		});

		return notePanel;
	}

	public void createNewNoteWidget(String title) {
		NoteWidget note = new NoteWidget(title);
		note.setPosition(notePanel.getOffsetX(), notePanel.getOffsetY());
		note.setNoteView(this);
		notePanel.addChild(note);
	}

	public void setActiveNote(NoteWidget note) {
		this.activeNote = note;
	}

	public void setNotes(List<NoteDTO> notes) {
		// TODO Auto-generated method stub

	}

	private void createNewNoteConfigureWidget(DropEvent event) {
		ConfigureNoteWidget noteTitleWidget = new ConfigureNoteWidget();
		noteTitleWidget.setPosition(event.getX(), event.getY());
		noteTitleWidget.setNoteView(this);
	}

}
