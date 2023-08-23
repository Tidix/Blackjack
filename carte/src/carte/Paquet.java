package carte;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import factory.Factory;
import factory.Factory52;

public class Paquet {

	private ArrayList<Carte> paquet;
	/**
	 * Il y a actuellement 3 factory methods differentes pour creer 3 paquets differents
	 * @param f factory utilise pour creer le paquet
	 */
	public Paquet(Factory f) {
		this.paquet = f.FactoryMethod();
		shuffle();
	}
	/**
	 * Si aucuns factory n'est utilise, un paquet 52 cartes est utilise
	 */
	public Paquet() {
		Factory f = new Factory52();
		this.paquet = f.FactoryMethod();
		shuffle();
	}
	/**
	 * Fait un clone du paquet pour faire un prototype
	 * @param p le paquet clone
	 */
	public Paquet(Paquet p) {
		this.paquet = new ArrayList<Carte>(p.getPaquet());
	}
	/**
	 * Les joueurs possedent un paquet de 2 cartes specifiques
	 * @param c1 Premiere carte (la cachee)
	 * @param c2 Deuxieme carte
	 */
	public Paquet(Carte c1, Carte c2) {
		this.paquet = new ArrayList<Carte>();
		this.paquet.add(c1);
		this.paquet.add(c2);
	}
	
	public String toString() {
		String p = "";
		for(Carte c : getPaquet()) {
			p += c + "\n";
		}
		return p;
	}
	/**
	 * 
	 * @return la liste de cartes
	 */
	public ArrayList<Carte> getPaquet() {
		return this.paquet;
	}
	
	/**
	 * Permet de melanger les cartes
	 */
	private void shuffle() {
		Collections.shuffle(this.paquet);
	}
	
	/**
	 * Ajoute une carte au paquet
	 * Si un joueur pioche une carte
	 * @param c1 carte prise
	 */
	public void addCarte(Carte c1) {
		paquet.add(c1);
	}
	/**
	 * Enleve une carte au paquet
	 * Si le paquet envoie une carte a un joueur
	 * @param c1 carte retiree
	 */
	public void removeCarte(Carte c1) {
		paquet.remove(c1);
	}

	/**
	 * Un paquet possede un poid total, qui est la somme des poids de toutes les cartes
	 * Un As etant special en vallant 1 ou 11, il faut faire une action speciale pour elle
	 * @return
	 */
	public int getPoidsTotal() {
		int poid = 0;
		List<Carte> ace = new ArrayList<Carte>();
		for(Carte carte : this.paquet) {
			if(carte.ace()) 
				ace.add(carte);
			poid+= carte.getPoids();
		}
		if(!ace.isEmpty())
			if(poid<=11)
				poid+=10;
		return poid;
	}
	
	/**
	 * Pioche la premiere carte du paquet
	 * Permet de prendre la premiere carte du paquet pour piocher
	 * @return la carte
	 */
	public Carte giveCarte() {
		return paquet.remove(0);

	}
	
}
