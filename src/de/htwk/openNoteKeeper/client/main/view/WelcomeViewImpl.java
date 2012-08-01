package de.htwk.openNoteKeeper.client.main.view;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import de.htwk.openNoteKeeper.client.i18n.WelcomeConstants;
import de.htwk.openNoteKeeper.client.main.presenter.WelcomePresenter.WelcomeView;
import de.htwk.openNoteKeeper.client.util.IconPool;

public class WelcomeViewImpl implements WelcomeView {

	@Inject
	private WelcomeConstants constants;

	public Widget asWidget() {
		AbsolutePanel main = new AbsolutePanel();
		main.setSize("100%", "100%");

		main.add(
				createWelcomePanel(IconPool.Online_Notes.getUrl(),
						constants.onlineNotes()), 50, 100);

		main.add(
				createWelcomePanel(IconPool.Note_Hierarchy.getUrl(),
						constants.noteHierarchy()), 25, 400);

		main.add(
				createSmallWelcomePanel(IconPool.Note_Search.getUrl(),
						constants.findNotes()), 700, 50);
		main.add(
				createSmallWelcomePanel(IconPool.Editor_Config.getUrl(),
						constants.configureEditor()), 675, 250);
		main.add(
				createSmallWelcomePanel(IconPool.Note_Security.getUrl(),
						constants.security()), 725, 450);

		return main;
	}

	private HorizontalPanel createWelcomePanel(String url, String text) {
		HorizontalPanel panel = new HorizontalPanel();
		panel.setSize("600px", "500px");
		panel.add(new Image(url));
		Label label = new Label(text);
		label.addStyleName("welcome");
		panel.add(label);
		return panel;
	}

	private HorizontalPanel createSmallWelcomePanel(String url, String text) {
		HorizontalPanel panel = new HorizontalPanel();
		panel.setSize("400px", "300px");
		Image image = new Image(url);
		image.setWidth("150px");
		panel.add(image);
		Label label = new Label(text);
		label.addStyleName("welcome-small");
		panel.add(label);
		return panel;
	}

}
