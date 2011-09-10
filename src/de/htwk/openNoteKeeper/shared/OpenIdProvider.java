package de.htwk.openNoteKeeper.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public enum OpenIdProvider implements IsSerializable {

	Google("https://www.google.com/accounts/o8/id"), Yahoo("http://yahoo.com/");

	private String url;

	OpenIdProvider() {
	}

	OpenIdProvider(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

}
