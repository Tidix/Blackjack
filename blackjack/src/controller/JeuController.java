package controller;

import java.io.IOException;
import java.net.URL;
import blackjack.Blackjack;
import blackjack.test;
import carte.Carte;
import factory.Factory52;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import joueurs.Ia;
import joueurs.Joueur;

/**
 * JeuController est la classe permettant d'effectuer des traitements sur le jeu entre le modele est la vue.
 * 
 * @author Clement Delamare - 21906426
 */
public class JeuController {
	
	public Blackjack blackjack; //Modele blackjack egal a celui de la classe test
	
	@FXML public AnchorPane anchorPane;
	@FXML public Button piocherButton;
	@FXML public Button passerButton;
	@FXML public Label joueurLabel;
	@FXML public Label winnerLabel;
	public Label nbCartes = new Label();
	public Group Paquet;
	public GridPane tabMain = new GridPane();
	public GridPane tabMainCroupier = new GridPane();
	
	
	/**
	 * La fonction initialize est invoquee au chargement du FXML apres l'injection des ressources du FXML dans le controleur.
	 */
	@FXML
	public void initialize() {
		this.blackjack = test.Blackjack;
		setJeu();
		setPaquet();	
		createMainJoueur();	
	}
	
	/**
	 * La fonction setJeu regroupe les traitements a faire au moment de l'initialisation du Controller
	 */
	public void setJeu() {
		this.nbCartes.setLayoutX(10);
		this.nbCartes.setLayoutY(300);
		this.nbCartes.setStyle("-fx-font-size:25px;");
		this.anchorPane.getChildren().add(this.nbCartes);
		Color c = Color.web("#2E8B39");
		this.anchorPane.setBackground(new Background(new BackgroundFill(c,null,null)));		
	}
	
	/**
	 * La fonction passer passe le tour du joueur actuel
	 */
	public void passer() {
		this.blackjack.passer();	
		nextTurn();
	}
	
	/**
	 * La fonction nextTurn realise des verifications concernant le tour suivant, comme connaitre le type du prochain joueur, si il a deja passe ou 
	 * si la partie est terminee.
	 */
	public void nextTurn() {	
		setJoueurLabel();
		if(this.blackjack.getJoueurTour() instanceof Ia && this.blackjack.getJoueurTour().getAction() == 0) {
			piocherCarte();
		}
		//Verifie si le prochain joueur peut piocher ou non
		if(this.blackjack.getPass().contains(this.blackjack.getJoueurTour()) || this.blackjack.getJoueurTour().getMain().getPoidsTotal() >= 21) {
			piocherButton.setDisable(true);
		}else {
			piocherButton.setDisable(false);
		}

		//Verifie si la partie est termine
		if(this.blackjack.finPartie()) {
			passerButton.setDisable(true);
			gagnant();
		}
		
		 if(this.blackjack.getJoueurTour() instanceof Ia && this.blackjack.getJoueurTour().getAction() == 1) {
				passer();
			}	

	}

	/**
	 * La fonction gagant est appelee lorsque la partie est terminee. Elle permet d'afficher les informations de fin de partie
	 * comme le ou les gagnants et met a jour les mains des joueurs.
	 */
	public void gagnant() {
		this.passerButton.setDisable(true);
		this.piocherButton.setDisable(true);
		while(this.blackjack.getCroupier().getAction()!=1) {
			Carte cartePioche = this.blackjack.getCroupier().addCarte(this.blackjack.piocher());		
			setPaquet();
			AnchorPane carte = createCarte(cartePioche.getCouleur(), cartePioche.getValeur());
			GridPane p = (GridPane) this.tabMainCroupier.getChildren().get(0);
			p.add(carte, p.getColumnCount(), 0);
		}
		int poids = this.blackjack.getCroupier().getMain().getPoidsTotal();
		this.blackjack.setWinner();
		Label l = new Label("Croupier" + "\n" + "Valeur: " + poids  + "\n" + this.blackjack.getCroupier().getMise().getNomMise());
		l.setStyle("-fx-font-size:25px;");
		tabMainCroupier.add(l,0, 2);
		for(int i=0; i< this.blackjack.getJoueurs().size();i++) {
			Label NodeL = (Label) getNodeFromGridPane(this.tabMain, i,2);
			NodeL.setText("Joueur " +  (i+1) + "\n" + "Valeur: " + this.blackjack.getJoueurs().get(i).getMain().getPoidsTotal() + "\n" + this.blackjack.getJoueurs().get(i).getMise().getNomMise());
		}

		this.winnerLabel.setText("Partie terminee !");
	}
	
	/**
	 * La fonction piocherCarte fait piocher une carte au joueur actuel et cree graphiquement la carte en consequence 
	 */
	public void piocherCarte(){
		int tour = this.blackjack.getTour();
		Joueur jactuel = this.blackjack.getJoueurTour();
		//On ajoute la carte dans la main dans le modele	
		Carte cartePioche = this.blackjack.piocher(jactuel);
		//On ajoute le nouveau paquet
		setPaquet();
		//On cree graphiquement la carte pioche
		AnchorPane carte = createCarte(cartePioche.getCouleur(), cartePioche.getValeur());
		GridPane p = (GridPane) getNodeFromGridPane(this.tabMain, tour%this.blackjack.getJoueurs().size(),1);
		p.add(carte, tour + 3, tour%this.blackjack.getJoueurs().size());
		//Recuperation du poids de la main du joueur actuel
		int poids = jactuel.getMain().getPoidsTotal();
		Label NodeL = (Label) getNodeFromGridPane(this.tabMain, tour%this.blackjack.getJoueurs().size(),2);
		NodeL.setText("Joueur " +  (tour%this.blackjack.getJoueurs().size()+1) + "\n" + "Valeur: " + poids);
		//Traitement de fin de tour
		nextTurn();
	}
	
