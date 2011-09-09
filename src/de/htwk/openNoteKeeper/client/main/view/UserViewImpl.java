package de.htwk.openNoteKeeper.client.main.view;

import java.util.Map;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;

import de.htwk.openNoteKeeper.client.main.presenter.UserPresenter.UserView;
import de.htwk.openNoteKeeper.client.util.RedirectClickHandler;
import de.htwk.openNoteKeeper.shared.OpenIdProvider;
import de.htwk.openNoteKeeper.shared.UserDTO;

@Singleton
public class UserViewImpl implements UserView {

	private FlowPanel providerPanel = new FlowPanel();
	private FlowPanel userPanel = new FlowPanel();

	public Widget asWidget() {
		DockLayoutPanel layout = new DockLayoutPanel(Unit.PX);
		layout.setSize("100%", "100%");
		layout.addEast(providerPanel, 100);
		layout.add(userPanel);
		return layout;
	}

	public void showLoginForOpenIdProviders(
			Map<OpenIdProvider, String> openIdProviders) {
		userPanel.clear();
		providerPanel.clear();
		for (OpenIdProvider openIdProvider : openIdProviders.keySet()) {
			String providerUrl = openIdProviders.get(openIdProvider);
			Image providerIcon = openIdProvider.getIcon().createImage();
			providerIcon.setAltText(openIdProvider.name());
			PushButton providerButton = new PushButton(providerIcon);
			providerButton
					.addClickHandler(new RedirectClickHandler(providerUrl));
			providerPanel.add(providerButton);
		}
	}

	public void showLogout(String logoutUrl) {
		providerPanel.clear();
		PushButton logoutButton = new PushButton("Logout");
		logoutButton.addClickHandler(new RedirectClickHandler(logoutUrl));
		providerPanel.add(logoutButton);
	}

	public void showUserData(UserDTO user) {
		Label userLabel = new Label(user.getNick());
		userPanel.add(userLabel);
	}

}
