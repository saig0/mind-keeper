package de.htwk.openNoteKeeper.client.main.presenter;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

import de.htwk.openNoteKeeper.client.main.MainEventBus;
import de.htwk.openNoteKeeper.client.main.service.UserServiceAsync;
import de.htwk.openNoteKeeper.client.main.view.UserViewImpl;
import de.htwk.openNoteKeeper.client.util.AbstractCallback;
import de.htwk.openNoteKeeper.shared.UserDTO;

@Presenter(view = UserViewImpl.class)
public class UserPresenter extends BasePresenter<UserViewImpl, MainEventBus> {

	public interface UserView extends IsWidget {
		public void setLoginUrl(String loginUrl);

		public void setLogoutUrl(String logoutUrl);

		public void setUser(UserDTO user);
	}

	@Inject
	private UserServiceAsync userService;

	@Override
	public void bind() {

	}

	public void onStart() {
		eventBus.setUserWidget(view.asWidget());

		initUserWidget();
	}

	private void initUserWidget() {
		userService.isLoggedIn(new AbstractCallback<Boolean>() {

			@Override
			protected void success(Boolean isLoggedIn) {
				if (isLoggedIn) {
					setLogoutUrl();
					setUser();
				} else {
					setLoginUrl();
				}
			}
		});
	}

	private void setLoginUrl() {
		userService.getLoginUrl(new AbstractCallback<String>() {

			@Override
			protected void success(String loginUrl) {
				view.setLoginUrl(loginUrl);
			}
		});
	}

	private void setUser() {
		userService.getUser(new AbstractCallback<UserDTO>() {

			@Override
			protected void success(UserDTO user) {
				view.setUser(user);
				eventBus.loggedIn(user);
			}
		});
	}

	private void setLogoutUrl() {
		userService.getLogoutUrl(new AbstractCallback<String>() {

			@Override
			protected void success(String logoutUrl) {
				view.setLogoutUrl(logoutUrl);
			}
		});
	}
}