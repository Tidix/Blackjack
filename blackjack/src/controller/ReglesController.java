package controller;

import java.io.IOException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * ReglesController est la classe controller de la vue permettant d'afficher la page des regles
 * 
 * @author Clement Delamare - 21906426
 */
public class ReglesController {

	@FXML
	public VBox regles;
	
	/**
	 * La fonction initialize est invoquee au chargement du FXML apres l'injection des ressources du FXML dans le controleur.
	 */
	@FXML
	public void initialize() {
		 setVBoxMenu();
	}
		
	/**
	 * La fonction retourMenu est une fonction JavaFX permettant de changer de scene pour retourner au menu
	 * @param event un evenement, ici un clic sur le bouton
	 */
	public void retourMenu(ActionEvent event) throws IOException {
		URL url = new URL("file:src/controller/Menu.fxml");
		FXMLLoader fxmlLoader = new FXMLLoader(url);
		Parent root = fxmlLoader.load();	
	    Scene scene = ((Node) event.getSource()).getScene();
	    scene.setRoot(root);
	    ((Stage) scene.getWindow()).setMaximized(true);
	}
	
	/**
	 * La fonction setVBoxMenu permet de mettre en forme la page de regle
	 */
	public void setVBoxMenu() {
		Color c = Color.web("#2E8B39");
		this.regles.setBackground(new Background(new BackgroundFill(c,null,null)));
		
	}

	
}
