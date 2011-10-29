package de.htwk.openNoteKeeper.shared;

import java.util.LinkedList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class WhiteBoardDTO implements IsSerializable {

	private String key;
	private String title;
	private List<NoteDTO> notes = new LinkedList<NoteDTO>();

	WhiteBoardDTO() {
	}

	public WhiteBoardDTO(String key, String title) {
		this.key = key;
		this.title = title;
	}

	public String getKey() {
		return key;
	}

	public String getTitle() {
		return title;
	}

	public List<NoteDTO> getNotes() {
		return notes;
	}

	public void addNote(NoteDTO note) {
		notes.add(note);
	}

	public void removeNote(NoteDTO note) {
		notes.remove(note);
	}
}
