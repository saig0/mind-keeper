package de.htwk.openNoteKeeper.client.main.view;

import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;

import de.htwk.openNoteKeeper.client.main.presenter.MainPresenter.MainView;
import de.htwk.openNoteKeeper.client.util.IconPool;

public class MainViewImpl implements MainView {

	private HLayout subHeader = new HLayout();
	private Layout content = new VLayout();

	public Widget asWidget() {
		VLayout main = new VLayout();
		main.setSize("100%", "100%");
		main.setMembersMargin(50);
		main.setLayoutMargin(10);

		HLayout header = new HLayout();
		header.setHeight(100);
		header.setWidth100();

		HLayout logo = new HLayout();
		logo.setWidth(600);
		logo.addMember(IconPool.Logo.createImage());
		header.addMember(logo);

		subHeader.setWidth("*");
		subHeader.setAlign(Alignment.RIGHT);
		subHeader.setMembersMargin(5);
		subHeader.setLayoutMargin(10);
		header.addMember(subHeader);

		main.addMember(header);

		content.setWidth100();
		content.setHeight("*");

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
		subHeader.addMember(userWidget);
	}
}
