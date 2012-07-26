package de.htwk.openNoteKeeper.client.main;

import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.Start;
import com.mvp4g.client.annotation.module.ChildModule;
import com.mvp4g.client.annotation.module.ChildModules;
import com.mvp4g.client.event.EventBus;

import de.htwk.openNoteKeeper.client.main.presenter.CommunityPresenter;
import de.htwk.openNoteKeeper.client.main.presenter.LanguageChooserPresenter;
import de.htwk.openNoteKeeper.client.main.presenter.MainPresenter;
import de.htwk.openNoteKeeper.client.main.presenter.SettingsMenuPresenter;
import de.htwk.openNoteKeeper.client.main.presenter.SettingsPresenter;
import de.htwk.openNoteKeeper.client.main.presenter.UserPresenter;
import de.htwk.openNoteKeeper.client.main.view.MainViewImpl;
import de.htwk.openNoteKeeper.client.note.NoteModule;
import de.htwk.openNoteKeeper.shared.UserDTO;

@Events(startView = MainViewImpl.class, ginModules = MainModule.class)
@ChildModules(value = @ChildModule(autoDisplay = false, moduleClass = NoteModule.class))
public interface MainEventBus extends EventBus {

	@Start
	@Event(handlers = { MainPresenter.class, UserPresenter.class,
			LanguageChooserPresenter.class, CommunityPresenter.class,
			SettingsMenuPresenter.class })
	public void start();

	@Event(handlers = MainPresenter.class)
	public void setContent(Widget content);

	@Event(handlers = MainPresenter.class)
	public void setActionBar(Widget actionWidget);

	@Event(handlers = { SettingsPresenter.class, SettingsMenuPresenter.class }, modulesToLoad = NoteModule.class)
	public void loggedIn(UserDTO user);

	@Event(handlers = SettingsPresenter.class)
	public void showSettings();

	@Event(handlers = MainPresenter.class)
	public void setSettingsMenu(MenuItem menuItem);

	@Event(handlers = MainPresenter.class)
	public void setUserMenu(MenuItem menuItem);
}
