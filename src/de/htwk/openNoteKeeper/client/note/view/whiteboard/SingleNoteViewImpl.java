package de.htwk.openNoteKeeper.client.note.view.whiteboard;

import com.axeiya.gwtckeditor.client.CKConfig;
import com.axeiya.gwtckeditor.client.CKConfig.PRESET_TOOLBAR;
import com.axeiya.gwtckeditor.client.CKEditor;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.HasBlurHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.htwk.openNoteKeeper.client.note.presenter.whiteboard.SingleNotePresenter.SingleNoteView;
import de.htwk.openNoteKeeper.client.util.IconPool;
import de.htwk.openNoteKeeper.client.widget.resize.HasResizeListener;
import de.htwk.openNoteKeeper.client.widget.resize.ResizeableWidget;

public class SingleNoteViewImpl implements SingleNoteView {

	private NoteContextMenu contextMenu;

	private final ResizeableWidget resizePanel;
	private FocusPanel main;
	private VerticalPanel contentPanel;
	private Label titleLabel;
	private RichTextArea contentLabel;
	private CKEditor editor;
	private String color;

	private boolean isSelected = false;

	public SingleNoteViewImpl() {
		resizePanel = createLayout();
	}

	private ResizeableWidget createLayout() {
		main = new FocusPanel();
		main.setSize("100%", "100%");
		main.addStyleName("note");
		final ResizeableWidget resizeableWidget = new ResizeableWidget(main);

		contentPanel = new VerticalPanel();
		contentPanel.setSize("100%", "100%");
		contentPanel.setSpacing(5);

		HorizontalPanel header = new HorizontalPanel();
		header.setSize("100%", "100%");
		header.addStyleName("noteHeader");

		titleLabel = new Label("");
		header.add(titleLabel);

		Image contextImage = IconPool.Settings_Small.createImage();
		contextImage.addStyleName("clickable");

		contextMenu = new NoteContextMenu();
		contextImage.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				contextMenu.show(event.getClientX(), event.getClientY());
			}
		});

		header.add(contextImage);
		header.setCellHorizontalAlignment(contextImage,
				HasHorizontalAlignment.ALIGN_RIGHT);
		header.setCellWidth(contextImage, "25px");

		contentPanel.add(header);
		contentPanel.setCellHeight(header, "25px");

		contentLabel = new RichTextArea();
		contentLabel.addStyleName("noteEditor");
		contentLabel.setSize("95%", "95%");
		contentPanel.add(contentLabel);

		main.setWidget(contentPanel);

		main.addMouseOverHandler(new MouseOverHandler() {

			public void onMouseOver(MouseOverEvent event) {
				main.addStyleName("activeNote");
			}
		});

		main.addMouseOutHandler(new MouseOutHandler() {

			public void onMouseOut(MouseOutEvent event) {
				if (!isSelected) {
					main.removeStyleName("activeNote");
				}
			}
		});

		contentLabel.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				main.addStyleName("activeNote");
				isSelected = true;
				// TODO Bugfix weil ContextMenu nicht schließt
				contextMenu.hide();
			}
		});

		main.addFocusHandler(new FocusHandler() {

			public void onFocus(FocusEvent event) {
				main.addStyleName("activeNote");
				isSelected = true;
			}
		});

		main.addBlurHandler(new BlurHandler() {

			public void onBlur(BlurEvent event) {
				main.removeStyleName("activeNote");
				isSelected = false;

				// if (editor != null) {
				// NativeEvent blurEvent = Document.get().createBlurEvent();
				// DomEvent.fireNativeEvent(blurEvent, contentLabel);
				// }
			}
		});

		return resizeableWidget;
	}

	private CKEditor createEditor() {
		CKConfig ckConfig = new CKConfig(PRESET_TOOLBAR.BASIC);
		ckConfig.setUiColor(color);
		ckConfig.setWidth("95%");
		// ckConfig.setHeight("100%");
		ckConfig.setHeight(contentLabel.getOffsetHeight() + "px");

		ckConfig.setResizeEnabled(false);
		ckConfig.setFocusOnStartup(true);

		CKEditor ckEditor = new CKEditor(ckConfig);
		return ckEditor;
	}

	public Widget asWidget() {
		return resizePanel;
	}

	public void setSize(int width, int height) {
		resizePanel.setSize(width + "px", height + "px");
	}

	public void setTitle(String title) {
		titleLabel.setText(title);
	}

	public void setContent(String content) {
		contentLabel.setHTML(content);
	}

	public Widget getDragHandle() {
		return titleLabel;
	}

	public HasResizeListener getResizableWidget() {
		return resizePanel;
	}

	public HasClickHandlers getDeleteButton() {
		return contextMenu.getDeleteButton();
	}

	public void hide() {
		contextMenu.hide();
	}

	public HasBlurHandlers getEditor() {
		return contentLabel;
	}

	public String getContentOfEditor() {
		if (editor != null)
			return editor.getHTML();
		else
			return null;
	}

	public void setColor(String color) {
		this.color = color;
		main.getElement().getStyle().setBackgroundColor(color);
	}

	public HasClickHandlers getEditButton() {
		return contextMenu.getEditButton();
	}

	public HasClickHandlers getMoveButton() {
		return contextMenu.getMoveButton();
	}

	public HasClickHandlers getEditorButton() {
		return contentLabel;
	}

	public void showEditor() {
		editor = createEditor();
		editor.setData(contentLabel.getHTML());

		contentPanel.remove(contentLabel);
		contentPanel.add(editor);

		editor.addValueChangeHandler(new ValueChangeHandler<String>() {

			public void onValueChange(ValueChangeEvent<String> event) {
				NativeEvent blurEvent = Document.get().createBlurEvent();
				DomEvent.fireNativeEvent(blurEvent, contentLabel);
			}
		});
	}

	public void hideEditor() {
		if (editor != null) {
			contentPanel.remove(editor);
			contentPanel.add(contentLabel);
			editor = null;
		}
	}

	public boolean isEditorVisible() {
		return editor != null;
	}
}