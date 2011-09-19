package de.htwk.openNoteKeeper.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CoordinateDTO implements IsSerializable {

	private Integer x;
	private Integer y;

	CoordinateDTO() {
	}

	public CoordinateDTO(Integer x, Integer y) {
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
