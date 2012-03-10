package ch.sebastienzurfluh.client.control;

import ch.sebastienzurfluh.client.model.structure.Booklet;
import ch.sebastienzurfluh.client.model.structure.Chapter;
import ch.sebastienzurfluh.client.model.structure.Data;
import ch.sebastienzurfluh.client.model.structure.Page;
import ch.sebastienzurfluh.client.model.structure.Tree;

/**
 * Create test data with this factory.
 * @author Sebastien Zurfluh
 *
 */
public class ModelFactory {
	public static Tree<Data> createTutorialTree() {
		//Create super tree
		Tree<Data> superTree = new Tree<Data>(0, 0, null, null, null, null, null);
		
		// Create language tree
		Tree<Booklet> languageTree = new Tree<Booklet>(1, 0, "English", null, null, null, null);
		
		// Create the tree for the tutorial booklet
		Booklet tutorialBooklet = new Booklet(2, 0,
				"Tutorial",
				"This booklet explains how to use the application.",
				"To coninue to the next step of this tutorial, choose the first image in the \"Chapter\" menu below",
				null,
				null);
		
		Chapter tutorialChapter1 = new Chapter(3, 0,
				"Step 1",
				"You've just used the \"tile menu\". Use this menu to choose which object you want to see.",
				"Now, take a look at the beginning of the page. You can see another line appeared. This is"
						+ " the navigation menu. You can slide with you're finger along any of the lines to go to"
						+ " the next or previous item on the list. <br>"
						+ "Give it a go and slide your finger from right to left on the last line of the navigation"
						+ "menu.",
				null,
				null);
		
		Chapter tutorialChapter2 = new Chapter(4, 2,
				"Step 2",
				"You've just changed chapter!",
				"You can use this navigation menu like you would turn a page of a book, except that depending on the"
						+ " line you choose to slide, you'll flip to the next booklet, the next chapter, or the next"
						+ " page!<br>"
						+ "You can see in the text bar below the navigation menu, that there is a reminder of your"
						+ "position in the booklet. To continue, click this <link>link</link>",
				null,
				null);
		
		Page tutorialChapter2Page1 = new Page(5, 0,
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
	
}
