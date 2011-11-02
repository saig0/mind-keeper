package de.htwk.openNoteKeeper.client.note.presenter;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasBlurHandlers;
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

@Presenter(view = NavigationTreeViewImpl.class)
public class NavigationTreePresenter extends
		BasePresenter<NavigationTreeViewImpl, NoteEventBus> {

	@Inject
	private NoteServiceAsync noteService;

	public interface NavigationTreeView extends IsWidget {
		public void setGroups(List<GroupDTO> groups);

		public HasClickHandlers getCreateNewGroupButton();

		public boolean hasSelectedGroup();

		public void showNewGroupNameField();

		public HasBlurHandlers getNewGroupNameField();

		public HasClickHandlers getCancelNewGroupButton();

		public HasClickHandlers getSaveNewGroupButton();

		public String getNewGroupName();

		public GroupDTO getSelectedGroup();

		public void addGroupToSelected(GroupDTO group);

		public void hideNewGroupField();

		public HasClickHandlers getRemoveGroupButton();

		public void removeSelectedGroup();
	}

	private UserDTO user;

	@Override
	public void bind() {
		view.getCreateNewGroupButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				if (view.hasSelectedGroup()) {
					view.showNewGroupNameField();
					view.getSaveNewGroupButton().addClickHandler(
							new ClickHandler() {

								public void onClick(ClickEvent event) {
									GroupDTO group = view.getSelectedGroup();
									String newGroupName = view
											.getNewGroupName();

									if (!newGroupName.isEmpty()) {
										view.hideNewGroupField();

										noteService.createGroupForUser(
												user.getId(),
												group.getKey(),
												newGroupName,
												new LoadingScreenCallback<GroupDTO>(
														event) {

													@Override
													protected void success(
															GroupDTO newGroup) {
														view.addGroupToSelected(newGroup);
													}
												});
									}
								}
							});
					view.getCancelNewGroupButton().addClickHandler(
							new ClickHandler() {

								public void onClick(ClickEvent event) {
									view.hideNewGroupField();
								}
							});
				}
			}
		});
		view.getRemoveGroupButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				if (view.hasSelectedGroup()) {
					GroupDTO group = view.getSelectedGroup();

					noteService.removeGroup(user.getId(), group.getKey(),
							new LoadingScreenCallback<Void>(event) {

								@Override
								protected void success(Void result) {
									view.removeSelectedGroup();
								}
							});
				}
			}
		});
	}

	public void onLoggedIn(UserDTO user) {
		this.user = user;

		noteService.getAllGroupsForUser(user.getId(),
				new LoadingScreenCallback<List<GroupDTO>>() {

					@Override
					protected void success(List<GroupDTO> groups) {
						view.setGroups(groups);
					}
				});
	}
}
