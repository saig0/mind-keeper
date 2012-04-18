package de.htwk.openNoteKeeper.client.main.presenter;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

import de.htwk.openNoteKeeper.client.main.MainEventBus;
import de.htwk.openNoteKeeper.client.main.view.MainViewImpl;
import de.htwk.openNoteKeeper.client.widget.StatusArea;
import de.htwk.openNoteKeeper.client.widget.StatusPanel;
import de.htwk.openNoteKeeper.shared.UserDTO;

@Presenter(view = MainViewImpl.class)
public class MainPresenter extends BasePresenter<MainViewImpl, MainEventBus> {
	public interface MainView extends IsWidget {
		public void setContent(Widget content);

		public void setActionBar(Widget actionWidget);
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
			// TODO für Firefox optimieren
			statusArea
					.addStatusMessage(new StatusPanel(
							"optimale Darstellung der Website mit Chrome-Browser (Firefox folgt)",
							true));
		} else {
			statusArea
					.addStatusMessage(new StatusPanel(
							"optimale Darstellung der Website mit Chrome-Browser",
							true));
		}
	}

	private String getUserAgent() {
		return Window.Navigator.getUserAgent().toLowerCase();
	}

	public void onLoggedIn(UserDTO user) {

	}

	public void onSetContent(Widget content) {
		view.setContent(content);
	}

	public void onSetActionBar(Widget actionWidget) {
		view.setActionBar(actionWidget);
	}
}