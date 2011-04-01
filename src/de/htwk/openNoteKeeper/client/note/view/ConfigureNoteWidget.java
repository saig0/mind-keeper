package de.htwk.openNoteKeeper.client.note.view;

import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.CloseClientEvent;
import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;

import de.htwk.openNoteKeeper.client.note.presenter.NoteConfigurationPresenter.NoteConfigurationView;

public class ConfigureNoteWidget implements NoteConfigurationView {

	private Window widget = new Window();

	private TextItem titleItem = new TextItem("title");

	private IButton confirmButton = new IButton("Fertig");

	public ConfigureNoteWidget() {
		widget.setIsModal(true);
		widget.setShowModalMask(true);
		widget.setTitle("neue Notiz erstellen");
		widget.setShowMinimizeButton(false);
		widget.setAutoSize(true);

		widget.addCloseClickHandler(new CloseClickHandler() {

			public void onCloseClick(CloseClientEvent event) {
				hide();
			}
		});

		Layout content = createContent();
		widget.addItem(content);
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

		confirmButton.setWidth(190);
		content.addMember(confirmButton);
		return content;
	}

	public HasClickHandlers getCreateNewNoteButton() {
		return confirmButton;
	}

	public String getNoteTitle() {
		return (String) titleItem.getValue();
	}

	public void showOnPosition(int left, int top) {
		widget.setLeft(left - widget.getOffsetWidth() / 2);
		widget.setTop(top - widget.getOffsetHeight() / 2);
		titleItem.focusInItem();
	}

	public Widget asWidget() {
		widget.show();
		titleItem.focusInItem();
		return widget;
	}

	public void hide() {
		titleItem.setValue("");
		widget.hide();
	}
}
