package de.htwk.openNoteKeeper.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SettingsDTO implements IsSerializable {

	private String key;
	private boolean shouldAskBeforeDelete;

	SettingsDTO() {
	}

	public SettingsDTO(String key, boolean shouldAskBeforeDelete) {
		this.key = key;
		this.shouldAskBeforeDelete = shouldAskBeforeDelete;
	}

	public boolean shouldAskBeforeDelete() {
		return shouldAskBeforeDelete;
	}

	public void setShouldAskBeforeDelete(boolean shouldAskBeforeDelete) {
		this.shouldAskBeforeDelete = shouldAskBeforeDelete;
	}

	public String getKey() {
		return key;
	}

}
