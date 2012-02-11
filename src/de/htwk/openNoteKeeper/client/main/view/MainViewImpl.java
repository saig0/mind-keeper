package de.htwk.openNoteKeeper.client.main.view;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import de.htwk.openNoteKeeper.client.main.presenter.MainPresenter.MainView;
import de.htwk.openNoteKeeper.client.util.IconPool;

public class MainViewImpl implements MainView {

	@Inject
	private UserViewImpl userView;
	@Inject
	private LanguageChooserViewImpl languageChooser;

	private FlowPanel content = new FlowPanel();

	public Widget asWidget() {
		VerticalPanel main = new VerticalPanel();
		main.setSize("100%", "100%");

		HorizontalPanel header = new HorizontalPanel();
		header.setSize("100%", "100%");
		header.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);

		Image logo = IconPool.Logo.createImage();
		header.add(logo);
		header.setCellHorizontalAlignment(logo,
				HasHorizontalAlignment.ALIGN_LEFT);
		header.setCellWidth(logo, "250px");

		header.add(languageChooser);
		header.add(userView);

		main.add(header);
		main.setCellHeight(header, "10%");

		content.setSize("100%", "100%");
		main.add(content);

		return main;
	}

	public void setContent(Widget widget) {
		content.clear();
		content.add(widget);
	}

}
