package de.htwk.openNoteKeeper.client.note.presenter;

import java.util.List;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

import de.htwk.openNoteKeeper.client.note.NoteEventBus;
import de.htwk.openNoteKeeper.client.note.service.NoteServiceAsync;
import de.htwk.openNoteKeeper.client.note.view.NavigationTreeViewImpl;
import de.htwk.openNoteKeeper.client.util.LoadingScreenCallback;
import de.htwk.openNoteKeeper.shared.GroupDTO;
import de.htwk.openNoteKeeper.shared.UserDTO;
import de.htwk.openNoteKeeper.shared.WhiteBoardDTO;

@Presenter(view = NavigationTreeViewImpl.class)
public class NavigationTreePresenter extends
		BasePresenter<NavigationTreeViewImpl, NoteEventBus> {

	@Inject
	private NoteServiceAsync noteService;

	public interface NavigationTreeView extends IsWidget {
		public void setGroups(List<GroupDTO> groups);

		public HasClickHandlers getCreateNewGroupButton();

		public HasClickHandlers getCreateNewWhiteBoardButton();

		public boolean hasSelectedGroup();

		public boolean hasSelectedWhiteBoard();

		public void showNewGroupInputField();

		public void showNewWhiteBoardInputField();

		public HasClickHandlers getCancelInputButton();

		public HasClickHandlers getSaveInputButton();

		public String getNameOfInputField();

		public GroupDTO getSelectedGroup();

		public WhiteBoardDTO getSelectedWhiteBoard();

		public void addGroupToSelectedGroup(GroupDTO group);

		public void addWhiteBoardToSelectedGroup(WhiteBoardDTO whiteboard);

		public void hideInputField();

		public HasClickHandlers getRemoveButton();

		public void removeSelectedGroup();

		public void removeSelectedWhiteBoard();
	}

	@Override
	public void bind() {
		view.getCreateNewGroupButton().addClickHandler(
				new CreateNewGroupClickHandler(view, noteService));
		view.getCreateNewWhiteBoardButton().addClickHandler(
				new CreateNeWhiteBoardClickHandler(view, noteService));
		view.getRemoveButton().addClickHandler(
				new RemoveItemClickHandler(view, noteService));
	}

	public void onLoggedIn(UserDTO user) {

		noteService.getAllGroupsForUser(user.getId(),
				new LoadingScreenCallback<List<GroupDTO>>() {

					@Override
					protected void success(List<GroupDTO> groups) {
						view.setGroups(groups);
					}
				});
	}
}
