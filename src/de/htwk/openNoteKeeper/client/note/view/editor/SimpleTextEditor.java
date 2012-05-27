package de.htwk.openNoteKeeper.client.note.view.editor;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.htwk.openNoteKeeper.client.i18n.CommonConstants;
import de.htwk.openNoteKeeper.client.main.presenter.Session;
import de.htwk.openNoteKeeper.client.util.IconPool;

public class SimpleTextEditor implements TextEditor {

	private RichTextArea editor;
	private VerticalPanel parentPanel;
	private Image saveIcon;
	private HorizontalPanel controlPanel;

	private CommonConstants commonConstants = GWT.create(CommonConstants.class);

	public SimpleTextEditor() {
		saveIcon = IconPool.Save.createImage();
		saveIcon.setTitle(commonConstants.save());
		saveIcon.addStyleName("clickable");
	}

	public void setParentPanel(VerticalPanel parentPanel) {
		this.parentPanel = parentPanel;
	}

	public void setHeight(int height) {
	}

	public void setColor(String color) {
	}

	public void hide() {
		if (editor != null) {
			editor.setEnabled(false);
			parentPanel.remove(editor);
			parentPanel.remove(controlPanel);
			editor = null;
			Session.setEditorIsVisable(false);
		}
	}

	public void show(final String text) {
		final Image loadingWidget = IconPool.Loading.createImage();
		parentPanel.add(loadingWidget);
		parentPanel.setCellHorizontalAlignment(loadingWidget,
				HasHorizontalAlignment.ALIGN_CENTER);
		parentPanel.setCellVerticalAlignment(loadingWidget,
				HasVerticalAlignment.ALIGN_MIDDLE);

		new Timer() {

			@Override
			public void run() {
				if (!Session.isEditorVisable()) {
					System.out.println("show: " + text);

					createAndInitEditor(text, loadingWidget);
					this.cancel();
				}
			}
		}.scheduleRepeating(100);
	}

	private void createAndInitEditor(String text, Image loadingWidget) {
		Session.setEditorIsVisable(true);

		editor = new RichTextArea();
		editor.addStyleName("noteEditor");
		editor.setSize("95%", "95%");

		editor.setHTML(text);

		parentPanel.remove(loadingWidget);

		controlPanel = new HorizontalPanel();
		controlPanel.setWidth("100%");
		controlPanel.setSpacing(5);
		controlPanel.addStyleName("editorControlPanel");

		controlPanel.add(saveIcon);
		parentPanel.add(controlPanel);

		parentPanel.add(editor);
		editor.setFocus(true);
	}

	public HasClickHandlers getSaveButton() {
		return saveIcon;
	}

	public String getContentOfEditor() {
		if (editor != null)
			return editor.getHTML();
		else
			return null;
	}

	public void setTextEditorOptions(List<String> textEditorOptions) {
	}

}
