package de.htwk.openNoteKeeper.client.note.presenter.navigation;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.allen_sauer.gwt.dnd.client.DragController;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.inject.Inject;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

import de.htwk.openNoteKeeper.client.main.presenter.Session;
import de.htwk.openNoteKeeper.client.note.NoteEventBus;
import de.htwk.openNoteKeeper.client.note.presenter.navigation.HasTreeDropHandler.TreeDropHandler;
import de.htwk.openNoteKeeper.client.note.presenter.navigation.TreeItemPresenter.NavigationTreeItemView;
import de.htwk.openNoteKeeper.client.note.service.NoteServiceAsync;
import de.htwk.openNoteKeeper.client.note.view.navigation.NavigationTreeViewImpl;
import de.htwk.openNoteKeeper.client.util.IconPool;
import de.htwk.openNoteKeeper.client.util.PresenterFactory;
import de.htwk.openNoteKeeper.client.util.StatusScreenCallback;
import de.htwk.openNoteKeeper.client.util.StatusScreenCallback.Status;
import de.htwk.openNoteKeeper.client.widget.ConfirmationPopup;
import de.htwk.openNoteKeeper.client.widget.ConfirmationPopup.SaveAction;
import de.htwk.openNoteKeeper.client.widget.StatusArea;
import de.htwk.openNoteKeeper.client.widget.StatusPanel;
import de.htwk.openNoteKeeper.shared.GroupDTO;
import de.htwk.openNoteKeeper.shared.UserDTO;
import de.htwk.openNoteKeeper.shared.WhiteBoardDTO;

;

