package de.htwk.openNoteKeeper.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class NoteDTO implements IsSerializable {

	private String key;
	private String title;
	private String content;
	private CoordinateDTO position;
	private CoordinateDTO size;

	NoteDTO() {
	}

	public NoteDTO(String key, String title, String content,
			CoordinateDTO position, CoordinateDTO size) {
		this.key = key;
		this.title = title;
		this.content = content;
		this.position = position;
		this.size = size;
	}

	public String getKey() {
		return key;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public CoordinateDTO getSize() {
		return size;
	}

	public void setSize(CoordinateDTO size) {
		this.size = size;
	}

	public CoordinateDTO getPosition() {
		return position;
	}

	public void setPosition(CoordinateDTO position) {
		this.position = position;
	}

}
