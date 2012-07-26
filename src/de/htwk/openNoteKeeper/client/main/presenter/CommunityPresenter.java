package de.htwk.openNoteKeeper.client.main.presenter;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.MenuItem;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

import de.htwk.openNoteKeeper.client.main.MainEventBus;
import de.htwk.openNoteKeeper.client.main.view.CommunityViewImpl;

@Presenter(view = CommunityViewImpl.class)
public class CommunityPresenter extends
		BasePresenter<CommunityViewImpl, MainEventBus> {

	private static final String ISSUE_TRACKER_URL = "https://bitbucket.org/saigo/mind-keeper/issues";

	public interface CommunityView {
		public MenuItem asMenuItem();

		public void setCommand(Command command);
	}

	@Override
	public void bind() {
		view.setCommand(new Command() {

			public void execute() {
				Window.open(ISSUE_TRACKER_URL, "_blank", "");
			}
		});
	}

	public void onStart() {
	}
}
