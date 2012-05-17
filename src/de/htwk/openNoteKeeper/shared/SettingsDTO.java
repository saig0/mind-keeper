package de.htwk.openNoteKeeper.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SettingsDTO implements IsSerializable {

	private boolean shouldAskBeforeDelete;

	public SettingsDTO(boolean shouldAskBeforeDelete) {
		this.shouldAskBeforeDelete = shouldAskBeforeDelete;
	}

	public boolean isShouldAskBeforeDelete() {
		return shouldAskBeforeDelete;
	}

	public void setShouldAskBeforeDelete(boolean shouldAskBeforeDelete) {
		this.shouldAskBeforeDelete = shouldAskBeforeDelete;
	}

}
