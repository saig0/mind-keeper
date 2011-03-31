package de.htwk.openNoteKeeper.client.main.view.widget;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;

import de.htwk.openNoteKeeper.client.main.view.NoteViewImpl;

public class ConfigureNoteWidget extends Window {

	private NoteViewImpl noteView;

	private TextItem titleItem = new TextItem("title");

	public ConfigureNoteWidget() {
		setIsModal(true);
		setShowModalMask(true);
		setTitle("neue Notiz erstellen");
		setShowMinimizeButton(false);
		setAutoSize(true);

		Layout content = createContent();
		addItem(content);
		show();

		titleItem.focusInItem();
	}

	private Layout createContent() {
		Layout content = new VLayout();
		content.setMembersMargin(5);
		content.setLayoutMargin(10);
		content.setLayoutAlign(Alignment.CENTER);
		content.setWidth(200);
		content.setAutoHeight();

		DynamicForm form = new DynamicForm();
		titleItem.setTitle("Name");
		form.setFields(titleItem);
		form.setWidth100();
		form.draw();

		content.addMember(form);

		IButton confirmButton = new IButton("Fertig");
		confirmButton.setWidth(190);
		confirmButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				hide();
				String title = (String) titleItem.getValue();
				noteView.createNewNoteWidget(title);
			}
		});
		content.addMember(confirmButton);
		return content;
	}

	public void setPosition(int left, int top) {
		setLeft(left - getOffsetWidth() / 2);
		setTop(top - getOffsetHeight() / 2);
	}

	public void setNoteView(NoteViewImpl noteView) {
		this.noteView = noteView;
	}
}
