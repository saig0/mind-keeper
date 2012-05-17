package de.htwk.openNoteKeeper.client.main.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.IsWidget;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

import de.htwk.openNoteKeeper.client.main.MainEventBus;
import de.htwk.openNoteKeeper.client.main.view.SettingsViewImpl;

@Presenter(view = SettingsViewImpl.class)
public class SettingsPresenter extends
		BasePresenter<SettingsViewImpl, MainEventBus> {

	public interface SettingsView extends IsWidget {
		public void show();

		public void hide();

		public HasClickHandlers getCreateButton();

		public HasClickHandlers getAbortButton();
	}

	@Override
	public void bind() {
		view.getCreateButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub

			}
		});

		view.getAbortButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				view.hide();
			}
		});
	}

	public void onShowSettings() {
		view.show();
	}
}
