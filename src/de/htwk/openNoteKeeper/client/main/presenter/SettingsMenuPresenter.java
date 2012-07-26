package de.htwk.openNoteKeeper.client.main.presenter;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuItem;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

import de.htwk.openNoteKeeper.client.main.MainEventBus;
import de.htwk.openNoteKeeper.client.main.view.SettingsMenuViewImpl;
import de.htwk.openNoteKeeper.shared.UserDTO;

@Presenter(view = SettingsMenuViewImpl.class)
public class SettingsMenuPresenter extends
		BasePresenter<SettingsMenuViewImpl, MainEventBus> {

	public interface SettingsMenuView {
		public MenuItem asMenuItem();

		public void setCommand(Command command);
	}

	@Override
	public void bind() {
		view.setCommand(new Command() {

			public void execute() {
				eventBus.showSettings();
			}
		});
	}

	public void onStart() {
	}

	public void onLoggedIn(UserDTO user) {
		eventBus.setSettingsMenu(view.asMenuItem());
	}
}
