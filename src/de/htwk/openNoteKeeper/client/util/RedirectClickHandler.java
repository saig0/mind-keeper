package de.htwk.openNoteKeeper.client.util;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;

import de.htwk.openNoteKeeper.client.main.view.RedirectClickHandlerData;

public class RedirectClickHandler implements ClickHandler {
	private RedirectClickHandlerData handler = new RedirectClickHandlerData();

	public RedirectClickHandler(String url) {
		this.handler.url = url;
	}

	public void onClick(ClickEvent event) {
		Window.open(handler.url, "_self", "");
	}
}
