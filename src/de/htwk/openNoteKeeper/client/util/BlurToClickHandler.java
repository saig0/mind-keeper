package de.htwk.openNoteKeeper.client.util;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.shared.HasHandlers;

public class BlurToClickHandler implements BlurHandler {

	private final HasHandlers targetWidget;

	public BlurToClickHandler(HasHandlers targetWidget) {
		this.targetWidget = targetWidget;
	}

	public void onBlur(BlurEvent event) {
		NativeEvent clickEvent = Document.get().createClickEvent(0, 0, 0, 0, 0,
				false, true, false, false);
		DomEvent.fireNativeEvent(clickEvent, targetWidget);
	}
}