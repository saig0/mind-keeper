package de.htwk.openNoteKeeper.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

import de.htwk.openNoteKeeper.client.util.IconPool;

public enum OpenIdProvider implements IsSerializable {

	Google("https://www.google.com/accounts/o8/id", IconPool.Google_Logo), Yahoo(
			"http://yahoo.com/", IconPool.Yahoo_Logo), Aol(
			"http://openid.aol.com/", IconPool.Aol_Logo);

	private String url;
	private IconPool icon;

	OpenIdProvider() {
	}

	OpenIdProvider(String url, IconPool icon) {
		this.url = url;
		this.icon = icon;
	}

	public String getUrl() {
		return url;
	}

	public IconPool getIcon() {
		return icon;
	}

}
