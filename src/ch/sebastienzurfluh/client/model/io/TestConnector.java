package ch.sebastienzurfluh.client.model.io;

import java.util.Collection;
import java.util.LinkedList;

import ch.sebastienzurfluh.client.control.eventbus.events.DataType;
import ch.sebastienzurfluh.client.control.eventbus.events.ResourceType;
import ch.sebastienzurfluh.client.model.structure.Data;
import ch.sebastienzurfluh.client.model.structure.DataReference;
import ch.sebastienzurfluh.client.model.structure.MenuData;
import ch.sebastienzurfluh.client.model.structure.ResourceData;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class TestConnector implements IOConnector {
	private Collection<MenuData> groupMenuList = new LinkedList<MenuData>();
	private Collection<Data> pageDataList = new LinkedList<Data>();
	
	public TestConnector() {
		groupMenuList.add(
				new MenuData(
						new DataReference(DataType.GROUP, 1),
						1, "Test Groupe 1", "Voici la description du groupe 1.",
						"resources/images/pix/light_violet.gif",
						"resources/images/pix/light_violet.gif"));
		
		pageDataList.add(new Data(new DataReference(DataType.PAGE, 1), 1, "Première page du groupe 1",
				"Ceci est un sous-titre fort long car c'est au final un sous-titre. ",
				"Nous voila dans le contenu de la page et pour cela il faut beaucoup de texte. " +
				"Il est cependant essentiel de voir ce à quoi une page ressemble vraiment pour " +
				"pouvoir travailler correctement. Ce pourquoi j'ajouterai une img ici. [img]1[/img]",
				"Titre du menu de la page 1.", "Description du menu de la page 1.",
				"resources/images/pix/light_green.gif",
				"resources/images/pix/light_green.gif"));
		
		pageDataList.add(new Data(new DataReference(DataType.PAGE, 2), 2, "Ceci est un page.",
				"D'ailleurs c'est la deuxième page.",
				"Qu'il est bien de gambader dans les prés en compagnie de nos amies les vaches qui" +
				"font sguiguigui./n" +
				"Je rajoute du texte exprès pour faire comme si j'avais quelque" +
				"chose a dire.",
				"Titre du menu de la page 1.", "Description du menu de la page 1.",
				"resources/images/pix/light_blue.gif",
				"resources/images/pix/light_blue.gif"));
	}
	
	public void asyncRequestAllGroupMenus(AsyncCallback<Collection<MenuData>> asyncCallBack) {
		asyncCallBack.onSuccess(groupMenuList);
	}

	public void asyncRequestGetFirstDataOfGroup(int referenceId, AsyncCallback<Data> asyncCallBack) {
		asyncCallBack.onSuccess(pageDataList.iterator().next());
	}

	public void asyncRequestGetData(int referenceId, AsyncCallback<Data> asyncCallBack) {
		int i = 1;
		for (Data data : pageDataList) {
			if (referenceId == i) {
				asyncCallBack.onSuccess(data);
			}
			i++;
		}
	}

	public void asyncRequestGetAllPageMenusFromGroup(int referenceId,
			AsyncCallback<Collection<MenuData>> asyncCallBack) {
		Collection<MenuData> pageMenus = new LinkedList<MenuData>();
		for (Data data : pageDataList) {
			pageMenus.add(data.getMenu());
		}
		asyncCallBack.onSuccess(pageMenus);
	}

	@Override
	public void asyncRequestResourceData(int referenceId,
			AsyncCallback<ResourceData> asyncCallback) {
		asyncCallback.onSuccess(
				new ResourceData(
						new DataReference(DataType.RESOURCE, 1),
						ResourceType.IMAGE,
						"Title of the resource", "Description of the resource.",
						"resources/images/pix/light_yellow.gif"));
	}

	@Override
	public void asyncRequestAllResourceData(
			AsyncCallback<Collection<ResourceData>> asyncCallback) {
		LinkedList<ResourceData> resourceList = new LinkedList<ResourceData>();
		resourceList.add(
				new ResourceData(
						new DataReference(DataType.RESOURCE, 1),
						ResourceType.IMAGE,
						"Title of the resource", "Description of the resource.",
						"resources/images/pix/light_yellow.gif"));
		asyncCallback.onSuccess(resourceList);
				
	}
}
