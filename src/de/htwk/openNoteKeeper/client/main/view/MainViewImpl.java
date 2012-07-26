package de.htwk.openNoteKeeper.client.main.view;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import de.htwk.openNoteKeeper.client.main.presenter.MainPresenter.MainView;
import de.htwk.openNoteKeeper.client.util.IconPool;

public class MainViewImpl implements MainView {

	@Inject
	private LanguageChooserViewImpl languageChooser;
	@Inject
	private CommunityViewImpl communityView;

	private FlowPanel content = new FlowPanel();
	private HorizontalPanel actionBar = new HorizontalPanel();
	private MenuBar menuBar;

	public Widget asWidget() {
		VerticalPanel main = new VerticalPanel();
		main.setSize("100%", "100%");

		MenuBar menuBar = createMenu(main);
		main.add(menuBar);
		menuBar.getElement().getParentElement().setAttribute("align", "right");

		HorizontalPanel header = createHeader();
		main.add(header);
		main.setCellHeight(header, "10%");

		content.setSize("100%", "100%");
		main.add(content);
		main.setCellHeight(content, "90%");

		return main;
	}

	private MenuBar createMenu(VerticalPanel main) {
		menuBar = new MenuBar();
		menuBar.setAnimationEnabled(true);
		menuBar.setAutoOpen(true);

		menuBar.addSeparator();
		menuBar.addItem(languageChooser.asMenuItem());
		menuBar.addSeparator();
		menuBar.addItem(communityView.asMenuItem());

		return menuBar;
	}

	private HorizontalPanel createHeader() {
		HorizontalPanel header = new HorizontalPanel();
		header.setSize("100%", "100%");
		header.setSpacing(5);
		header.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);

		Image logo = IconPool.Logo.createImage();
		header.add(logo);
		header.setCellHorizontalAlignment(logo,
				HasHorizontalAlignment.ALIGN_LEFT);
		header.setCellWidth(logo, "300px");

		header.add(actionBar);
		header.setCellHorizontalAlignment(actionBar,
				HasHorizontalAlignment.ALIGN_LEFT);
		return header;
	}

	public void setContent(Widget widget) {
		content.clear();
		content.add(widget);
	}

	public void setActionBar(Widget actionWidget) {
		actionBar.clear();
		actionBar.add(actionWidget);
	}

	public void setSettingsMenu(MenuItem menuItem) {
		menuBar.insertItem(menuItem, 0);
		menuBar.insertSeparator(0);
	}

	public void setUserMenu(MenuItem menuItem) {
		menuBar.insertItem(menuItem, 0);
	}
}
