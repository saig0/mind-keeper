package de.htwk.openNoteKeeper.client.util;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;

public class RedirectClickHandler implements ClickHandler {
	private final String url;
	private final boolean openInNewWindow;

	public RedirectClickHandler(String url) {
		this(url, false);
	}

	public RedirectClickHandler(String url, boolean openInNewWindow) {
		this.url = url;
		this.openInNewWindow = openInNewWindow;
	}

	public void onClick(ClickEvent event) {
		if (openInNewWindow)
			Window.open(url, "_blank", "");
		else
			Window.open(url, "_self", "");
	}
}
