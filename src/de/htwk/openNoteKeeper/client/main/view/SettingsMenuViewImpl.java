package de.htwk.openNoteKeeper.client.main.view;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.htwk.openNoteKeeper.client.i18n.CommonConstants;
import de.htwk.openNoteKeeper.client.main.presenter.SettingsMenuPresenter.SettingsMenuView;
import de.htwk.openNoteKeeper.client.util.IconPool;
import de.htwk.openNoteKeeper.client.widget.IconButton;

@Singleton
public class SettingsMenuViewImpl implements SettingsMenuView {

	@Inject
	private CommonConstants constants;
	private Command command;

	public MenuItem asMenuItem() {
		IconButton settings = new IconButton(
				IconPool.Settings_Small.createImage(), constants.settings());
		return new MenuItem(settings.getHTML(), true, command);
	}

	public void setCommand(Command command) {
		this.command = command;
	}

}
