package de.htwk.openNoteKeeper.client.widget;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class HideablePanel implements IsWidget {

	private final Widget hideableWidget;
	private final Widget fullWidget;

	private final Widget widget;
	private int widthOfHideWidgetInPercent;
	private boolean isVisible = true;

	private static final int WIDTH_OF_SIDEBAR = 35;

	public HideablePanel(Widget hideableWidget, Widget fullWidget,
			int widthOfHideWidgetInPercent) {
		this.hideableWidget = hideableWidget;
		this.fullWidget = fullWidget;
		this.widthOfHideWidgetInPercent = widthOfHideWidgetInPercent;

		widget = createLayout();
	}

	private Widget createLayout() {
		HorizontalPanel main = new HorizontalPanel();
		main.setSize("100%", "100%");
		main.setSpacing(10);

		Widget slidePanel = createSlidePanel(main);
		main.add(slidePanel);
		main.setCellWidth(slidePanel, widthOfHideWidgetInPercent + "%");

		main.add(fullWidget);

		return main;
	}

	private Widget createSlidePanel(final HorizontalPanel main) {
		final HorizontalPanel slidePanel = new HorizontalPanel();
		slidePanel.setSize("100%", "100%");

		slidePanel.add(hideableWidget);

		VerticalPanel sidebarPanel = new VerticalPanel();
		sidebarPanel.setSize("100%", "100%");
		sidebarPanel.addStyleName("sidebar");
		final Button hideButton = new Button("<");
		hideButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				if (isVisible) {
					hideButton.setText(">");
					main.setCellWidth(slidePanel, WIDTH_OF_SIDEBAR + "px");
				} else {
					hideButton.setText("<");
					main.setCellWidth(slidePanel, widthOfHideWidgetInPercent
							+ "%");
				}
				hideableWidget.setVisible(!isVisible);
				isVisible = !isVisible;
			}
		});
		sidebarPanel.add(hideButton);

		slidePanel.add(sidebarPanel);
		slidePanel.setCellWidth(sidebarPanel, WIDTH_OF_SIDEBAR + "px");

		return slidePanel;
	}

	public Widget asWidget() {
		return widget;
	}
}