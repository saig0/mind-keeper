package de.htwk.openNoteKeeper.client.util;

import java.util.LinkedList;
import java.util.List;

import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.presenter.BasePresenter;

public class PresenterFactory<Presenter extends BasePresenter<?, ModuleEventBus>, ModuleEventBus extends EventBus> {

	private final Class<Presenter> presenterType;

	public PresenterFactory(Class<Presenter> presenterType) {
		this.presenterType = presenterType;
	}

	private List<Presenter> handler = new LinkedList<Presenter>();

	public Presenter createPresenter(ModuleEventBus eventBus) {
		Presenter presenter = eventBus.addHandler(presenterType);
		handler.add(presenter);
		return presenter;
	}

	public void destroyPresenter(Presenter presenter) {
		handler.remove(presenter);
	}
}
