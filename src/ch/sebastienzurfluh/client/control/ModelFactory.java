package ch.sebastienzurfluh.client.control;

import ch.sebastienzurfluh.client.model.Model;
import ch.sebastienzurfluh.client.model.io.CakeConnector;
import ch.sebastienzurfluh.client.model.io.TestConnector;

/**
 * Create test data with this factory.
 * @author Sebastien Zurfluh
 *
 */
public class ModelFactory {
	enum Connector {
		TEST, CAKE;
	}
	
	public static Model createModel(Connector connector) {
		switch (connector) {
			case TEST:
				return new Model(new TestConnector());
			case CAKE:
				return new Model(new CakeConnector());
			default:
				throw(new Error(""));
		}
	}
	
	
	/*
	public static Data createTutorialTree() {
		//Create super tree
		Data superTree = new Data(0, DataType.SUPER, 0, null, null, null, null, null);
		
		// Create language tree
		Data languageTree = new Data(1, DataType.SUPER, 0, "English", null, null, null, null);
		
		// Create the tree for the tutorial booklet
		Data tutorialBooklet = new Data(2, DataType.BOOKLET, 0,
				"Tutorial",
				"This booklet explains how to use the application.",
				"To coninue to the next step of this tutorial, choose the first image in the \"Chapter\" menu below",
				null,
				null);
		
		Data tutorialChapter1 = new Data(3, DataType.CHAPTER, 0,
				"Step 1",
				"You've just used the \"tile menu\". Use this menu to choose which object you want to see.",
				"Now, take a look at the beginning of the page. You can see another line appeared. This is"
						+ " the navigation menu. You can slide with you're finger along any of the lines to go to"
						+ " the next or previous item on the list. <br>"
						+ "Give it a go and slide your finger from right to left on the last line of the navigation"
						+ "menu.",
				null,
				null);
		
		Data tutorialChapter2 = new Data(4, DataType.CHAPTER, 2,
				"Step 2",
				"You've just changed chapter!",
				"You can use this navigation menu like you would turn a page of a book, except that depending on the"
						+ " line you choose to slide, you'll flip to the next booklet, the next chapter, or the next"
						+ " page!<br>"
						+ "You can see in the text bar below the navigation menu, that there is a reminder of your"
						+ "position in the booklet. To continue, click this <link>link</link>",
				null,
				null);
		
		Data tutorialChapter2Page1 = new Data(5, DataType.PAGE, 0,
				"Step 3",
				"This is a page.",
				"The link you've just clicked sent you here. This page belongs to the same chapter you were in. <br>" +
				"Sometimes, this is not the case and a link in the page will send you to another chapter or" +
				" another booklet. Don't worry you can always go back, using the back button on you're device " +
				"[img].<br> Go to the next page by either using the navigation menu (above) or the tile menu (below).",
				null,
				null);
		
		// Create relations
		superTree.addChild(languageTree);
		languageTree.addChild(tutorialBooklet);
		tutorialBooklet.addChild(tutorialChapter1);
		tutorialBooklet.addChild(tutorialChapter2);
		tutorialChapter2.addChild(tutorialChapter2Page1);
		
		return superTree;
		
	}
	*/
}
