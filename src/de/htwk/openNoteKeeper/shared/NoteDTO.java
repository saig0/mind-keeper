package de.htwk.openNoteKeeper.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class NoteDTO implements IsSerializable {

	private String key;
	private String title;
	private String content;
	private String color;
	private CoordinateDTO position;
	private CoordinateDTO size;

	NoteDTO() {
	}

	public NoteDTO(String key, String title, String content, String color,
			CoordinateDTO position, CoordinateDTO size) {
		this.key = key;
		this.title = title;
		this.content = content;
		this.position = position;
		this.size = size;
		this.color = color;
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

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
