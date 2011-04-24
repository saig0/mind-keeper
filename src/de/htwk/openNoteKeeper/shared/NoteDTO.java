package de.htwk.openNoteKeeper.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class NoteDTO implements IsSerializable {

	private Long id;
	private String title;
	private String content;
	private Coordinate position;
	private Coordinate size;

	public NoteDTO() {
	}

	public NoteDTO(Long id, String title, String content,
			Coordinate position, Coordinate size) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.position = position;
		this.size = size;
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

	public void setContent(String content) {
		this.content = content;
	}

	public Coordinate getSize() {
		return size;
	}

	public void setSize(Coordinate size) {
		this.size = size;
	}

	public Coordinate getPosition() {
		return position;
	}

	public void setPosition(Coordinate position) {
		this.position = position;
	}

}
