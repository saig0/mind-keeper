package de.htwk.openNoteKeeper.client.main.view;

import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.impl.ImageResourcePrototype;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.htwk.openNoteKeeper.client.i18n.CommonConstants;
import de.htwk.openNoteKeeper.client.main.presenter.UserPresenter.UserView;
import de.htwk.openNoteKeeper.client.util.IconPool;
import de.htwk.openNoteKeeper.client.util.RedirectClickHandler;
import de.htwk.openNoteKeeper.shared.OpenIdProvider;
import de.htwk.openNoteKeeper.shared.UserDTO;

@Singleton
public class UserViewImpl implements UserView {

	@Inject
	private CommonConstants constants = GWT.create(CommonConstants.class);

	private DisclosurePanel main = new DisclosurePanel(
			new ImageResourcePrototype("", IconPool.Arrow_Down_Light.getUrl(),
					0, 0, 16, 16, true, false), new ImageResourcePrototype("",
					IconPool.Arrow_Right_Light.getUrl(), 0, 0, 16, 16, true,
					false), constants.signIn());

	public Widget asWidget() {
		main.setAnimationEnabled(true);
		return main;
	}

	public void showLoginForOpenIdProviders(
			Map<OpenIdProvider, String> openIdProviders) {
		main.getHeaderTextAccessor().setText(constants.signIn());
		VerticalPanel content = new VerticalPanel();
		for (OpenIdProvider openIdProvider : openIdProviders.keySet()) {
			String providerUrl = openIdProviders.get(openIdProvider);
			Image providerIcon = IconPool.Google_Logo.createImage();
			providerIcon.setTitle(openIdProvider.name());
			PushButton providerButton = new PushButton(providerIcon);
			providerButton
					.addClickHandler(new RedirectClickHandler(providerUrl));
			content.add(providerButton);
		}
		main.setContent(content);
	}

	public void showLogout(String logoutUrl) {
		VerticalPanel content = new VerticalPanel();
		PushButton providerButton = new PushButton(constants.signOut());
		providerButton.addClickHandler(new RedirectClickHandler(logoutUrl));
		content.add(providerButton);
		main.setContent(content);
	}

	public void showUserData(UserDTO user) {
		main.getHeaderTextAccessor().setText(user.getNick());
	}

}
