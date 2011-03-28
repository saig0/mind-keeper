package de.htwk.openNoteKeeper.client.main.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.htwk.openNoteKeeper.shared.UserDTO;

@RemoteServiceRelativePath("user")
public interface UserService extends RemoteService {

	public Boolean isLoggedIn();

	public String getLoginUrl();

	public String getLogoutUrl();

	public UserDTO getUser();
}
