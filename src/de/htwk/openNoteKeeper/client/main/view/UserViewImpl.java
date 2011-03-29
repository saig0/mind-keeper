package de.htwk.openNoteKeeper.client.main.view;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;

import de.htwk.openNoteKeeper.client.main.presenter.UserPresenter.UserView;
import de.htwk.openNoteKeeper.client.util.IconPool;
import de.htwk.openNoteKeeper.shared.UserDTO;

public class UserViewImpl implements UserView {

	private final class RedirectClickHandler implements ClickHandler {
		private String url = "/";

		private void setUrl(String url) {
			this.url = url;
		}

		public void onClick(ClickEvent event) {
			Window.open(url, "", "");
		}
	}

	private HLayout loginPanel = new HLayout();
	private HLayout logoutPanel = new HLayout();

	private RedirectClickHandler loginClickHandler = new RedirectClickHandler();
	private RedirectClickHandler logoutClickHandler = new RedirectClickHandler();

	private Label userLabel = new Label();

	public Widget asWidget() {
		Layout main = new HLayout();
		main.setWidth("300px");

		createLoginPanel();
		main.addMember(loginPanel);

		createLogoutPanel();
		logoutPanel.setVisible(false);
		main.addMember(logoutPanel);

		return main;
	}

	private void createLogoutPanel() {
		logoutPanel.setAlign(Alignment.RIGHT);
		logoutPanel.setMembersMargin(20);

		userLabel.setValign(VerticalAlignment.TOP);
		userLabel.setBaseStyle("big");
		logoutPanel.addMember(userLabel);

		IButton logoutButton = new IButton("Abmelden");
		logoutButton.setTitleStyle("bold");
		logoutButton.addClickHandler(logoutClickHandler);
		logoutPanel.addMember(logoutButton);
	}

	private void createLoginPanel() {
		loginPanel.setAlign(Alignment.RIGHT);

		IButton loginButton = new IButton("Anmelden über");
		loginButton.setTitleStyle("bold");
		loginButton.setSize("225px", "40px");
		loginButton.setIconHeight(30);
		loginButton.setIconWidth(100);
		loginButton.setIconOrientation("right");
		loginButton.setIconSpacing(10);
		loginButton.setIcon(IconPool.Google.getUrl());
		loginButton.addClickHandler(loginClickHandler);
		loginPanel.addMember(loginButton);
	}

	public void setLoginUrl(final String loginUrl) {
		loginClickHandler.setUrl(loginUrl);
		showLoginPanel();
	}

	private void showLoginPanel() {
		loginPanel.setVisible(true);
		logoutPanel.setVisible(false);
	}

	public void setLogoutUrl(final String logoutUrl) {
		logoutClickHandler.setUrl(logoutUrl);
		showLogoutPanel();
	}

	private void showLogoutPanel() {
		logoutPanel.setVisible(true);
		loginPanel.setVisible(false);
	}

	public void setUser(UserDTO user) {
		userLabel.setContents(user.getNick());
		showLogoutPanel();
	}

}
