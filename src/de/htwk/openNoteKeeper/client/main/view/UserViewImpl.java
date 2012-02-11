package de.htwk.openNoteKeeper.client.main.view;

import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.htwk.openNoteKeeper.client.i18n.CommonConstants;
import de.htwk.openNoteKeeper.client.main.presenter.UserPresenter.UserView;
import de.htwk.openNoteKeeper.client.util.RedirectClickHandler;
import de.htwk.openNoteKeeper.client.widget.PopupPanel;
import de.htwk.openNoteKeeper.client.widget.PopupPanel.AnimationType;
import de.htwk.openNoteKeeper.shared.OpenIdProvider;
import de.htwk.openNoteKeeper.shared.UserDTO;

@Singleton
public class UserViewImpl implements UserView {

	@Inject
	private CommonConstants constants = GWT.create(CommonConstants.class);

	private Label userLabel = new Label();
	private Button actionButton = new Button(constants.signIn());

	private final Widget widget;

	public UserViewImpl() {
		widget = createLayout();
	}

	private Widget createLayout() {
		HorizontalPanel panel = new HorizontalPanel();
		panel.setSpacing(10);
		panel.add(userLabel);
		panel.add(actionButton);
		return panel;
	}

	public Widget asWidget() {
		return widget;
	}

	public void showLoginForOpenIdProviders(
			final Map<OpenIdProvider, String> openIdProviders) {
		actionButton.setText(constants.signIn());
		actionButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				HorizontalPanel content = new HorizontalPanel();
				content.setSpacing(5);
				for (OpenIdProvider openIdProvider : openIdProviders.keySet()) {
					String providerUrl = openIdProviders.get(openIdProvider);
					Image providerIcon = openIdProvider.getIcon().createImage();
					providerIcon.setTitle(openIdProvider.name());
					providerIcon.setHeight("35px");
					PushButton providerButton = new PushButton(providerIcon);
					providerButton.addClickHandler(new RedirectClickHandler(
							providerUrl));
					content.add(providerButton);
				}

				PopupPanel popup = new PopupPanel(true);
				popup.setSize(openIdProviders.size() * 125 + "px", "50px");
				popup.setAnimationType(AnimationType.RIGHT_TO_LEFT);
				popup.setAnimationEnabled(true);
				popup.add(content);
				popup.showRelativeTo(actionButton);
			}
		});
	}

	public void showLogout(String logoutUrl) {
		actionButton.setText(constants.signOut());
		actionButton.addClickHandler(new RedirectClickHandler(logoutUrl));
	}

	public void showUserData(UserDTO user) {
		userLabel.setText(user.getNick());
	}
}
