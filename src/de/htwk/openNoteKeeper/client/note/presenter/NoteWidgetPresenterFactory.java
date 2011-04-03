package de.htwk.openNoteKeeper.client.note.presenter;

import java.util.HashMap;
import java.util.Map;

import de.htwk.openNoteKeeper.client.note.NoteEventBus;
import de.htwk.openNoteKeeper.client.note.presenter.NoteWidgetPresenter.NoteWidgetView;

public class NoteWidgetPresenterFactory {

	private static Map<NoteWidgetView, NoteWidgetPresenter> handler = new HashMap<NoteWidgetView, NoteWidgetPresenter>();

	public static NoteWidgetPresenter createPresenter(NoteEventBus eventBus) {
		NoteWidgetPresenter presenter = eventBus
				.addHandler(NoteWidgetPresenter.class);
		handler.put(presenter.getView(), presenter);
		return presenter;
	}

	public static void destroyPresenter(NoteWidgetView noteWidget) {
		handler.remove(noteWidget);
	}
}
