package de.htwk.openNoteKeeper.client.note.presenter.navigation;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

import de.htwk.openNoteKeeper.client.note.NoteEventBus;
import de.htwk.openNoteKeeper.client.note.view.navigation.TreeItemViewImpl;
import de.htwk.openNoteKeeper.shared.GroupDTO;
import de.htwk.openNoteKeeper.shared.WhiteBoardDTO;

@Presenter(view = TreeItemViewImpl.class, multiple = true)
public class TreeItemPresenter extends
		BasePresenter<TreeItemViewImpl, NoteEventBus> {

	public interface NavigationTreeItemView extends IsWidget {
		public void setIcon(String url);

		public void setText(String text);

		public void setUserObject(Object userObject);

		public Widget getDragHandle();

		public TreeItem asTreeItem();

		public HasClickHandlers getEditButton();

		public void hideContextMenu();
	}

	private Object userObject;

	@Override
	public void bind() {
		view.getEditButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				view.hideContextMenu();
				if (userObject instanceof GroupDTO) {
					eventBus.showGroupEditView((GroupDTO) userObject);
				} else if (userObject instanceof WhiteBoardDTO) {
					eventBus.showWhiteboardEditView((WhiteBoardDTO) userObject);
				}
			}
		});
	}

	public NavigationTreeItemView onShowTreeItem(String url, String text,
			Object userObject) {
		this.userObject = userObject;
		view.setIcon(url);
		view.setText(text);
		view.setUserObject(userObject);
		return view;
	}
}
