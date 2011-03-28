package de.htwk.openNoteKeeper.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UserDTO implements IsSerializable {
	private String id;
	private String nick;
	private String email;

	public UserDTO() {
	}

	public UserDTO(String id, String nick, String email) {
		this.id = id;
		this.nick = nick;
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public String getNick() {
		return nick;
	}

	public String getEmail() {
		return email;
	}
}
