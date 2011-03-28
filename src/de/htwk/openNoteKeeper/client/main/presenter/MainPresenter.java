package de.htwk.openNoteKeeper.client.main.presenter;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

import de.htwk.openNoteKeeper.client.main.MainEventBus;
import de.htwk.openNoteKeeper.client.main.view.MainViewImpl;

@Presenter(view = MainViewImpl.class)
public class MainPresenter extends BasePresenter<MainViewImpl, MainEventBus> {
	public interface MainView extends IsWidget {
		public void setContent(Widget content);

		public void setUserWidget(Widget userWidget);
	}

	@Override
	public void bind() {

	}

	public void onStart() {

	}

	public void onSetUserWidget(Widget userWidget) {
		view.setUserWidget(userWidget);
	}

}