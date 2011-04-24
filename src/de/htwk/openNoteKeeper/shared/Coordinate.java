package de.htwk.openNoteKeeper.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Coordinate implements IsSerializable {

	private Integer x;
	private Integer y;

	public Coordinate() {
	}

	public Coordinate(Integer x, Integer y) {
		this.x = x;
		this.y = y;
	}

	public Integer getX() {
		return x;
	}

	public Integer getY() {
		return y;
	}

}
