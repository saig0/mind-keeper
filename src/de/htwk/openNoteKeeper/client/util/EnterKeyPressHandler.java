package de.htwk.openNoteKeeper.client.util;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.shared.HasHandlers;

public class EnterKeyPressHandler implements KeyPressHandler {

	private final HasHandlers targetWidget;

	public EnterKeyPressHandler(HasHandlers targetWidget) {
		this.targetWidget = targetWidget;
	}

	public void onKeyPress(KeyPressEvent event) {
		if (event.getUnicodeCharCode() == KeyCodes.KEY_ENTER) {
			NativeEvent clickEvent = Document.get().createClickEvent(0, 0, 0,
					0, 0, false, true, false, false);
			DomEvent.fireNativeEvent(clickEvent, targetWidget);
		}
	}
}