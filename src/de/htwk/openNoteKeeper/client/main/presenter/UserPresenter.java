package de.htwk.openNoteKeeper.client.main.presenter;

import java.util.Map;

import com.google.gwt.user.client.ui.IsWidget;
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

	public interface UserView extends IsWidget {
		public void showLoginForOpenIdProviders(
				Map<OpenIdProvider, String> openIdProviders);

		public void showLogout(String logoutUrl);

		public void showUserData(UserDTO user);
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
						view.showLoginForOpenIdProviders(openIdProviders);
					}
				});
	}

	private void showUser() {
		userService.getUser(new StatusScreenCallback<UserDTO>("login") {

			@Override
			protected void success(UserDTO user) {
				Session.setCurrentUser(user);
				view.showUserData(user);
				eventBus.loggedIn(user);

				showLogout();
			}
		});
	}

	private void showLogout() {
		userService.getLogoutUrl(new StatusScreenCallback<String>(
				Status.Loading) {

			@Override
			protected void success(String logoutUrl) {
				view.showLogout(logoutUrl);
			}
		});
	}
}