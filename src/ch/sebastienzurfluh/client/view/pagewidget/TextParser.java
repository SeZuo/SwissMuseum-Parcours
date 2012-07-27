package ch.sebastienzurfluh.client.view.pagewidget;

import ch.sebastienzurfluh.client.control.eventbus.events.DataType;
import ch.sebastienzurfluh.client.model.Model;
import ch.sebastienzurfluh.client.model.structure.Data;
import ch.sebastienzurfluh.client.model.structure.DataReference;

public class TextParser {
	private static final String IMG_BALISE_START = "[img]";
	private static final String IMG_BALISE_END = "[/img]";
	Model model;
	
	public TextParser(Model model) {
		this.model = model;
	}
	
	public String parse(String text) {
		StringBuilder parsed = new StringBuilder(); 
		
		String parsing = text;
		
		// parse images
		while (!parsing.isEmpty()) {
			int imgDefBegin = parsing.indexOf(IMG_BALISE_START);
			int imgDefEnd = parsing.indexOf(IMG_BALISE_END);
			
			if(imgDefBegin == -1) {
				parsed.append(parsing);
				
				parsing = "";
			} else {
				parsed.append(parsing.substring(0, imgDefBegin));
				
				parsed.append(getImageHTML(
						new DataReference(
								DataType.RESOURCE,
								Integer.parseInt(
										parsing.substring(
												imgDefBegin + IMG_BALISE_START.length(),
												imgDefEnd)))));
				
				parsing = parsing.substring(imgDefEnd + IMG_BALISE_END.length());
			}
		}
		
		return parsed.toString();
	}
	
	private String getImageHTML(DataReference reference) {
		Data data = model.getAssociatedData(reference);
		
		
		
		return "<img src=\"%1\"></img>";
	}
}