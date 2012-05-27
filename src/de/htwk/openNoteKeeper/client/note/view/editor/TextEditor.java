package de.htwk.openNoteKeeper.client.note.view.editor;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.VerticalPanel;

public interface TextEditor {

	public void setParentPanel(VerticalPanel parentPanel);

	public void setHeight(int height);

	public void setColor(String color);

	public void hide();

	public void show(String text);

	public HasClickHandlers getSaveButton();

	public String getContentOfEditor();
}
