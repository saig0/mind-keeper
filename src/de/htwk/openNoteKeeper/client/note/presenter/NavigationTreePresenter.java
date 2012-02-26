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
import de.htwk.openNoteKeeper.client.note.view.NavigationTreeViewImpl;
import de.htwk.openNoteKeeper.client.util.StatusScreenCallback;
import de.htwk.openNoteKeeper.client.util.StatusScreenCallback.Status;
import de.htwk.openNoteKeeper.client.widget.StatusArea;
import de.htwk.openNoteKeeper.client.widget.StatusPanel;
import de.htwk.openNoteKeeper.shared.GroupDTO;
import de.htwk.openNoteKeeper.shared.UserDTO;
import de.htwk.openNoteKeeper.shared.WhiteBoardDTO;

@Presenter(view = NavigationTreeViewImpl.class)
public class NavigationTreePresenter extends
		BasePresenter<NavigationTreeViewImpl, NoteEventBus> {

	@Inject
	private NoteServiceAsync noteService;

	@Inject
	private StatusArea statusArea;

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

		public TreeItem getSelectedTreeItem();

		public void addTreeItemToParent(TreeItem parent, TreeItem child);
	}

	@Override
	public void bind() {
		view.getAddGroupButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				if (view.hasSelectedGroup()) {
					GroupDTO group = view.getSelectedGroup();
					eventBus.showInputFieldForNewGroup(view, group);
				}
			}
		});

		view.getAddWhiteBoardButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				if (view.hasSelectedGroup()) {
					GroupDTO group = view.getSelectedGroup();
					eventBus.showInputFieldForNewWhiteBoard(view, group);
				}
			}
		});

		view.getRemoveButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				if (view.hasSelectedGroup()) {
					final GroupDTO group = view.getSelectedGroup();
					UserDTO user = Session.getCurrentUser();
					noteService.removeGroup(
							user.getId(),
							group.getKey(),
							new StatusScreenCallback<Void>(Status.Remove_Group) {

								@Override
								protected void success(Void result) {
									view.removeSelectedGroup();

									statusArea
											.addStatusMessage(new StatusPanel(
													"Gruppe: "
															+ group.getTitle()
															+ " gelöscht",
													true, 5));
								}
							});
				} else if (view.hasSelectedWhiteBoard()) {
					final WhiteBoardDTO whiteBoard = view
							.getSelectedWhiteBoard();
					noteService.removeWhiteBoard(whiteBoard.getKey(),
							new StatusScreenCallback<Void>(
									Status.Remove_Whiteboard) {

								@Override
								protected void success(Void result) {
									view.removeSelectedWhiteBoard();

									statusArea
											.addStatusMessage(new StatusPanel(
													"Whiteboard: "
															+ whiteBoard
																	.getTitle()
															+ " gelöscht",
													true, 5));
								}
							});
				}
			}
		});

		view.getDropHandler().addTreeDropHandler(new TreeDropHandler() {

			public void onTreeDrop(final TreeItem dropTarget,
					final TreeItem sourceItem) {
				if (sourceItem.getUserObject() instanceof GroupDTO) {
					GroupDTO group = (GroupDTO) sourceItem.getUserObject();

					final TreeItem targetItem = dropTarget.getParentItem();
					GroupDTO targetGroup = (GroupDTO) targetItem
							.getUserObject();
					final int index = targetItem.getChildIndex(dropTarget);

					noteService.moveGroup(
							Session.getCurrentUser().getId(),
							group.getKey(),
							targetGroup.getKey(),
							index + 1,
							new StatusScreenCallback<Void>("verschiebe Gruppe") {

								@Override
								protected void success(Void result) {
									eventBus.loggedIn(Session.getCurrentUser());
								}
							});
				}
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
