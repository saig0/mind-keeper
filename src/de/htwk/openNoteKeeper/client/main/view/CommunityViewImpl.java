package de.htwk.openNoteKeeper.client.main.view;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.htwk.openNoteKeeper.client.i18n.CommonConstants;
import de.htwk.openNoteKeeper.client.main.presenter.CommunityPresenter.CommunityView;
import de.htwk.openNoteKeeper.client.util.IconPool;
import de.htwk.openNoteKeeper.client.widget.IconButton;

@Singleton
public class CommunityViewImpl implements CommunityView {

	@Inject
	private CommonConstants constants;
	private Command command;

	public MenuItem asMenuItem() {
		IconButton issue = new IconButton(
				IconPool.ConstructionArea.createImage(),
				constants.issueTracker());
		return new MenuItem(issue.getHTML(), true, command);
	}

	public void setCommand(Command command) {
		this.command = command;
	}

}
