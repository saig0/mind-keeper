package de.htwk.openNoteKeeper.client.main;

import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.Start;
import com.mvp4g.client.event.EventBus;

import de.htwk.openNoteKeeper.client.main.presenter.MainPresenter;
import de.htwk.openNoteKeeper.client.main.view.MainViewImpl;

@Events(startView = MainViewImpl.class)
public interface MainEventBus extends EventBus {

	@Start
	@Event(handlers = { MainPresenter.class })
	public void start();

}
