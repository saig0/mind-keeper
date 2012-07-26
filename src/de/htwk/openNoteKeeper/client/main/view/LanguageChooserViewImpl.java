package de.htwk.openNoteKeeper.client.main.view;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.htwk.openNoteKeeper.client.i18n.CommonConstants;
import de.htwk.openNoteKeeper.client.main.presenter.LanguageChooserPresenter.LanguageChooserView;
import de.htwk.openNoteKeeper.client.util.IconPool;
import de.htwk.openNoteKeeper.client.widget.IconButton;

@Singleton
public class LanguageChooserViewImpl implements LanguageChooserView {

	@Inject
	private CommonConstants constants;

	private Command germanCommand;
	private Command usCommand;

	public MenuItem asMenuItem() {
		MenuBar menuBar = new MenuBar(true);
		IconButton german = new IconButton(IconPool.Flag_German.createImage(),
				constants.german());
		menuBar.addItem(german.getHTML(), true, germanCommand);
		IconButton us = new IconButton(IconPool.Flag_Us.createImage(),
				constants.english());
		menuBar.addItem(us.getHTML(), true, usCommand);
		IconButton language = new IconButton(IconPool.World.createImage(),
				constants.language());
		return new MenuItem(language.getHTML(), true, menuBar);
	}

	public void setGermanCommand(Command command) {
		this.germanCommand = command;
	}

	public void setUsCommand(Command command) {
		this.usCommand = command;
	}
}
