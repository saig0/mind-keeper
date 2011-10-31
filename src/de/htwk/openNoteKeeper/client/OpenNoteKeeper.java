package de.htwk.openNoteKeeper.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.Mvp4gModule;

import de.htwk.openNoteKeeper.client.widget.ErrorPopup;

public class OpenNoteKeeper implements EntryPoint {

	public void onModuleLoad() {
		Mvp4gModule module = GWT.create(Mvp4gModule.class);
		module.createAndStartModule();
		Widget startView = getStartView(module);
		RootLayoutPanel.get().add(startView);

		GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {

			public void onUncaughtException(Throwable e) {
				new ErrorPopup(e).show();
				e.printStackTrace();
			}
		});
	}

	private Widget getStartView(Mvp4gModule module) {
		Object startView = module.getStartView();
		if (startView instanceof Widget)
			return (Widget) startView;
		else if (startView instanceof IsWidget)
			return ((IsWidget) startView).asWidget();
		else
			throw new IllegalArgumentException(
					"unexpected type of start view: " + startView.getClass());
	}
}
