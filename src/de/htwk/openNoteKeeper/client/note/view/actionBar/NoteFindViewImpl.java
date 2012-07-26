package de.htwk.openNoteKeeper.client.note.view.actionBar;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;

import de.htwk.openNoteKeeper.client.note.i18n.NoteConstants;
import de.htwk.openNoteKeeper.client.note.presenter.actionBar.NoteFindPresenter.NoteFindView;
import de.htwk.openNoteKeeper.client.util.IconPool;

@Singleton
public class NoteFindViewImpl implements NoteFindView {

	private final Widget widget;
	private MultiWordSuggestOracle oracle;
	private SuggestBox suggestBox;
	private Image icon;

	private NoteConstants noteConstants = GWT.create(NoteConstants.class);

	public NoteFindViewImpl() {
		widget = createWidget();
	}

	private Widget createWidget() {
		HorizontalPanel main = new HorizontalPanel();
		main.setSize("100%", "100%");
		main.setSpacing(3);

		oracle = new MultiWordSuggestOracle(" ()");
		suggestBox = new SuggestBox(oracle);
		suggestBox.setWidth("150px");
		suggestBox.setTitle(noteConstants.findNotesField());
		main.add(suggestBox);

		icon = IconPool.Find.createImage();
		icon.setSize("24px", "24px");
		icon.addStyleName("clickable");
		icon.setTitle(noteConstants.findNotes());
		main.add(icon);

		return main;
	}

	public Widget asWidget() {
		return widget;
	}

	public void setSuggestions(List<String> suggestions) {
		oracle.clear();
		oracle.addAll(suggestions);
	}

	public HasSelectionHandlers<Suggestion> getSuggestionField() {
		return suggestBox;
	}

	public HasClickHandlers getFindButton() {
		return icon;
	}

	public SuggestBox getSuggestionBox() {
		return suggestBox;
	}
}
