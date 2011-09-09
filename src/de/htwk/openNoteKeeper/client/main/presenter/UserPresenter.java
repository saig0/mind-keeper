package de.htwk.openNoteKeeper.client.main.presenter;

import java.util.Map;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

import de.htwk.openNoteKeeper.client.main.MainEventBus;
import de.htwk.openNoteKeeper.client.main.service.UserServiceAsync;
import de.htwk.openNoteKeeper.client.main.view.UserViewImpl;
import de.htwk.openNoteKeeper.client.util.NonBlockingCallback;
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
		// userService.isLoggedIn(new NonBlockingCallback<Boolean>() {
		//
		// @Override
		// protected void success(Boolean isLoggedIn) {
		// if (isLoggedIn) {
		// showLogout();
		// showUser();
		// } else {
		// showLogin();
		// }
		// }
		// });
	}

	private void showLogin() {
		userService
				.getLoginUrlsForOpenIdProviders(new NonBlockingCallback<Map<OpenIdProvider, String>>() {

					@Override
					protected void success(
							Map<OpenIdProvider, String> openIdProviders) {
						System.out.println("show login: " + openIdProviders);
						view.showLoginForOpenIdProviders(openIdProviders);
					}
				});
	}

	private void showUser() {
		userService.getUser(new NonBlockingCallback<UserDTO>() {

			@Override
			protected void success(UserDTO user) {
				view.showUserData(user);
				eventBus.loggedIn(user);
			}
		});
	}

	private void showLogout() {
		userService.getLogoutUrl(new NonBlockingCallback<String>() {

			@Override
			protected void success(String logoutUrl) {
				view.showLogout(logoutUrl);
			}
		});
	}
}