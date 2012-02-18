package de.htwk.openNoteKeeper.client.main;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Provides;

import de.htwk.openNoteKeeper.client.widget.StatusArea;

public class MainModule extends AbstractGinModule {

	private static StatusArea statusArea;

	@Override
	protected void configure() {
		// add here any extra configuration you need
	}

	@Provides
	public StatusArea getStatusArea() {
		if (statusArea == null) {
			statusArea = new StatusArea();
		}
		return statusArea;
	}

}