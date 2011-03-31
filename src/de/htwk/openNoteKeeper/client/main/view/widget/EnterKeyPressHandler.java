package de.htwk.openNoteKeeper.client.main.view.widget;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;

public class EnterKeyPressHandler implements KeyPressHandler {

	private final HasHandlers targetWidget;

	public EnterKeyPressHandler(HasHandlers targetWidget) {
		this.targetWidget = targetWidget;
	}

	public void onKeyPress(KeyPressEvent event) {
		if (event.getKeyName().toUpperCase().equals("ENTER")) {
			NativeEvent clickEvent = Document.get().createClickEvent(0, 0, 0,
					0, 0, false, true, false, false);
			DomEvent.fireNativeEvent(clickEvent, targetWidget);
		}
	}
}