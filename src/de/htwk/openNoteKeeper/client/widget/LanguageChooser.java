package de.htwk.openNoteKeeper.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import de.htwk.openNoteKeeper.client.i18n.CommonConstants;
import de.htwk.openNoteKeeper.client.util.IconPool;

public class LanguageChooser implements IsWidget {

	private CommonConstants constants = GWT.create(CommonConstants.class);

	public Widget asWidget() {
		HorizontalPanel panel = new HorizontalPanel();
		panel.setSpacing(10);

		panel.add(new Label(constants.language()));

		Image germanFlag = IconPool.Flag_German.createImage();
		germanFlag.setTitle("German");
		germanFlag.setStyleName("clickable");
		germanFlag.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				setLocaleTo("de");
			}

		});
		panel.add(germanFlag);

		Image usFlag = IconPool.Flag_Us.createImage();
		usFlag.setTitle("United States");
		usFlag.setStyleName("clickable");
		usFlag.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				setLocaleTo("en");
			}

		});
		panel.add(usFlag);

		return panel;
	}

	private void setLocaleTo(String newLocale) {
		String actualLocale = LocaleInfo.getCurrentLocale().getLocaleName();
		if (actualLocale == null || !actualLocale.equals(newLocale)) {
			UrlBuilder builder = Location.createUrlBuilder().setParameter(
					"locale", newLocale);
			Location.replace(builder.buildString());
		}
	}
}
