package de.htwk.openNoteKeeper.client.note.presenter.navigation;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

import de.htwk.openNoteKeeper.client.main.presenter.Session;
import de.htwk.openNoteKeeper.client.note.NoteEventBus;
import de.htwk.openNoteKeeper.client.note.service.NoteServiceAsync;
import de.htwk.openNoteKeeper.client.note.view.navigation.TreeItemCreationViewImpl;
import de.htwk.openNoteKeeper.client.util.LoadingScreenCallback;
import de.htwk.openNoteKeeper.shared.GroupDTO;
import de.htwk.openNoteKeeper.shared.WhiteBoardDTO;

@Presenter(view = TreeItemCreationViewImpl.class)
public class TreeItemCreationPresenter extends
		BasePresenter<TreeItemCreationViewImpl, NoteEventBus> {

	@Inject
	private NoteServiceAsync noteService;

	private GroupDTO group;
	private WhiteBoardDTO whiteBoard;

	public interface NavigationTreeItemCreationView extends IsWidget {
		public void showForGroupEdit();

		public void showForWhiteboardEdit();

		public void hide();

		public String getName();

		public void setName(String name);

		public HasClickHandlers getCreateButton();

		public HasClickHandlers getAbortButton();
	}

	@Override
	public void bind() {
		view.getCreateButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				String name = view.getName();
				if (!name.isEmpty()) {
					String userKey = Session.getCurrentUser().getId();
					if (group != null) {
						group.setTitle(name);
						noteService.updateGroup(userKey, group,
								new LoadingScreenCallback<Void>(event) {

									@Override
									protected void success(Void result) {
										view.hide();
										// TODO Modell auf Client
										// aktualliesieren
										eventBus.loggedIn(Session
												.getCurrentUser());
									}

								});
					} else if (whiteBoard != null) {
						whiteBoard.setTitle(name);
						noteService.updateWhiteBoard(userKey, whiteBoard,
								new LoadingScreenCallback<Void>(event) {

									@Override
									protected void success(Void result) {
										view.hide();
										// TODO Modell auf Client
										// aktualliesieren
										eventBus.loggedIn(Session
												.getCurrentUser());

									}
								});
					}
				}
			}
		});

		view.getAbortButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				view.hide();
			}
		});
	}

	public void onShowGroupEditView(GroupDTO group) {
		this.group = group;
		this.whiteBoard = null;
		view.setName(group.getTitle());
		view.showForGroupEdit();
	}

	public void onShowWhiteboardEditView(WhiteBoardDTO whiteBoard) {
		this.whiteBoard = whiteBoard;
		this.group = null;
		view.setName(whiteBoard.getTitle());
		view.showForWhiteboardEdit();
	}
}
