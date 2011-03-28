package de.htwk.openNoteKeeper.client.main.view;

import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;

import de.htwk.openNoteKeeper.client.main.presenter.MainPresenter.MainView;

public class MainViewImpl implements MainView {

	private Layout content = new VLayout();
	private Layout header = new VLayout();

	public Widget asWidget() {
		VLayout main = new VLayout();
		main.setSize("100%", "100%");
		main.setMembersMargin(20);
		main.setLayoutMargin(5);

		header.setWidth100();
		header.setHeight("50px");

		header.addMember(new Label("header"));

		main.addMember(header);

		content.setWidth100();
		content.setHeight("*");

		content.addMember(new Label("content"));

		main.addMember(content);

		return main;
	}

	public void setContent(Widget widget) {
		Layout container = new VLayout();
		container.setSize("100%", "100%");
		container.addMember(widget);
		content.setMembers(container);
	}

	public void setUserWidget(Widget userWidget) {
		Layout container = new VLayout();
		container.setSize("100%", "100%");
		container.addMember(userWidget);
		header.setMembers(container);
	}
}
