/*
 * Copyright Zak Linder
 */

package ch.sebastienzurfluh.client.view.cms.edit.page;

import ch.sebastienzurfluh.client.model.structure.Data;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Zak Linder
 */
public class PageEditor extends VerticalPanel {
	private TextBox titleBox = new TextBox();
	private TextArea subtitleBox = new TextArea();
	private StretchyTextArea contentBox = new StretchyTextArea();
	
	/**
	 * Create a blank page editor.
	 */
	public PageEditor() {
		add(createNewLine("Titre", titleBox));
		add(createNewLine("Sous-titre", subtitleBox));
		add(createNewLine("Contenu", contentBox));
		
		contentBox.setVisibleLines(15);
	}
	
	private FlowPanel createNewLine(String labelText, Widget textBox) {
		FlowPanel newLine = new FlowPanel();
		Label label = new Label(labelText);
		label.setStyleName("cms-textboxLabel");
		newLine.add(label);
		newLine.add(textBox);
		
		return newLine;
	}

	/**
	 * Create a menu editor with default values given by the {@code MenuData}.
	 * @param menuData
	 */
	public PageEditor(Data data) {
		this();
		
		titleBox.setText(data.getPageTitle());
		subtitleBox.setText(data.getPageContentHeader());
		contentBox.setText(data.getPageContentBody());
	}
}
