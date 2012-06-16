package de.htwk.openNoteKeeper.client.note.presenter.actionBar;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

import de.htwk.openNoteKeeper.client.note.NoteEventBus;
import de.htwk.openNoteKeeper.client.note.view.actionBar.NoteFindViewImpl;
import de.htwk.openNoteKeeper.shared.GroupDTO;
import de.htwk.openNoteKeeper.shared.NoteDTO;
import de.htwk.openNoteKeeper.shared.WhiteBoardDTO;

@Presenter(view = NoteFindViewImpl.class)
public class NoteFindPresenter extends
		BasePresenter<NoteFindViewImpl, NoteEventBus> {

	public interface NoteFindView extends IsWidget {
		public HasSelectionHandlers<Suggestion> getSuggestionField();

		public SuggestBox getSuggestionBox();

		public HasClickHandlers getFindButton();

		public void setSuggestions(List<String> suggestions);
	}

	private Map<String, String> suggestionToWhiteboardKeyMapping = new HashMap<String, String>();

	@Override
	public void bind() {
		view.getSuggestionField().addSelectionHandler(
				new SelectionHandler<Suggestion>() {

					public void onSelection(SelectionEvent<Suggestion> event) {
						String suggestion = event.getSelectedItem()
								.getReplacementString();
						String whiteboardKey = suggestionToWhiteboardKeyMapping
								.get(suggestion);
						eventBus.showWhiteboard(whiteboardKey);
					}
				});

		view.getFindButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				view.getSuggestionBox().showSuggestionList();
			}
		});
	}

	public void onShowGroupsForUser(List<GroupDTO> groups) {
		suggestionToWhiteboardKeyMapping.clear();
		List<String> suggestions = getSuggestionsOfGroups(groups);
		view.setSuggestions(suggestions);
	}

	private List<String> getSuggestionsOfGroups(List<GroupDTO> groups) {
		List<String> suggestions = new LinkedList<String>();
		for (GroupDTO group : groups) {
			for (WhiteBoardDTO whiteboard : group.getWhiteBoards()) {
				for (NoteDTO note : whiteboard.getNotes()) {
					String suggestion = note.getTitle() + " ("
							+ whiteboard.getTitle() + ")";
					suggestions.add(suggestion);

					suggestionToWhiteboardKeyMapping.put(suggestion,
							whiteboard.getKey());
				}
			}
			List<String> suggestionsOfSubGroups = getSuggestionsOfGroups(group
					.getSubGroups());
			suggestions.addAll(suggestionsOfSubGroups);
		}
		return suggestions;
	}
}
