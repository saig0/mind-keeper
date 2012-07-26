package de.htwk.openNoteKeeper.client.main.view;

import java.util.Map;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.htwk.openNoteKeeper.client.i18n.CommonConstants;
import de.htwk.openNoteKeeper.client.main.presenter.UserPresenter.UserView;
import de.htwk.openNoteKeeper.client.util.IconPool;
import de.htwk.openNoteKeeper.client.widget.IconButton;
import de.htwk.openNoteKeeper.shared.OpenIdProvider;
import de.htwk.openNoteKeeper.shared.UserDTO;

@Singleton
public class UserViewImpl implements UserView {

	@Inject
	private CommonConstants constants;

	private MenuItem menuItem;
	private Command logoutCommand;

	public void showUserData(UserDTO user) {
		MenuBar logoutMenu = new MenuBar(true);
		IconButton logout = new IconButton(IconPool.Logout.createImage(),
				constants.signOut());
		logoutMenu.addItem(logout.getHTML(), true, logoutCommand);
		IconButton userButton = new IconButton(IconPool.Login.createImage(),
				user.getNick());
		menuItem = new MenuItem(userButton.getHTML(), true, logoutMenu);
	}

	public void setLogoutCommand(Command command) {
		this.logoutCommand = command;
	}

	public void setOpenIdProvider(Map<OpenIdProvider, Command> openIdProviders) {
		MenuBar openIdProviderMenu = new MenuBar(true);
		for (OpenIdProvider openIdProvider : openIdProviders.keySet()) {
			Command command = openIdProviders.get(openIdProvider);
			Image providerIcon = openIdProvider.getIcon().createImage();
			providerIcon.setTitle(openIdProvider.name());
			providerIcon.setHeight("35px");
			openIdProviderMenu.addItem(providerIcon.toString(), true, command);
		}
		IconButton userButton = new IconButton(IconPool.Login.createImage(),
				constants.signIn());
		menuItem = new MenuItem(userButton.getHTML(), true, openIdProviderMenu);
	}

	public MenuItem asMenuItem() {
		return menuItem;
	}
}
