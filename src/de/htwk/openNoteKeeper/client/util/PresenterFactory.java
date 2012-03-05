package de.htwk.openNoteKeeper.client.util;

import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.presenter.BasePresenter;

public class PresenterFactory {

	public static <Presenter extends BasePresenter<?, ModuleEventBus>, ModuleEventBus extends EventBus> Presenter createPresenter(
			ModuleEventBus eventBus, Class<Presenter> presenterType) {
		Presenter presenter = eventBus.addHandler(presenterType);
		return presenter;
	}

	public static <Presenter extends BasePresenter<?, ModuleEventBus>, ModuleEventBus extends EventBus> void destroyPresenter(
			ModuleEventBus eventBus, Presenter presenter) {
		eventBus.removeHandler(presenter);
	}
}
