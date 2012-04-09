package de.htwk.openNoteKeeper.client.main.presenter;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.IsWidget;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

import de.htwk.openNoteKeeper.client.main.MainEventBus;
import de.htwk.openNoteKeeper.client.main.view.CommunityViewImpl;
import de.htwk.openNoteKeeper.client.util.RedirectClickHandler;

@Presenter(view = CommunityViewImpl.class)
public class CommunityPresenter extends
		BasePresenter<CommunityViewImpl, MainEventBus> {

	private static final String ISSUE_TRACKER_URL = "https://bitbucket.org/saigo/mind-keeper/issues";

	public interface CommunityView extends IsWidget {
		HasClickHandlers getIssueTrackerButton();

	}

	@Override
	public void bind() {
		view.getIssueTrackerButton().addClickHandler(
				new RedirectClickHandler(ISSUE_TRACKER_URL, true));
	}

	public void onStart() {
	}
}
