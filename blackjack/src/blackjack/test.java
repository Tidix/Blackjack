package blackjack;

import controller.MenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.net.URL;
import factory.Factory52;

public class test extends Application {
	
	public MenuController menuC;
	public static Blackjack Blackjack;
	
	public static void main(String[] args) {
		Blackjack = new Blackjack(3, new Factory52());
		//Blackjack.jouer();
		//System.out.println(Blackjack);
		//System.out.println(Blackjack);
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		URL url = new URL("file:src/controller/Menu.fxml");
		FXMLLoader fxmlLoader = new FXMLLoader(url);
		Parent root = fxmlLoader.load();
		menuC = fxmlLoader.getController();
		menuC.giveMain(this);
		Scene sceneJeu = new Scene(root, 1800, 900, Color.LIGHTGRAY);

		primaryStage.setScene(sceneJeu);
		primaryStage.setTitle("Editeur");
		primaryStage.setMaximized(true);
		primaryStage.setFullScreen(true);
		primaryStage.setResizable(false);
		primaryStage.show();		
	}
}
