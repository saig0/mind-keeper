package de.htwk.openNoteKeeper.client.note.presenter;

import java.util.HashMap;
import java.util.Map;

import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.presenter.BasePresenter;

public class PresenterFactory<Presenter extends BasePresenter<View, ModuleEventBus>, View, ModuleEventBus extends EventBus> {

	private final Class<Presenter> presenterType;

	public PresenterFactory(Class<Presenter> presenterType) {
		this.presenterType = presenterType;
	}

	private Map<View, Presenter> handler = new HashMap<View, Presenter>();

	public Presenter createPresenter(ModuleEventBus eventBus) {
		Presenter presenter = eventBus.addHandler(presenterType);
		handler.put(presenter.getView(), presenter);
		return presenter;
	}

	public void destroyPresenter(View view) {
		handler.remove(view);
	}
}
