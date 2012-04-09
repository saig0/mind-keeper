package de.htwk.openNoteKeeper.client.main.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;

import de.htwk.openNoteKeeper.client.i18n.CommonConstants;
import de.htwk.openNoteKeeper.client.main.presenter.CommunityPresenter.CommunityView;
import de.htwk.openNoteKeeper.client.util.RedirectClickHandler;

@Singleton
public class CommunityViewImpl implements CommunityView {

	private CommonConstants constants = GWT.create(CommonConstants.class);

	private static final String ISSUE_TRACKER_URL = "https://bitbucket.org/saigo/mind-keeper/issues";

	private final Widget main;
	private Button issueTrackerButton;

	public CommunityViewImpl() {
		main = createView();
	}

	private Widget createView() {
		HorizontalPanel panel = new HorizontalPanel();
		panel.setSpacing(10);

		issueTrackerButton = new Button(constants.issueTracker());
		issueTrackerButton.addClickHandler(new RedirectClickHandler(
				ISSUE_TRACKER_URL, true));
		panel.add(issueTrackerButton);

		return panel;
	}

	public Widget asWidget() {
		return main;
	}

	public HasClickHandlers getIssueTrackerButton() {
		return issueTrackerButton;
	}

}
