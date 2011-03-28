package de.htwk.openNoteKeeper.client.main.view;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;

import de.htwk.openNoteKeeper.client.main.presenter.UserPresenter.UserView;
import de.htwk.openNoteKeeper.shared.UserDTO;

public class UserViewImpl implements UserView {

	private HLayout loginPanel = new HLayout();
	private HLayout logoutPanel = new HLayout();

	public Widget asWidget() {
		Layout main = new VLayout();
		main.setSize("100%", "100%");

		main.addMember(loginPanel);
		return main;
	}

	public void setLoginUrl(String loginUrl) {
		// IButton loginButton = new IButton("Anmelden");
		// loginButton.setIcon(IconPool.Google.getUrl());
		// loginButton.addChild(new Anchor("Google SignOn", "url"));
		// loginPanel.addMember(loginButton);
		// for (Canvas member : main.getMembers())
		// main.removeMember(member);
		// main.addMember(new Anchor("Google SignOn", loginUrl));

		Layout container = new VLayout();
		container.setSize("100%", "100%");
		container.addMember(new Anchor("Google SignOn", loginUrl));
		loginPanel.setMembers(container);
	}

	public void setLogoutUrl(String logoutUrl) {
		// IButton loginButton = new IButton("Abmelden");
		// loginButton.addChild(new Anchor("Google SignOn", logoutUrl));
		// logoutPanel.addMember(loginButton);
		loginPanel.addMember(new Label(logoutUrl));

		logoutPanel.setVisible(true);
		loginPanel.setVertical(false);
	}

	public void setUser(UserDTO user) {
		Label userLabel = new Label(user.getNick());
		logoutPanel.addMember(userLabel);

		logoutPanel.setVisible(true);
		loginPanel.setVertical(false);
	}

}
