package de.htwk.openNoteKeeper.shared;

import java.util.LinkedList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SettingsDTO implements IsSerializable {

	private String key;
	private boolean shouldAskBeforeDelete;
	private String defaultNoteColor;
	private boolean shouldUseRichTextEditor;
	private List<String> textEditorOptions = new LinkedList<String>();

	SettingsDTO() {
	}

	public SettingsDTO(String key, boolean shouldAskBeforeDelete,
			String defaultNoteColor, boolean shouldUseRichTextEditor,
			List<String> textEditorOptions) {
		this.key = key;
		this.shouldAskBeforeDelete = shouldAskBeforeDelete;
		this.defaultNoteColor = defaultNoteColor;
		this.shouldUseRichTextEditor = shouldUseRichTextEditor;
		this.textEditorOptions.clear();
		this.textEditorOptions.addAll(textEditorOptions);
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

	public boolean shouldUseRichTextEditor() {
		return shouldUseRichTextEditor;
	}

	public void setShouldUseRichTextEditor(boolean shouldUseRichTextEditor) {
		this.shouldUseRichTextEditor = shouldUseRichTextEditor;
	}

	public List<String> getTextEditorOptions() {
		return textEditorOptions;
	}

	public void setTextEditorOptions(List<String> textEditorOptions) {
		this.textEditorOptions.clear();
		this.textEditorOptions.addAll(textEditorOptions);
	}

}
