package de.htwk.openNoteKeeper.client.util;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Window;

import de.htwk.openNoteKeeper.client.widget.ErrorPopup;

public abstract class LoadingScreenCallback<T> implements AsyncCallback<T> {

	private final Canvas widget;
	private final Window loadingPanel;

	public LoadingScreenCallback(GwtEvent<?> event) {
		widget = (Canvas) event.getSource();
		loadingPanel = addLoading(widget);
	}

	private Window addLoading(Canvas widget) {
		widget.disable();

		int x = calculatePosition(widget.getAbsoluteLeft(), widget.getWidth());
		int y = calculatePosition(widget.getAbsoluteTop(), widget.getHeight());

		Window loadingPanel = new Window();
		loadingPanel.setShowEdges(false);
		loadingPanel.setShowHeader(false);
		loadingPanel.setShowStatusBar(false);
		loadingPanel.addItem(IconPool.Loading.createImage());
		loadingPanel.moveTo(x, y);
		loadingPanel.show();
		return loadingPanel;
	}

	private int calculatePosition(int absolute, int dimension) {
		return absolute + dimension / 2;
	}

	private void removeLoading() {
		loadingPanel.hide();
		widget.enable();
	}

	public void onFailure(Throwable caught) {
		removeLoading();

		ErrorPopup popupPanel = new ErrorPopup(caught);
		popupPanel.showRelativeTo(widget);
	}

	public void onSuccess(T result) {
		removeLoading();
		success(result);
	}

	protected abstract void success(T result);
}
