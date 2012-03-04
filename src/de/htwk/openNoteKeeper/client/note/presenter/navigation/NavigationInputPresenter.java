package de.htwk.openNoteKeeper.client.note.presenter.navigation;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.inject.Inject;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

import de.htwk.openNoteKeeper.client.main.presenter.Session;
import de.htwk.openNoteKeeper.client.note.NoteEventBus;
import de.htwk.openNoteKeeper.client.note.presenter.navigation.NavigationTreePresenter.NavigationTreeView;
import de.htwk.openNoteKeeper.client.note.service.NoteServiceAsync;
import de.htwk.openNoteKeeper.client.note.view.navigation.NavigationInputViewImpl;
import de.htwk.openNoteKeeper.client.util.IconPool;
import de.htwk.openNoteKeeper.client.util.StatusScreenCallback;
import de.htwk.openNoteKeeper.client.util.StatusScreenCallback.Status;
import de.htwk.openNoteKeeper.shared.AccessRole;
import de.htwk.openNoteKeeper.shared.GroupDTO;
import de.htwk.openNoteKeeper.shared.UserDTO;
import de.htwk.openNoteKeeper.shared.WhiteBoardDTO;

@Presenter(view = NavigationInputViewImpl.class)
public class NavigationInputPresenter extends
		BasePresenter<NavigationInputViewImpl, NoteEventBus> {

	public interface NavigationInputView {
		public HasClickHandlers getCancelInputButton();

		public HasClickHandlers getSaveInputButton();

		public String getNameOfInputField();

		public void show(TreeItem selectedGroup);

		public void hide();

		public Image getIcon();

		public TreeItem asTreeItem();
	}

	@Inject
	private NoteServiceAsync noteService;

	private HandlerRegistration handlerRegistration;

	@Override
	public void bind() {
		view.getCancelInputButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				view.hide();
			}
		});
	}

	public void onShowInputFieldForNewGroup(
			final NavigationTreeView navigationTreeView,
			final GroupDTO selectedGroup) {
		view.getIcon().setUrl(IconPool.Folder_Big.getUrl());

		if (handlerRegistration != null) {
			handlerRegistration.removeHandler();
		}
		handlerRegistration = view.getSaveInputButton().addClickHandler(
				new ClickHandler() {

					public void onClick(ClickEvent event) {
						String newGroupName = view.getNameOfInputField();

						if (!newGroupName.isEmpty()) {
							view.hide();

							UserDTO user = Session.getCurrentUser();
							noteService.createGroupForUser(user.getId(),
									selectedGroup.getKey(), newGroupName,
									new StatusScreenCallback<GroupDTO>(
											Status.Add_Group) {

										@Override
										protected void success(GroupDTO newGroup) {
											eventBus.loggedIn(Session
													.getCurrentUser());
										}
									});
						}
					}
				});

		showView(navigationTreeView, new GroupDTO("dummy key", "dummy",
				AccessRole.Read));
	}

	private void showView(final NavigationTreeView navigationTreeView,
			Object userObject) {
		TreeItem item = view.asTreeItem();
		item.setUserObject(userObject);
		navigationTreeView.addTreeItemToParent(
				navigationTreeView.getSelectedTreeItem(), item);
		view.show(navigationTreeView.getSelectedTreeItem());
	}

	public void onShowInputFieldForNewWhiteBoard(
			final NavigationTreeView navigationTreeView,
			final GroupDTO selectedGroup) {
		view.getIcon().setUrl(IconPool.Blank_Sheet_Big.getUrl());

		if (handlerRegistration != null) {
			handlerRegistration.removeHandler();
		}
		handlerRegistration = view.getSaveInputButton().addClickHandler(
				new ClickHandler() {

					public void onClick(ClickEvent event) {
						String newGroupName = view.getNameOfInputField();

						if (!newGroupName.isEmpty()) {
							view.hide();

							noteService.createWhiteBoard(
									selectedGroup.getKey(), newGroupName,
									new StatusScreenCallback<WhiteBoardDTO>(
											Status.Add_Whiteboard) {

										@Override
										protected void success(
												WhiteBoardDTO whiteboard) {
											eventBus.loggedIn(Session
													.getCurrentUser());
										}
									});
						}
					}
				});

		showView(navigationTreeView, new WhiteBoardDTO("dummy key", "dummy"));
	}
}
