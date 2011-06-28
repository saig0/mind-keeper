package de.htwk.openNoteKeeper.client.note;

import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.view.NoStartView;

@Events(startView = NoStartView.class, module = NoteModule.class)
public interface NoteEventBus extends EventBus {

	@Event(forwardToParent = true)
	public void setContent(Widget content);

}
