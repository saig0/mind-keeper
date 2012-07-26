package de.htwk.openNoteKeeper.client.main.presenter;

import java.util.Arrays;
import java.util.Date;

import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.MenuItem;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

import de.htwk.openNoteKeeper.client.main.MainEventBus;
import de.htwk.openNoteKeeper.client.main.view.LanguageChooserViewImpl;

@Presenter(view = LanguageChooserViewImpl.class)
public class LanguageChooserPresenter extends
		BasePresenter<LanguageChooserViewImpl, MainEventBus> {

	private static final String COOKIE_NAME = "locale";
	private static final int COOKIE_TIMEOUT = 1000 * 60 * 60 * 24 * 14;

	public interface LanguageChooserView {
		public MenuItem asMenuItem();

		public void setGermanCommand(Command command);

		public void setUsCommand(Command command);
	}

	@Override
	public void bind() {
		view.setGermanCommand(new Command() {

			public void execute() {
				setLocaleTo("de");
			}
		});

		view.setUsCommand(new Command() {

			public void execute() {
				setLocaleTo("en");
			}
		});
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
		if (!actualLocale.equals(newLocale)) {
			UrlBuilder builder = Location.createUrlBuilder().setParameter(
					"locale", newLocale);
			Location.replace(builder.buildString());
		}
		Date expires = new Date(new Date().getTime() + COOKIE_TIMEOUT);
		Cookies.setCookie(COOKIE_NAME, newLocale, expires);
	}

}