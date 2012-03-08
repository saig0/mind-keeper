package de.htwk.openNoteKeeper.client.util;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.shared.HasHandlers;

public class IndirectClickHandler implements ClickHandler {

	private final HasHandlers targetWidget;

	public IndirectClickHandler(HasHandlers targetWidget) {
		this.targetWidget = targetWidget;
	}

	public void onClick(ClickEvent event) {
		DomEvent.fireNativeEvent(event.getNativeEvent(), targetWidget);
	}
}