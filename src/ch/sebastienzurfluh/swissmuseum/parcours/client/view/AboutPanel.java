package ch.sebastienzurfluh.swissmuseum.parcours.client.view;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.FlowPanel;

public class AboutPanel extends FlowPanel {
	public AboutPanel() {
		add(new HTML("<h1>A propos de Mimosa 3.</h1><p>Créé par Sébastien Zurfluh pour le" +
				"Musée des Suisses dans le Monde, Mimosa 3 permet l'édition des Parcours " +
				"de Penthes.</p><p>Utilisez les boutons \"+\" pour ajouter un nouveau groupe," +
				"ou une nouvelle page. Le signet \"ressources\" en haut de la page, vous" +
				"permettra d'ajouter du contenu graphique (vidéos, images, ...) à vos parcours." +
				"</p>"));
		add(new HTML("<h1>A propos des parcours de Penthes.</h1><p>Ce guide virtuel vous " +
				"emporte dans le Musée des Suisses dans le Monde, à la découverte de " +
				"l'exposition permanente et de ses nombreuses curiosités.</p><p>" +
				"Les sources de ce programme sont sous licence libre GPLv3, et vous êtes libres" +
				"de les modifier et de les réutiliser. Le Musée des Suisses dans le Monde" +
				"estime que son travail doit pouvoir servir à l'amélioration d'autres musées." +
				"</p>"));
	}
}
