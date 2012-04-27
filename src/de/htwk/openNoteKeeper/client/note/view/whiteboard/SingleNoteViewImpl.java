package de.htwk.openNoteKeeper.client.note.view.whiteboard;

import com.axeiya.gwtckeditor.client.CKConfig;
import com.axeiya.gwtckeditor.client.CKConfig.PRESET_TOOLBAR;
import com.axeiya.gwtckeditor.client.CKConfig.TOOLBAR_OPTIONS;
import com.axeiya.gwtckeditor.client.CKEditor;
import com.axeiya.gwtckeditor.client.Toolbar;
import com.axeiya.gwtckeditor.client.ToolbarLine;
import com.axeiya.gwtckeditor.client.event.SaveEvent;
import com.axeiya.gwtckeditor.client.event.SaveHandler;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.htwk.openNoteKeeper.client.main.presenter.Session;
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
	private Label dummyEditor;

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
		contentPanel.setCellHeight(contentLabel, "100%");

		dummyEditor = new Label();

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
			}
		});

		return resizeableWidget;
	}

	private CKEditor createEditor(int height) {
		CKConfig ckConfig = new CKConfig(PRESET_TOOLBAR.BASIC);
		ckConfig.setUiColor(color);
		ckConfig.setWidth("95%");
		// ckConfig.setHeight("100%");
		ckConfig.setHeight(height + "px");

		ckConfig.setResizeEnabled(false);
		ckConfig.setFocusOnStartup(true);

		ToolbarLine line = new ToolbarLine();
		// Define the first line
		TOOLBAR_OPTIONS[] t1 = { TOOLBAR_OPTIONS.Save, TOOLBAR_OPTIONS.Undo,
				TOOLBAR_OPTIONS.Redo };
		line.addAll(t1);

		// Define the second line
		ToolbarLine line2 = new ToolbarLine();
		TOOLBAR_OPTIONS[] t2 = { TOOLBAR_OPTIONS.Bold, TOOLBAR_OPTIONS.Italic,
				TOOLBAR_OPTIONS.Underline, TOOLBAR_OPTIONS.Strike,
				TOOLBAR_OPTIONS._, TOOLBAR_OPTIONS.FontSize,
				TOOLBAR_OPTIONS.TextColor, TOOLBAR_OPTIONS.NumberedList,
				TOOLBAR_OPTIONS.BulletedList, TOOLBAR_OPTIONS._,
				TOOLBAR_OPTIONS.Outdent, TOOLBAR_OPTIONS.Indent,
				TOOLBAR_OPTIONS._, TOOLBAR_OPTIONS._, TOOLBAR_OPTIONS.Image,
				TOOLBAR_OPTIONS.Table };
		line2.addAll(t2);

		// Creates the toolbar
		Toolbar toolbar = new Toolbar();
		toolbar.add(line);
		toolbar.add(line2);
		ckConfig.setToolbar(toolbar);

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
		final int height = contentLabel.getOffsetHeight();
		contentPanel.remove(contentLabel);

		final Image loadingWidget = IconPool.Loading.createImage();
		contentPanel.add(loadingWidget);
		contentPanel.setCellHorizontalAlignment(loadingWidget,
				HasHorizontalAlignment.ALIGN_CENTER);
		contentPanel.setCellVerticalAlignment(loadingWidget,
				HasVerticalAlignment.ALIGN_MIDDLE);

		new Timer() {

			@Override
			public void run() {
				if (!Session.isEditorVisable()) {
					createAndInitEditor(height, loadingWidget);
					this.cancel();
				}
			}
		}.scheduleRepeating(100);
	}

	private void removeLoadingWidgetWhenEditorIsVisable(
			final Image loadingWidget) {
		if (isEditorVisable(loadingWidget.getElement())) {
			contentPanel.remove(loadingWidget);
		} else {
			new Timer() {

				@Override
				public void run() {
					if (isEditorVisable(loadingWidget.getElement())) {
						contentPanel.remove(loadingWidget);
						this.cancel();
					}
				}

			}.scheduleRepeating(100);
		}
	}

	public void hideEditor() {
		if (editor != null) {
			editor.setDisabled(true);
			contentPanel.remove(editor);
			contentPanel.add(contentLabel);
			contentPanel.setCellHeight(contentLabel, "100%");
			editor = null;
			Session.setEditorIsVisable(false);
		}
	}

	public boolean isEditorVisible() {
		return editor != null;
	}

	public HasClickHandlers getSaveButton() {
		return dummyEditor;
	}

	private void createAndInitEditor(final int height, final Image loadingWidget) {
		Session.setEditorIsVisable(true);
		editor = createEditor(height);
		editor.setData(contentLabel.getHTML());

		removeLoadingWidgetWhenEditorIsVisable(loadingWidget);
		contentPanel.add(editor);

		editor.addSaveHandler(new SaveHandler<CKEditor>() {

			public void onSave(SaveEvent<CKEditor> event) {
				NativeEvent clickEvent = Document.get().createClickEvent(0, 0,
						0, 0, 0, false, false, false, false);
				DomEvent.fireNativeEvent(clickEvent, dummyEditor);
			}
		});
	}

	private boolean isEditorVisable(Element e) {
		try {
			Element td = assertNotNull(DOM.getParent(e));
			Element tr = assertNotNull(DOM.getParent(td));
			Element tr2 = assertNotNull(DOM.getNextSibling(tr));
			Element td2 = assertNotNull(DOM.getFirstChild(tr2));
			Element form = assertNotNull(DOM.getFirstChild(td2));
			Element div = assertNotNull(DOM.getFirstChild(form));
			Element span = assertNotNull(DOM.getChild(div, 1));
			Element span2 = assertNotNull(DOM.getChild(span, 1));
			Element span3 = assertNotNull(DOM.getFirstChild(span2));
			Element table = assertNotNull(DOM.getFirstChild(span3));
			String cssClass = table.getClassName();
			if ("cke_editor".equals(cssClass)) {
				return true;
			} else {
				return false;
			}
		} catch (NullPointerException ex) {
			return false;
		}
	}

	private Element assertNotNull(Element e) throws NullPointerException {
		if (e == null)
			throw new NullPointerException();
		else
			return e;
	}
}