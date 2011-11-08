package de.htwk.openNoteKeeper.client.note.presenter;

import java.util.List;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.inject.Inject;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

import de.htwk.openNoteKeeper.client.note.NoteEventBus;
import de.htwk.openNoteKeeper.client.note.presenter.HasTreeDropHandler.TreeDropHandler;
import de.htwk.openNoteKeeper.client.note.service.NoteServiceAsync;
import de.htwk.openNoteKeeper.client.note.view.NavigationInputWidget;
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

		public HasTreeDropHandler getDropHandler();

		public boolean hasSelectedGroup();

		public boolean hasSelectedWhiteBoard();

		public GroupDTO getSelectedGroup();

		public WhiteBoardDTO getSelectedWhiteBoard();

		public void addGroupToTree(TreeItem treeItem, GroupDTO group);

		public void addWhiteBoardToGroup(TreeItem treeItem,
				WhiteBoardDTO whiteboard);

		public HasClickHandlers getRemoveButton();

		public void removeSelectedGroup();

		public void removeSelectedWhiteBoard();

		public DragableWidget getDragWidget();
	}

	public interface NavigationInputView {
		public HasClickHandlers getCancelInputButton();

		public HasClickHandlers getSaveInputButton();

		public String getNameOfInputField();

		public void show(TreeItem selectedGroup);

		public void hide();
	}

	@Override
	public void bind() {
		view.getDropHandler().addTreeDropHandler(new TreeDropHandler() {

			public void onTreeDrop(final TreeItem dropTreeItem) {
				final DragableWidget dragWidget = view.getDragWidget();
				final NavigationInputView navigationInputView = new NavigationInputWidget(
						dragWidget.getDragIcon());
				navigationInputView.getSaveInputButton().addClickHandler(
						new SaveInputClickHandler(noteService, view,
								navigationInputView, dropTreeItem, dragWidget));
				navigationInputView.getCancelInputButton().addClickHandler(
						new CancelInputClickHandler(navigationInputView));
				navigationInputView.show(dropTreeItem);
			}

		});
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
