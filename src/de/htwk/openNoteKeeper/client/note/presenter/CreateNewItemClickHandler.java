package de.htwk.openNoteKeeper.client.note.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import de.htwk.openNoteKeeper.client.note.presenter.NavigationTreePresenter.NavigationTreeView;
import de.htwk.openNoteKeeper.shared.GroupDTO;

public abstract class CreateNewItemClickHandler implements ClickHandler {

	protected final NavigationTreeView view;

	public CreateNewItemClickHandler(NavigationTreeView view) {
		this.view = view;
	}

	private final class CancelNewItemClickHandler implements ClickHandler {
		public void onClick(ClickEvent event) {
			view.hideInputField();
		}
	}

	private final class SaveNewItemClickHandler implements ClickHandler {
		private final GroupDTO group;

		private SaveNewItemClickHandler(GroupDTO group) {
			this.group = group;
		}

		public void onClick(ClickEvent event) {
			String newGroupName = view.getNameOfInputField();

			if (!newGroupName.isEmpty()) {
				view.hideInputField();

				serviceCall(group, event, newGroupName);
			}
		}
	}

	public void onClick(ClickEvent event) {
		if (view.hasSelectedGroup()) {
			GroupDTO group = view.getSelectedGroup();
			showItemInputField();
			view.getSaveInputButton().addClickHandler(
					new SaveNewItemClickHandler(group));
			view.getCancelInputButton().addClickHandler(
					new CancelNewItemClickHandler());
		}
	}

	protected abstract void showItemInputField();

	abstract void serviceCall(final GroupDTO group, ClickEvent event,
			String name);
}
