package de.htwk.openNoteKeeper.client.util;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.widgets.Canvas;

import de.htwk.openNoteKeeper.client.widget.ErrorPopup;

public abstract class AbstractCallback<T> implements AsyncCallback<T> {

	private Canvas widget;

	public AbstractCallback() {
	}

	public AbstractCallback(GwtEvent<?> event) {
		widget = (Canvas) event.getSource();
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
