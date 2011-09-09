package de.htwk.openNoteKeeper.client.main.view;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.htwk.openNoteKeeper.client.i18n.CommonConstants;
import de.htwk.openNoteKeeper.client.main.presenter.LanguageChooserPresenter.LanguageChooserView;
import de.htwk.openNoteKeeper.client.util.IconPool;

@Singleton
public class LanguageChooserViewImpl implements LanguageChooserView {

	@Inject
	private CommonConstants constants;

	private Image germanFlag = IconPool.Flag_German.createImage();
	private Image usFlag = IconPool.Flag_Us.createImage();

	public Widget asWidget() {
		HorizontalPanel panel = new HorizontalPanel();
		panel.setSpacing(10);

		panel.add(new Label(constants.language()));

		germanFlag.setTitle("German");
		germanFlag.setStyleName("clickable");
		panel.add(germanFlag);

		usFlag.setTitle("United States");
		usFlag.setStyleName("clickable");
		panel.add(usFlag);

		return panel;
	}

	public HasClickHandlers getGermanWidget() {
		return germanFlag;
	}

	public HasClickHandlers getUsWidget() {
		return usFlag;
	}
}
