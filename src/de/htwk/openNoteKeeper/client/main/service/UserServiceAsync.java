package de.htwk.openNoteKeeper.client.main.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.htwk.openNoteKeeper.shared.UserDTO;

public interface UserServiceAsync {

	void isLoggedIn(AsyncCallback<Boolean> callback);

	void getLoginUrl(AsyncCallback<String> callback);

	void getLogoutUrl(AsyncCallback<String> callback);

	void getUser(AsyncCallback<UserDTO> callback);

}
