package de.htwk.openNoteKeeper.client.main.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

import de.htwk.openNoteKeeper.client.main.MainEventBus;
import de.htwk.openNoteKeeper.client.main.service.UserServiceAsync;
import de.htwk.openNoteKeeper.client.main.view.SettingsViewImpl;
import de.htwk.openNoteKeeper.client.util.LoadingScreenCallback;
import de.htwk.openNoteKeeper.client.util.StatusScreenCallback;
import de.htwk.openNoteKeeper.client.util.StatusScreenCallback.Status;
import de.htwk.openNoteKeeper.shared.SettingsDTO;
import de.htwk.openNoteKeeper.shared.UserDTO;

@Presenter(view = SettingsViewImpl.class)
public class SettingsPresenter extends
		BasePresenter<SettingsViewImpl, MainEventBus> {

	public interface SettingsView extends IsWidget {
		public void show();

		public void hide();

		public HasClickHandlers getSaveButton();

		public HasClickHandlers getAbortButton();

		public void setShouldAskBeforeDelete(boolean shouldAskBeforeDelete);

		public boolean shouldAskBeforeDelete();

		public void setDefaultNoteColor(String color);

		public String getDefaultNoteColor();

		public void setShouldUseRichTextEditor(boolean shouldUseRichTextEditor);

		public boolean shouldUseRichTextEditor();
	}

	@Inject
	private UserServiceAsync userService;

	private SettingsDTO settings;

	@Override
	public void bind() {
		view.getSaveButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				settings.setShouldAskBeforeDelete(view.shouldAskBeforeDelete());
				settings.setDefaultNoteColor(view.getDefaultNoteColor());
				settings.setShouldUseRichTextEditor(view
						.shouldUseRichTextEditor());
				userService.updateSettings(settings,
						new LoadingScreenCallback<Void>(event) {

							@Override
							protected void success(Void result) {
								Session.setSettings(settings);
								view.hide();
							}
						});
			}
		});

		view.getAbortButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				view.hide();
			}
		});
	}

	public void onShowSettings() {
		view.setShouldAskBeforeDelete(settings.shouldAskBeforeDelete());
		view.setDefaultNoteColor(settings.getDefaultNoteColor());
		view.setShouldUseRichTextEditor(settings.shouldUseRichTextEditor());
		view.show();
	}

	public void onLoggedIn(UserDTO user) {
		userService.getSettings(user.getId(),
				new StatusScreenCallback<SettingsDTO>(Status.Loading) {

					@Override
					protected void success(SettingsDTO settingsDto) {
						settings = settingsDto;
						Session.setSettings(settings);
					}
				});
	}
}
