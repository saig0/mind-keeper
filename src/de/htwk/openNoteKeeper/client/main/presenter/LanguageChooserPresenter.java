package de.htwk.openNoteKeeper.client.main.presenter;

import java.util.Arrays;
import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.IsWidget;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

import de.htwk.openNoteKeeper.client.main.MainEventBus;
import de.htwk.openNoteKeeper.client.main.view.LanguageChooserViewImpl;

@Presenter(view = LanguageChooserViewImpl.class)
public class LanguageChooserPresenter extends
		BasePresenter<LanguageChooserViewImpl, MainEventBus> {

	private static final String COOKIE_NAME = "locale";
	private static final int COOKIE_TIMEOUT = 1000 * 60 * 60 * 24 * 14;

	private final class ChangeLocalClickHandler implements ClickHandler {

		private final String newLocale;

		public ChangeLocalClickHandler(String newLocale) {
			this.newLocale = newLocale;
		}

		public void onClick(ClickEvent event) {
			setLocaleTo(newLocale);
		}
	}

	public interface LanguageChooserView extends IsWidget {
		public HasClickHandlers getGermanWidget();

		public HasClickHandlers getUsWidget();
	}

	@Override
	public void bind() {
		view.getGermanWidget().addClickHandler(
				new ChangeLocalClickHandler("de"));

		view.getUsWidget().addClickHandler(new ChangeLocalClickHandler("en"));
	}

	public void onStart() {
		String locale = Cookies.getCookie(COOKIE_NAME);
		if (locale != null
				&& Arrays.asList(LocaleInfo.getAvailableLocaleNames())
						.contains(locale)) {
			setLocaleTo(locale);
		}
	}

	private void setLocaleTo(String newLocale) {
		String actualLocale = LocaleInfo.getCurrentLocale().getLocaleName();
		if (actualLocale == null || !actualLocale.equals(newLocale)) {
			UrlBuilder builder = Location.createUrlBuilder().setParameter(
					"locale", newLocale);
			Location.replace(builder.buildString());

			Date expires = new Date(new Date().getTime() + COOKIE_TIMEOUT);
			Cookies.setCookie(COOKIE_NAME, newLocale, expires);
		}
	}

}