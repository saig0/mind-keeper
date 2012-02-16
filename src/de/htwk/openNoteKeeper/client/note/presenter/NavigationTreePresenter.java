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

import de.htwk.openNoteKeeper.client.note.NoteEventBus;
import de.htwk.openNoteKeeper.client.note.presenter.HasTreeDropHandler.TreeDropHandler;
import de.htwk.openNoteKeeper.client.note.presenter.SaveInputClickHandler.Type;
import de.htwk.openNoteKeeper.client.note.service.NoteServiceAsync;
import de.htwk.openNoteKeeper.client.note.view.NavigationInputWidget;
import de.htwk.openNoteKeeper.client.note.view.NavigationTreeViewImpl;
import de.htwk.openNoteKeeper.client.util.IconPool;
import de.htwk.openNoteKeeper.client.util.StatusScreenCallback;
import de.htwk.openNoteKeeper.client.util.StatusScreenCallback.Status;
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

		public void addGroupToTree(GroupDTO group);

		public void addWhiteBoardToGroup(WhiteBoardDTO whiteboard);

		public HasClickHandlers getRemoveButton();

		public HasClickHandlers getAddGroupButton();

		public HasClickHandlers getAddWhiteBoardButton();

		public void removeSelectedGroup();

		public void removeSelectedWhiteBoard();

		public TreeItem getDragWidget();

		public TreeItem getSelectedTreeItem();
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
		view.getAddGroupButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				if (view.hasSelectedGroup()) {
					GroupDTO group = view.getSelectedGroup();
					final NavigationInputView navigationInputView = new NavigationInputWidget(
							IconPool.Folder_Big.createImage());
					navigationInputView.getSaveInputButton().addClickHandler(
							new SaveInputClickHandler(noteService, view,
									navigationInputView, group, Type.Group));
					navigationInputView.getCancelInputButton().addClickHandler(
							new CancelInputClickHandler(navigationInputView));
					navigationInputView.show(view.getSelectedTreeItem());
				}
			}
		});

		view.getAddWhiteBoardButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				if (view.hasSelectedGroup()) {
					GroupDTO group = view.getSelectedGroup();
					final NavigationInputView navigationInputView = new NavigationInputWidget(
							IconPool.Blank_Sheet_Big.createImage());
					navigationInputView.getSaveInputButton()
							.addClickHandler(
									new SaveInputClickHandler(noteService,
											view, navigationInputView, group,
											Type.WhiteBoard));
					navigationInputView.getCancelInputButton().addClickHandler(
							new CancelInputClickHandler(navigationInputView));
					navigationInputView.show(view.getSelectedTreeItem());
				}
			}
		});

		view.getRemoveButton().addClickHandler(
				new RemoveItemClickHandler(view, noteService));

		view.getDropHandler().addTreeDropHandler(new TreeDropHandler() {

			public void onTreeDrop(TreeItem dropTarget, TreeItem sourceItem) {
				// TODO service call

			}

		});
	}

	public void onLoggedIn(UserDTO user) {

		noteService.getAllGroupsForUser(user.getId(),
				new StatusScreenCallback<List<GroupDTO>>(Status.Load_Notes) {

					@Override
					protected void success(List<GroupDTO> groups) {
						view.setGroups(groups);
					}
				});
	}

}
