package de.htwk.openNoteKeeper.client.util;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.htwk.openNoteKeeper.client.i18n.StatusConstants;
import de.htwk.openNoteKeeper.client.widget.ErrorPopup;
import de.htwk.openNoteKeeper.client.widget.StatusPanel;

public abstract class StatusScreenCallback<T> implements AsyncCallback<T> {

	private StatusPanel statusPanel;

	public enum Status {

		Loading, Load_Notes, Add_Group, Add_Whiteboard, Remove_Group, Remove_Whiteboard;

		private StatusConstants constants = GWT.create(StatusConstants.class);

		public String getMessage() {
			switch (this) {
			case Loading:
				return constants.loading();
			case Load_Notes:
				return constants.loadNotes();
			case Add_Group:
				return constants.addGroup();
			case Add_Whiteboard:
				return constants.addWhiteboard();
			case Remove_Group:
				return constants.removeGroup();
			case Remove_Whiteboard:
				return constants.addWhiteboard();
			default:
				return constants.loading();
			}
		}

	}

	public StatusScreenCallback(Status status) {
		this(status.getMessage());
	}

	public StatusScreenCallback(String message) {
		statusPanel = new StatusPanel(message + "...");
		statusPanel.show();
	}

	private void removeStatus() {
		statusPanel.hide();
	}

	public void onFailure(Throwable caught) {
		removeStatus();

		ErrorPopup popupPanel = new ErrorPopup(caught);
		popupPanel.show();
	}

	public void onSuccess(T result) {
		removeStatus();
		success(result);
	}

	protected abstract void success(T result);
}
