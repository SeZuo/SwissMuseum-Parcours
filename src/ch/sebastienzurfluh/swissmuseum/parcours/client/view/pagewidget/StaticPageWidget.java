package ch.sebastienzurfluh.swissmuseum.parcours.client.view.pagewidget;

import java.util.LinkedList;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;

import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.swissmuseum.core.client.model.Model;
import ch.sebastienzurfluh.swissmuseum.core.client.model.structure.PageData;
import ch.sebastienzurfluh.swissmuseum.core.client.patterns.Observable;
import ch.sebastienzurfluh.swissmuseum.core.client.patterns.Observer;
import ch.sebastienzurfluh.swissmuseum.core.client.view.pagewidget.PageToken;
import ch.sebastienzurfluh.swissmuseum.core.client.view.pagewidget.PageWidget;
import ch.sebastienzurfluh.swissmuseum.core.client.view.pagewidget.TextParser;

/**
 * This page widget loads the current data from the model and does not update to it unless asked to.
 * Additionnaly, you may ask the page to load the data from the next or previous page only.
 *
 *
 * @author Sebastien Zurfluh
 *
 */
public class StaticPageWidget extends FlowPanel implements Observer {
	private HTML title;
	private Label header;
	private FlowPanel content;
	private TextParser parser;
	private Model model;
	
	private static String primaryStyleName =  "pageWidget";
	
	public enum PagePosition {
		PREVIOUS, CURRENT, NEXT;
	}
	
	
	public StaticPageWidget(EventBus pageChangeEventBus, Model model, PagePosition position) {
		this.model = model;
		
		parser = new TextParser(pageChangeEventBus, model);
		
		model.currentPageDataObservable.subscribeObserver(this);
		
		title = new HTML("");
		title.setStyleName(primaryStyleName + "-title");
		header = new Label("");
		header.setStyleName(primaryStyleName + "-header");
		
//		Image titleImage = new Image("resources/images/fioritures/line.jpg");
//		titleImage.setStyleName(primaryStyleName + "-title-image");
		
		content = new FlowPanel();
		content.setStyleName(primaryStyleName + "-content");
		
		this.add(title);
		this.add(header);
//		this.add(titleImage);
		this.add(content);
		
		this.setStyleName(primaryStyleName);
		
		
	}


	@Override
	public void notifyObserver(Observable source) {
		if(model.getCurrentPageData().equals(PageData.NONE)) {
			setVisible(false);
		} else {
			setVisible(true);
			this.title.setHTML(
					"<span class='" + primaryStyleName + "-spanTitle'>"
					+ model.getCurrentPageData().getPageTitle()
					+ "</span>");
			this.header.setText(" " + model.getCurrentPageData().getPageContentHeader());
			
			this.content.clear();
			LinkedList<PageToken> tokenisedContent = 
					parser.parse(model.getCurrentPageData().getPageContentBody());
			for (PageToken pageToken : tokenisedContent) {
				if (pageToken.isResource()) {
					this.content.add(pageToken.getResourceWidget());
				} else if (pageToken.isText()) {
					this.content.add(new HTML(pageToken.getText()));
				}
			}
		}
		
		// this is the main difference with the dynamic PageWidget.
		// the page will only load once.
		model.currentPageDataObservable.unsubscribeObserver(this);
	}
}
