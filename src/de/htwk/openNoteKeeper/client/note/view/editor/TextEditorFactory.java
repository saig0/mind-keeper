package de.htwk.openNoteKeeper.client.note.view.editor;

import de.htwk.openNoteKeeper.client.main.presenter.Session;

public class TextEditorFactory {

	public static TextEditor createTextEditor() {
		if (Session.getSettings().shouldUseRichTextEditor()) {
			return new CkTextEditor();
		} else {
			return new SimpleTextEditor();
		}
	}
}
