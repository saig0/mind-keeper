package de.htwk.openNoteKeeper.client.main.presenter;

import com.google.gwt.user.client.ui.IsWidget;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

import de.htwk.openNoteKeeper.client.main.MainEventBus;
import de.htwk.openNoteKeeper.client.main.view.WelcomeViewImpl;

@Presenter(view=WelcomeViewImpl.class)
public class WelcomePresenter extends BasePresenter<WelcomeViewImpl, MainEventBus> {

	public interface WelcomeView extends IsWidget {
		
	}
	
	public void onStart(){
		eventBus.setContent(view.asWidget());
	}
}
