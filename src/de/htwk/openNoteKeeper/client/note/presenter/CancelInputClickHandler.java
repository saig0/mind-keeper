package de.htwk.openNoteKeeper.client.note.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import de.htwk.openNoteKeeper.client.note.presenter.NavigationTreePresenter.NavigationInputView;

public final class CancelInputClickHandler implements ClickHandler {
	private final NavigationInputView navigationInputView;

	public CancelInputClickHandler(NavigationInputView navigationInputView) {
		this.navigationInputView = navigationInputView;
	}

	public void onClick(ClickEvent event) {
		navigationInputView.hide();
	}
}
