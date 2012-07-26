package de.htwk.openNoteKeeper.client.main.presenter;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

import de.htwk.openNoteKeeper.client.main.MainEventBus;
import de.htwk.openNoteKeeper.client.main.view.MainViewImpl;
import de.htwk.openNoteKeeper.client.widget.StatusArea;
import de.htwk.openNoteKeeper.client.widget.StatusPanel;

@Presenter(view = MainViewImpl.class)
public class MainPresenter extends BasePresenter<MainViewImpl, MainEventBus> {
	public interface MainView extends IsWidget {
		public void setContent(Widget content);

		public void setActionBar(Widget actionWidget);

		public void setSettingsMenu(MenuItem menuItem);

		public void setUserMenu(MenuItem menuItem);
	}

	@Inject
	private StatusArea statusArea;

	@Override
	public void bind() {

	}

	public void onStart() {
		String userAgent = getUserAgent();
		if (userAgent.contains("chrome")) {
			// ok
		} else if (userAgent.contains("firefox")) {
			// naja
		} else {
			statusArea.addStatusMessage(new StatusPanel(
					"optimale Darstellung der Website mit Chrome-Browser",
					true, 10));
		}
	}

	private String getUserAgent() {
		return Window.Navigator.getUserAgent().toLowerCase();
	}

	public void onSetContent(Widget content) {
		view.setContent(content);
	}

	public void onSetActionBar(Widget actionWidget) {
		view.setActionBar(actionWidget);
	}

	public void onSetSettingsMenu(MenuItem menuItem) {
		view.setSettingsMenu(menuItem);
	}

	public void onSetUserMenu(MenuItem menuItem) {
		view.setUserMenu(menuItem);
	}
}