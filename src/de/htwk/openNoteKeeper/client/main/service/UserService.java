package de.htwk.openNoteKeeper.client.main.service;

import java.util.Map;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.htwk.openNoteKeeper.shared.OpenIdProvider;
import de.htwk.openNoteKeeper.shared.SettingsDTO;
import de.htwk.openNoteKeeper.shared.UserDTO;

@RemoteServiceRelativePath("user")
public interface UserService extends RemoteService {

	public Boolean isLoggedIn();

	public String getLoginUrl(String openIdProvider);

	public Map<OpenIdProvider, String> getLoginUrlsForOpenIdProviders();

	public String getLogoutUrl();

	public UserDTO getUser();

	public SettingsDTO getSettings(String userKey);

	public void updateSettings(SettingsDTO settings);
}