	/**
	 * La fonction setPaquet initialise graphiquement le paquet de carte et precise le nombre de carte
	 * qu'il possede
	 */
	public void setPaquet() {
		this.anchorPane.getChildren().remove(this.Paquet);
		Group s = new Group();
		s.setLayoutX(10);
		s.setLayoutY(50);
		for(int i=0;i<this.blackjack.getPaquetDispo().getPaquet().size();i++){
			Rectangle r = new Rectangle(100,180);
			r.setFill(Color.WHITE);
			r.setStroke(Color.BLACK);
			r.setArcWidth(20);
			r.setArcHeight(20);
			r.setLayoutX(s.getLayoutX());
			r.setLayoutY(s.getLayoutY() - i*2);
			s.getChildren().add(r);
		}
		this.Paquet = s;

		this.nbCartes.setText("Nombre de carte: " + String.valueOf(this.blackjack.getPaquetDispo().getPaquet().size()));
		
		this.anchorPane.getChildren().add(this.Paquet);
	}	
	
	/**
	 * La fonction createMainJoueur initialise graphiquement les mains des joueurs avec toutes les informations necessaires
	 */
	public void createMainJoueur(){
		//On cree la main que graphiquement car le constructeur de blackjack l'initialise deja dans le modele
		this.tabMain.setHgap(50);
		this.tabMain.setVgap(50);	        
		this.tabMain.setLayoutX(100);
		this.tabMain.setLayoutY(450);
		int i = 0;
		//On cree la main des joueurs
		for(Joueur j: this.blackjack.getJoueurs()) {
			GridPane mJoueur = new GridPane();

			for(Carte c : j.getMain().getPaquet()) {
				mJoueur.addRow(i, createCarte(c.getCouleur(), c.getValeur()));				
			}
			this.tabMain.add(mJoueur, i, 1);
			Label l = new Label("Joueur " + (i+1) + "\n" + "Valeur: " + j.getMain().getPoidsTotal());
			l.setStyle("-fx-font-size:25px;");
			tabMain.add(l,i, 2);	
			i++;
		}

		this.anchorPane.getChildren().add(this.tabMain);
		//On cree la main du croupier
		this.tabMainCroupier.setHgap(50);
		this.tabMainCroupier.setVgap(50);	        
		this.tabMainCroupier.setLayoutX(820);
		this.tabMainCroupier.setLayoutY(100);
		GridPane mCroupier = new GridPane();
		for(Carte c : this.blackjack.getCroupier().getMain().getPaquet()) {
			mCroupier.addRow(0, createCarte(c.getCouleur(), c.getValeur()));				
		}
		tabMainCroupier.add(mCroupier, 0, 1);
		this.anchorPane.getChildren().add(this.tabMainCroupier);
		
	}
	
	/**
	 * La fonction createCarte permet de creer graphiquement des cartes en fonctions de celles piochees dans le modele
	 * @param couleur la couleur de la carte
	 * @param valeur la valeur de la carte
	 * @return carte un objet AnchorPane representant graphiquement la carte
	 */
	public AnchorPane createCarte(String couleur, String valeur) {
		Rectangle r = new Rectangle(100,180);
		r.setFill(Color.WHITE);
		if(couleur == "Carreau" || couleur == "Coeur") {
			r.setStroke(Color.RED);
		}else{
			r.setStroke(Color.BLACK);
		}

		r.setArcWidth(20);
		r.setArcHeight(20);
		
		Text textValeur = new Text(valeur);		
		Text textCouleur = new Text(couleur);
		textCouleur.setLayoutX(10);
		textCouleur.setLayoutY(20);		
		
		StackPane stackCarte = new StackPane();
		AnchorPane carte = new AnchorPane();
		
		stackCarte.getChildren().addAll(r, textValeur);
		carte.getChildren().addAll(stackCarte, textCouleur);
		return carte;		
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
	 * La fonction setJoueurLabel met a jour le texte permettant d'afficher le joueur actuel
	 */
	public void setJoueurLabel() {
		this.joueurLabel.setText("Tour du joueur numero " + (this.blackjack.getTour()%this.blackjack.getJoueurs().size() + 1));
		this.joueurLabel.setStyle("-fx-font-size:40px;;");
	}
	
	/**
	 * La fonction newGame permet de recommencer une partie selon les options de lancement
	 */
	public void newGame() {
		this.joueurLabel.setText("Tour du joueur numero 1");
		this.winnerLabel.setText("");
		this.passerButton.setDisable(false);
		this.piocherButton.setDisable(false);
		this.anchorPane.getChildren().removeAll(this.tabMain,this.tabMainCroupier);
		this.blackjack = new Blackjack(this.blackjack.getJoueurs().size()-1 , new Factory52());
		tabMain = new GridPane();
		tabMainCroupier = new GridPane();
		setPaquet();	
		createMainJoueur();	
	}
	
	/**
	 * La fonction getNodeFromGridPane permet de recuperer le noeud d'un tableau en fonction de sa colonne et sa ligne
	 * @param gridPane l'object GridPane etant le tableau
	 * @param col la colonne
	 * @param row la ligne
	 * @return node le noeud que l'on veut recuperer
	 */
	public Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
	    for (Node node : gridPane.getChildren()) {
	        if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
	            return node;
	        }
	    }
	    return null;
	}
}
