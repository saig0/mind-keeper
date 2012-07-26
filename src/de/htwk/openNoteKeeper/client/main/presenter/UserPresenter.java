package de.htwk.openNoteKeeper.client.main.presenter;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.inject.Inject;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

import de.htwk.openNoteKeeper.client.main.MainEventBus;
import de.htwk.openNoteKeeper.client.main.service.UserServiceAsync;
import de.htwk.openNoteKeeper.client.main.view.UserViewImpl;
import de.htwk.openNoteKeeper.client.util.StatusScreenCallback;
import de.htwk.openNoteKeeper.client.util.StatusScreenCallback.Status;
import de.htwk.openNoteKeeper.shared.OpenIdProvider;
import de.htwk.openNoteKeeper.shared.UserDTO;

@Presenter(view = UserViewImpl.class)
public class UserPresenter extends BasePresenter<UserViewImpl, MainEventBus> {

	public interface UserView {
		public void showUserData(UserDTO user);

		public void setLogoutCommand(Command command);

		public void setOpenIdProvider(
				Map<OpenIdProvider, Command> openIdProviders);

		public MenuItem asMenuItem();
	}

	@Inject
	private UserServiceAsync userService;

	public void onStart() {
		userService
				.isLoggedIn(new StatusScreenCallback<Boolean>(Status.Loading) {

					@Override
					protected void success(Boolean isLoggedIn) {
						if (isLoggedIn) {
							showUser();
						} else {
							showLogin();
						}
					}
				});
	}

	private void showLogin() {
		userService
				.getLoginUrlsForOpenIdProviders(new StatusScreenCallback<Map<OpenIdProvider, String>>(
						Status.Loading) {

					@Override
					protected void success(
							Map<OpenIdProvider, String> openIdProviders) {
						Map<OpenIdProvider, Command> providers = new HashMap<OpenIdProvider, Command>();
						for (OpenIdProvider provider : openIdProviders.keySet()) {
							final String url = openIdProviders.get(provider);
							providers.put(provider, new Command() {

								public void execute() {
									Window.open(url, "_self", "");
								}
							});
						}
						view.setOpenIdProvider(providers);
						eventBus.setUserMenu(view.asMenuItem());
					}
				});
	}

	private void showUser() {
		userService.getUser(new StatusScreenCallback<UserDTO>("login") {

			@Override
			protected void success(UserDTO user) {
				Session.setCurrentUser(user);
				eventBus.loggedIn(user);
				showLogout(user);
			}
		});
	}

	private void showLogout(final UserDTO user) {
		userService.getLogoutUrl(new StatusScreenCallback<String>(
				Status.Loading) {

			@Override
			protected void success(final String logoutUrl) {
				view.setLogoutCommand(new Command() {

					public void execute() {
						Window.open(logoutUrl, "_self", "");
					}
				});
				view.showUserData(user);
				eventBus.setUserMenu(view.asMenuItem());
			}
		});
	}
}