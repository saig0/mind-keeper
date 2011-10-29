package de.htwk.openNoteKeeper.shared;

import java.util.LinkedList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GroupDTO implements IsSerializable {

	private String key;
	private String title;
	private AccessRole accessRole;
	private List<WhiteBoardDTO> whiteBoards = new LinkedList<WhiteBoardDTO>();
	private List<GroupDTO> subGroups = new LinkedList<GroupDTO>();

	GroupDTO() {
	}

	public GroupDTO(String key, String title, AccessRole accessRole) {
		this.key = key;
		this.title = title;
		this.accessRole = accessRole;
	}

	public String getKey() {
		return key;
	}

	public String getTitle() {
		return title;
	}

	public List<WhiteBoardDTO> getWhiteBoards() {
		return whiteBoards;
	}

	public List<GroupDTO> getSubGroups() {
		return subGroups;
	}

	public void addWhiteBoard(WhiteBoardDTO whiteBoard) {
		whiteBoards.add(whiteBoard);
	}

	public void removeWhiteBoard(WhiteBoardDTO whiteBoard) {
		whiteBoards.remove(whiteBoard);
	}

	public void addSubGroup(GroupDTO group) {
		subGroups.add(group);
	}

	public void removeSubGroup(GroupDTO group) {
		subGroups.remove(group);
	}

	public AccessRole getAccessRole() {
		return accessRole;
	}

	public void setAccessRole(AccessRole accessRole) {
		this.accessRole = accessRole;
	}
}
