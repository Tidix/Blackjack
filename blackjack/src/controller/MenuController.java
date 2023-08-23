package controller;

import java.io.IOException;
import java.net.URL;
import blackjack.test;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * MenuController est la classe controller de la vue permettant d'afficher le menu de l'application
 * 
 * @author Clement Delamare - 21906426
 */
public class MenuController {

	public test test;
	
	@FXML
	public VBox menu;
	public BorderPane borderPane;

	/**
	 * La fonction initialize est invoquee au chargement du FXML apres l'injection des ressources du FXML dans le controleur.
	 */
	@FXML
	public void initialize() {
		setHBoxMenu();
	}
	
	/**
	 * La fonction jouer est une fonction JavaFX permettant de changer de scene pour aller sur la scene de jeu
	 * @param event un evenement, ici un clic sur le bouton
	 */
	public void jouer(ActionEvent event) throws IOException {
		URL url = new URL("file:src/controller/Jeu.fxml");
		FXMLLoader fxmlLoader = new FXMLLoader(url);
		Parent root = fxmlLoader.load();	
	    Scene scene = ((Node) event.getSource()).getScene();
	    scene.setRoot(root);
	    ((Stage) scene.getWindow()).setMaximized(true);
	}
	
	/**
	 * La fonction afficherRegle est une fonction JavaFX permettant de changer de scene pour aller sur la page des regles du Blackjack
	 * @param event un evenement, ici un clic sur le bouton
	 */
	public void afficherRegle(ActionEvent event) throws IOException {
		URL url = new URL("file:src/controller/Regles.fxml");
		FXMLLoader fxmlLoader = new FXMLLoader(url);
		Parent root = fxmlLoader.load();	
	    Scene scene = ((Node) event.getSource()).getScene();
	    scene.setRoot(root);
	    ((Stage) scene.getWindow()).setMaximized(false);
	}

	/**
	 * La fonction quitter permet de fermer l'application
	 */
	public void quitter() {
		Platform.exit();
	}
	
	/**
	 * La fonction setHBoxMenu permet de mettre en forme le menu
	 */
	public void setHBoxMenu() {
		Color c = Color.web("#2E8B39");
		this.menu.setBackground(new Background(new BackgroundFill(c,null,null)));
		
	}
	
	/**
	 * La fonction giveMain permet de recuperer le modele pour la fenetre representant l'interface graphique
	 * @param m l'objet possedant le modele Blackjack
	 */
	public void giveMain(test m){
		  this.test=m;
	}
	
}
