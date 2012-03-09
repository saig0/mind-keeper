package de.htwk.openNoteKeeper.client.widget;

import org.skrat.gwt.client.ui.Color;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class ColorPicker implements IsWidget, HasValueChangeHandlers<String> {

	private class ColorPopup extends PopupPanel {

		private HorizontalPanel panel;
		private Image hueSaturation;
		private Image lightness;
		private Label preview;
		private boolean down = false;

		float h = 200;
		float s = 2 / 3f;
		float l = 1 / 3f;

		ColorPopup() {
			super(true);

			this.panel = new HorizontalPanel();
			panel.setSpacing(5);
			this.hueSaturation = new Image("images/" + "hueSaturation.png");
			this.lightness = new Image("images/" + "lightness.png");
			this.preview = new Label();

			panel.setSize("220px", "100px");
			preview.setSize("20px", "100px");

			panel.add(hueSaturation);
			panel.add(lightness);
			panel.add(preview);
			setWidget(panel);
			addStyleName("fp-cp");

			DOM.setStyleAttribute(hueSaturation.getElement(), "cursor",
					"crosshair");
			DOM.setStyleAttribute(lightness.getElement(), "cursor", "ns-resize");
			DOM.setStyleAttribute(preview.getElement(), "cursor", "pointer");
			DOM.setStyleAttribute(preview.getElement(), "float", "right");
			DOM.setStyleAttribute(preview.getElement(), "cssFloat", "right");
			DOM.setStyleAttribute(preview.getElement(), "styleFloat", "right");

			setColor();

			hueSaturation.addMouseDownHandler(new MouseDownHandler() {

				public void onMouseDown(MouseDownEvent event) {
					event.preventDefault();
					setHueSaturation(event.getNativeEvent());
					down = true;
				}
			});

			hueSaturation.addMouseUpHandler(new MouseUpHandler() {

				public void onMouseUp(MouseUpEvent event) {
					setHueSaturation(event.getNativeEvent());
					down = false;
				}
			});

			hueSaturation.addMouseMoveHandler(new MouseMoveHandler() {

				public void onMouseMove(MouseMoveEvent event) {
					if (down)
						setHueSaturation(event.getNativeEvent());
				}
			});

			hueSaturation.addMouseOutHandler(new MouseOutHandler() {

				public void onMouseOut(MouseOutEvent event) {
					down = false;
				}
			});

			/* --- */

			lightness.addMouseDownHandler(new MouseDownHandler() {

				public void onMouseDown(MouseDownEvent event) {
					event.preventDefault();
					setLightness(event.getNativeEvent());
					down = true;
				}
			});

			lightness.addMouseUpHandler(new MouseUpHandler() {

				public void onMouseUp(MouseUpEvent event) {
					setLightness(event.getNativeEvent());
					down = false;
				}
			});

			lightness.addMouseMoveHandler(new MouseMoveHandler() {

				public void onMouseMove(MouseMoveEvent event) {
					if (down)
						setLightness(event.getNativeEvent());
				}
			});

			lightness.addMouseOutHandler(new MouseOutHandler() {

				public void onMouseOut(MouseOutEvent event) {
					down = false;
				}
			});

			/* --- */

			preview.addMouseDownHandler(new MouseDownHandler() {

				public void onMouseDown(MouseDownEvent event) {
					hide();
				}
			});
		}

		public String getHex() {
			return new Color(h, s, l).toString();
		}

		public void setHex(String colorString) {
			if (colorString.length() == 7 && colorString.startsWith("#")) {
				Color rgb = new Color(colorString);
				h = rgb.getHue();
				s = rgb.getSaturation();
				l = rgb.getLightness();
				setColor();
			}
		}

		private void setColor() {
			Color p = new Color(h, s, l);
			DOM.setStyleAttribute(preview.getElement(), "backgroundColor",
					p.toString());
			Color l = new Color(h, s, 0.5f);
			DOM.setStyleAttribute(lightness.getElement(), "backgroundColor",
					l.toString());
		}

		private void setHueSaturation(NativeEvent event) {
			int x = event.getClientX() - hueSaturation.getAbsoluteLeft();
			int y = event.getClientY() - hueSaturation.getAbsoluteTop();

			if (x > -1 && x < 181 && y > -1 && y < 101) {
				h = x * 2;
				s = (100 - y) / 100f;

				setColor();
			} else {
				down = false;
			}
		}

		private void setLightness(NativeEvent event) {
			int y = event.getClientY() - lightness.getAbsoluteTop();

			if (y > -1 && y < 101) {
				l = (100 - y) / 100f;
				setColor();
			} else {
				down = false;
			}
		}
	}

	private ColorPopup popup;
	private final Widget widget;
	private TextBox colorNameBox;
	private TextBox colorBox;

	private final String defaultColor;

	public ColorPicker(String defaultColor) {
		this.defaultColor = defaultColor;
		widget = createWidget();
	}

	private Widget createWidget() {
		this.popup = new ColorPopup();

		final HorizontalPanel panel = new HorizontalPanel();
		panel.setWidth("100%");

		colorNameBox = new TextBox();
		colorNameBox.setWidth("90%");
		colorNameBox.setValue(defaultColor);
		colorNameBox.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				popup.setHex(colorNameBox.getValue());
				popup.showRelativeTo(panel);
			}
		});

		colorNameBox.addKeyUpHandler(new KeyUpHandler() {

			public void onKeyUp(KeyUpEvent event) {
				popup.setHex(colorNameBox.getValue());
			}
		});

		panel.add(colorNameBox);
		panel.setCellWidth(colorNameBox, "80%");

		colorBox = new TextBox();
		colorBox.addStyleName("clickable");
		colorBox.setReadOnly(true);
		colorBox.setWidth("70%");
		colorBox.getElement().getStyle().setBackgroundColor(defaultColor);
		colorBox.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				DomEvent.fireNativeEvent(event.getNativeEvent(), colorNameBox);
			}
		});

		panel.add(colorBox);
		panel.setCellWidth(colorBox, "20%");

		popup.addCloseHandler(new CloseHandler<PopupPanel>() {

			public void onClose(CloseEvent<PopupPanel> event) {
				String color = popup.getHex();
				colorNameBox.setValue(color, true);
				colorBox.getElement().getStyle().setBackgroundColor(color);
			}
		});

		return panel;
	}

	public Widget asWidget() {
		return widget;
	}

	public HandlerRegistration addValueChangeHandler(
			ValueChangeHandler<String> handler) {
		return colorNameBox.addValueChangeHandler(handler);
	}

	public void fireEvent(GwtEvent<?> event) {
		colorNameBox.fireEvent(event);
	}

	public String getColor() {
		return colorNameBox.getValue();
	}

	public void setColor(String color) {
		colorNameBox.setValue(color);
		colorBox.getElement().getStyle().setBackgroundColor(color);
	}
}
