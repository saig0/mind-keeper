package de.htwk.openNoteKeeper.client.util;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FocusWidget;

import de.htwk.openNoteKeeper.client.widget.ErrorPopup;

public abstract class NonBlockingCallback<T> implements AsyncCallback<T> {

	private FocusWidget widget;

	public NonBlockingCallback() {
	}

	public NonBlockingCallback(GwtEvent<?> event) {
		widget = (FocusWidget) event.getSource();
	}

	public void onFailure(Throwable caught) {
		ErrorPopup panel = new ErrorPopup(caught);
		if (widget != null)
			panel.showRelativeTo(widget);
		else
			panel.show();
	}

	public void onSuccess(T result) {
		success(result);
	}

	protected abstract void success(T result);
}
