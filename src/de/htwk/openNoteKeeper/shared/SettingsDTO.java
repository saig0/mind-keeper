package de.htwk.openNoteKeeper.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SettingsDTO implements IsSerializable {

	private String key;
	private boolean shouldAskBeforeDelete;
	private String defaultNoteColor;

	SettingsDTO() {
	}

	public SettingsDTO(String key, boolean shouldAskBeforeDelete,
			String defaultNoteColor) {
		this.key = key;
		this.shouldAskBeforeDelete = shouldAskBeforeDelete;
		this.defaultNoteColor = defaultNoteColor;
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

	public void setDefaultNoteColor(String defaultNoteColor) {
		this.defaultNoteColor = defaultNoteColor;
	}

	public String getDefaultNoteColor() {
		return defaultNoteColor;
	}

}
