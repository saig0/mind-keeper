package de.htwk.openNoteKeeper.client.note.presenter;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.inject.Inject;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

import de.htwk.openNoteKeeper.client.main.presenter.Session;
import de.htwk.openNoteKeeper.client.note.NoteEventBus;
import de.htwk.openNoteKeeper.client.note.presenter.HasTreeDropHandler.TreeDropHandler;
import de.htwk.openNoteKeeper.client.note.service.NoteServiceAsync;
import de.htwk.openNoteKeeper.client.note.view.NavigationInputWidget;
import de.htwk.openNoteKeeper.client.note.view.NavigationTreeViewImpl;
import de.htwk.openNoteKeeper.client.util.IconPool;
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

		public void addWhiteBoardToSelectedGroup(WhiteBoardDTO whiteboard);

		public void hideInputField();

		public HasClickHandlers getRemoveButton();

		public void removeSelectedGroup();

		public void removeSelectedWhiteBoard();
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
				final NavigationInputView navigationInputView = new NavigationInputWidget(
						IconPool.Folder.createImage());
				navigationInputView.getSaveInputButton().addClickHandler(
						new ClickHandler() {

							public void onClick(ClickEvent event) {
								String newGroupName = navigationInputView
										.getNameOfInputField();

								if (!newGroupName.isEmpty()) {
									navigationInputView.hide();
									GroupDTO group = (GroupDTO) dropTreeItem
											.getUserObject();

									UserDTO user = Session.getCurrentUser();
									noteService.createGroupForUser(
											user.getId(),
											group.getKey(),
											newGroupName,
											new LoadingScreenCallback<GroupDTO>(
													event) {

												@Override
												protected void success(
														GroupDTO newGroup) {
													view.addGroupToTree(
															dropTreeItem,
															newGroup);
												}
											});
								}
							}
						});
				navigationInputView.getCancelInputButton().addClickHandler(
						new ClickHandler() {

							public void onClick(ClickEvent event) {
								navigationInputView.hide();
							}
						});
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
