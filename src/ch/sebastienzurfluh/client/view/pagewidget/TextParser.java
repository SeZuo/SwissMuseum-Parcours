package ch.sebastienzurfluh.client.view.pagewidget;

import java.util.LinkedList;

import ch.sebastienzurfluh.client.control.eventbus.Event;
import ch.sebastienzurfluh.client.control.eventbus.EventBus;
import ch.sebastienzurfluh.client.control.eventbus.events.DataType;
import ch.sebastienzurfluh.client.control.eventbus.events.PageChangeRequest;
import ch.sebastienzurfluh.client.control.eventbus.events.ResourceRequest;
import ch.sebastienzurfluh.client.model.Model;
import ch.sebastienzurfluh.client.model.structure.DataReference;

public class TextParser {
	private static final String IMG_BALISE_START = "[img]";
	private static final String IMG_BALISE_END = "[/img]";
	private Model model;
	private EventBus pageChangeEventBus;
	
	public TextParser(EventBus pageChangeEventBus, Model model) {
		this.model = model;
		this.pageChangeEventBus = pageChangeEventBus;
	}
	
	public LinkedList<PageToken> parse(String text) {
		return parseImages(text);
	}
	
	private LinkedList<PageToken> parseImages(String parsing) {
		LinkedList<PageToken> tokenList = new LinkedList<PageToken>();
		StringBuilder parsed = new StringBuilder();
		
		while (!parsing.isEmpty()) {
			int imgDefBegin = parsing.indexOf(IMG_BALISE_START);
			int imgDefEnd = parsing.indexOf(IMG_BALISE_END);
			
			if(imgDefBegin == -1 || imgDefEnd == -1) {
				parsed.append(parsing);
				
				break;
			} else {
				parsed.append(parsing.substring(0, imgDefBegin));
				
				PageToken textParsedSoFar = new PageToken(parsed.toString());
				
				tokenList.add(textParsedSoFar);
				parsed = new StringBuilder(); 
				
				DataReference neededResource = new DataReference(
						DataType.RESOURCE,
						Integer.parseInt(
								parsing.substring(
										imgDefBegin + IMG_BALISE_START.length(),
										imgDefEnd)));
				
				tokenList.add(new PageToken(new ResourceWidget(
						neededResource,
						pageChangeEventBus,
						model)));						

				
				parsing = parsing.substring(imgDefEnd + IMG_BALISE_END.length());
			}
		}
		
		// Finish to empty the string builder.
		if (parsed.length() > 0)
			tokenList.add(new PageToken(parsed.toString()));
		
		return tokenList;
	}
}