package de.htwk.openNoteKeeper.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class NoteDTO implements IsSerializable {

	private Long id;
	private String title;
	private String content;

	public NoteDTO() {
	}

	public NoteDTO(Long id, String title, String content) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}
}
