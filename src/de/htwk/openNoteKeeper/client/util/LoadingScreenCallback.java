package de.htwk.openNoteKeeper.client.util;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.PopupPanel;

import de.htwk.openNoteKeeper.client.widget.ErrorPopup;

public abstract class LoadingScreenCallback<T> implements AsyncCallback<T> {

	private final FocusWidget widget;
	private final PopupPanel loadingPanel;

	public LoadingScreenCallback() {
		widget = null;
		loadingPanel = addLoading(widget);
		loadingPanel.show();
	}

	public LoadingScreenCallback(GwtEvent<?> event) {
		widget = (FocusWidget) event.getSource();
		loadingPanel = addLoading(widget);
		loadingPanel.show();
	}

	private PopupPanel addLoading(FocusWidget widget) {
		PopupPanel loadingPanel = new PopupPanel(false, false);
		loadingPanel.setAnimationEnabled(true);
		loadingPanel.add(IconPool.Loading.createImage());

		if (widget != null) {
			widget.setEnabled(false);

			Element element = widget.getElement();
			int x = calculatePosition(element.getAbsoluteLeft(),
					element.getClientWidth());
			int y = calculatePosition(element.getAbsoluteTop(),
					element.getClientHeight());
			loadingPanel.setPopupPosition(x, y);
		}
		return loadingPanel;
	}

	private int calculatePosition(int absolute, int dimension) {
		return absolute + dimension / 2;
	}

	private void removeLoading() {
		loadingPanel.hide();
		if (widget != null)
			widget.setEnabled(true);
	}

	public void onFailure(Throwable caught) {
		removeLoading();

		ErrorPopup popupPanel = new ErrorPopup(caught);
		if (widget != null)
			popupPanel.showRelativeTo(widget);
		else
			popupPanel.show();
	}

	public void onSuccess(T result) {
		removeLoading();
		success(result);
	}

	protected abstract void success(T result);
}