@Presenter(view = NavigationTreeViewImpl.class)
public class NavigationTreePresenter extends
		BasePresenter<NavigationTreeViewImpl, NoteEventBus> {

	@Inject
	private NoteServiceAsync noteService;

	@Inject
	private StatusArea statusArea;

	private Map<String, TreeItem> whiteboardKeyToTreeItemMapper = new HashMap<String, TreeItem>();

	public interface NavigationTreeView extends IsWidget {
		public void setGroups(List<TreeItem> groupItems);

		public void addTreeItemAndSetStyle(TreeItem parent, TreeItem child);

		public DragController getDragController();

		public HasTreeDropHandler getDropHandler();

		public boolean hasSelectedGroup();

		public boolean hasSelectedWhiteBoard();

		public GroupDTO getSelectedGroup();

		public WhiteBoardDTO getSelectedWhiteBoard();

		public HasClickHandlers getRemoveButton();

		public HasClickHandlers getAddGroupButton();

		public HasClickHandlers getAddWhiteBoardButton();

		public void removeSelectedGroup();

		public void removeSelectedWhiteBoard();

		public TreeItem getSelectedTreeItem();

		public void addTreeItemToParent(TreeItem parent, TreeItem child);

		public HasSelectionHandlers<TreeItem> getNavigationTree();

		public void selectTreeItem(TreeItem item);
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
					new ConfirmationPopup("Gruppe " + group.getTitle()
							+ " löschen?", new SaveAction() {

						public void run() {
							UserDTO user = Session.getCurrentUser();
							noteService.removeGroup(user.getId(), group
									.getKey(), new StatusScreenCallback<Void>(
									Status.Remove_Group) {

								@Override
								protected void success(Void result) {
									TreeItem parentItem = view
											.getSelectedTreeItem()
											.getParentItem();
									view.removeSelectedGroup();
									view.selectTreeItem(parentItem);

									statusArea
											.addStatusMessage(new StatusPanel(
													"Gruppe: "
															+ group.getTitle()
															+ " gelöscht",
													true, 5));
								}
							});
						}

					}).show();
				} else if (view.hasSelectedWhiteBoard()) {
					final WhiteBoardDTO whiteBoard = view
							.getSelectedWhiteBoard();
					new ConfirmationPopup("Whiteboard " + whiteBoard.getTitle()
							+ " löschen?", new SaveAction() {

						public void run() {
							noteService.removeWhiteBoard(whiteBoard.getKey(),
									new StatusScreenCallback<Void>(
											Status.Remove_Whiteboard) {

										@Override
										protected void success(Void result) {
											TreeItem parentItem = view
													.getSelectedTreeItem()
													.getParentItem();
											view.removeSelectedWhiteBoard();
											view.selectTreeItem(parentItem);

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
					}).show();
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
					final int index = targetItem.getChildIndex(dropTarget) + 1;

					noteService.moveGroup(group.getKey(), targetGroup.getKey(),
							index, new StatusScreenCallback<Void>(
									"verschiebe Gruppe") {

								@Override
								protected void success(Void result) {
									// TODO Modell auf Client aktualliesieren
									eventBus.loggedIn(Session.getCurrentUser());
								}
							});
				} else if (sourceItem.getUserObject() instanceof WhiteBoardDTO) {
					WhiteBoardDTO whiteBoard = (WhiteBoardDTO) sourceItem
							.getUserObject();
					GroupDTO targetGroup = getTargetGroup(dropTarget);
					int index = getIndex(dropTarget);

					noteService.moveWhiteBoard(whiteBoard.getKey(), targetGroup
							.getKey(), index, new StatusScreenCallback<Void>(
							"verschiebe WhiteBoard") {

						@Override
						protected void success(Void result) {
							// TODO Modell auf Client aktualliesieren
							eventBus.loggedIn(Session.getCurrentUser());
						}
					});
				}
			}

			private GroupDTO getTargetGroup(TreeItem dropTarget) {
				if (dropTarget.getUserObject() instanceof WhiteBoardDTO) {
					final TreeItem targetItem = dropTarget.getParentItem();
					return (GroupDTO) targetItem.getUserObject();
				} else {
					return (GroupDTO) dropTarget.getUserObject();
				}
			}

			private int getIndex(TreeItem dropTarget) {
				if (dropTarget.getUserObject() instanceof WhiteBoardDTO) {
					final TreeItem targetItem = dropTarget.getParentItem();
					int index = 0;
					for (int i = 0; i < targetItem.getChildCount(); i++) {
						TreeItem item = targetItem.getChild(i);
						if (item.equals(dropTarget)) {
							return index + 1;
						} else if (item.getUserObject() instanceof WhiteBoardDTO) {
							index += 1;
						}
					}
					return index;
				} else {
					return dropTarget.getChildCount();
				}
			}

		});

		view.getNavigationTree().addSelectionHandler(
				new SelectionHandler<TreeItem>() {

					public void onSelection(SelectionEvent<TreeItem> event) {
						eventBus.hideEditor();

						TreeItem selectedItem = event.getSelectedItem();
						Object userObject = selectedItem.getUserObject();
						if (selectedItem != null && userObject != null) {
							if (userObject instanceof GroupDTO
									&& !((GroupDTO) userObject).getKey()
											.contains("dummy")) {
								eventBus.selectGroup((GroupDTO) userObject);
							} else if (userObject instanceof WhiteBoardDTO
									&& !((WhiteBoardDTO) userObject).getKey()
											.contains("dummy")) {
								eventBus.selectWhiteBoard((WhiteBoardDTO) userObject);
							}
						}
					}
				});
	}

	// TODO Refactoring
	public void onLoggedIn(UserDTO user) {
		noteService.getAllGroupsForUser(user.getId(),
				new StatusScreenCallback<List<GroupDTO>>(Status.Load_Notes) {

					@Override
					protected void success(List<GroupDTO> groups) {
						eventBus.showGroupsForUser(groups);
					}
				});
	}

	public void onShowGroupsForUser(List<GroupDTO> groups) {
		whiteboardKeyToTreeItemMapper.clear();
		List<TreeItem> groupItems = new LinkedList<TreeItem>();
		for (GroupDTO group : groups) {
			TreeItem groupItem = createTreeItem(group);
			groupItems.add(groupItem);
		}
		view.setGroups(groupItems);
	}

	private TreeItem createTreeItem(GroupDTO group) {
		TreeItem parent = createGroupTreeItem(group);
		for (GroupDTO childGroup : group.getSubGroups()) {
			final TreeItem groupItem = createTreeItem(childGroup);
			view.addTreeItemAndSetStyle(parent, groupItem);
		}
		for (WhiteBoardDTO whiteBoard : group.getWhiteBoards()) {
			TreeItem whiteBoardItem = createWhiteBoardTreeItem(whiteBoard);
			view.addTreeItemAndSetStyle(parent, whiteBoardItem);
			whiteboardKeyToTreeItemMapper.put(whiteBoard.getKey(),
					whiteBoardItem);
		}
		return parent;
	}

	private TreeItem createGroupTreeItem(GroupDTO group) {
		TreeItemPresenter treeItemPresenter = PresenterFactory.createPresenter(
				eventBus, TreeItemPresenter.class);
		NavigationTreeItemView navigationTreeItem = treeItemPresenter
				.onShowTreeItem(IconPool.Folder_Big.getUrl(), group.getTitle(),
						group);
		TreeItem item = navigationTreeItem.asTreeItem();
		view.getDragController().makeDraggable(navigationTreeItem.asWidget(),
				navigationTreeItem.getDragHandle());
		return item;
	}

	private TreeItem createWhiteBoardTreeItem(WhiteBoardDTO whiteBoard) {
		TreeItemPresenter treeItemPresenter = PresenterFactory.createPresenter(
				eventBus, TreeItemPresenter.class);
		NavigationTreeItemView navigationTreeItem = treeItemPresenter
				.onShowTreeItem(IconPool.Blank_Sheet_Big.getUrl(),
						whiteBoard.getTitle(), whiteBoard);
		view.getDragController().makeDraggable(navigationTreeItem.asWidget(),
				navigationTreeItem.getDragHandle());
		return navigationTreeItem.asTreeItem();
	}

	public void onShowWhiteboard(String whiteboardKey) {
		if (whiteboardKeyToTreeItemMapper.containsKey(whiteboardKey)) {
			TreeItem item = whiteboardKeyToTreeItemMapper.get(whiteboardKey);
			view.selectTreeItem(item);
		}
	}
}
