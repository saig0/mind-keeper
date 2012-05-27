package de.htwk.openNoteKeeper.client.note.view.whiteboard;

import java.util.List;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.htwk.openNoteKeeper.client.note.presenter.whiteboard.SingleNotePresenter.SingleNoteView;
import de.htwk.openNoteKeeper.client.note.view.editor.TextEditor;
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
	private TextEditor editor;
	private Label dummyEditor = new Label();

	private HorizontalPanel header;

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

		header = new HorizontalPanel();
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
		return editor.getContentOfEditor();
	}

	public void setColor(String color) {
		editor.setColor(color);
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
		editor.setHeight(height);
		editor.setParentPanel(contentPanel);
		editor.show(contentLabel.getHTML());
		editor.getSaveButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				dummyEditor.fireEvent(event);
			}
		});
	}

	public void hideEditor() {
		if (editor != null) {
			contentPanel.add(contentLabel);
			contentPanel.setCellHeight(contentLabel, "100%");
			editor.hide();
			editor = null;
		}
	}

	public boolean isEditorVisible() {
		return editor != null;
	}

	public HasClickHandlers getSaveButton() {
		return dummyEditor;
	}

	public void setTextEditor(TextEditor editor) {
		this.editor = editor;
	}

	public void setTextEditorOptions(List<String> textEditorOptions) {
		editor.setTextEditorOptions(textEditorOptions);
	}

}