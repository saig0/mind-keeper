package de.htwk.openNoteKeeper.client.main.service;

import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.htwk.openNoteKeeper.shared.OpenIdProvider;
import de.htwk.openNoteKeeper.shared.UserDTO;

public interface UserServiceAsync {

	void isLoggedIn(AsyncCallback<Boolean> callback);

	void getLoginUrl(String openIdProvider, AsyncCallback<String> callback);

	void getLogoutUrl(AsyncCallback<String> callback);

	void getUser(AsyncCallback<UserDTO> callback);

	void getLoginUrlsForOpenIdProviders(
			AsyncCallback<Map<OpenIdProvider, String>> callback);

}
