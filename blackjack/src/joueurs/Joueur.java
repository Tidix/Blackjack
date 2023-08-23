package joueurs;

import carte.Carte;
import carte.Paquet;
import mises.Mise;

public abstract class Joueur {
	
	/**
	 * Un joueur, une ia ou un croupier sont des joueurs differents, mais restent des joueurs du jeu.
	 * Pour cela, on utilise donc la Template Method
	 * Ils ont donc tous :
	 * 	- Un paquet de cartes
	 * 	- Un State en fin de partie (pour gagner ou perdre)
	 * 	- Des actions differentes a effectuer
	 */
	protected Paquet main;
	protected Mise mise;

	/**
	 * 
	 * @return le paquet du joueur
	 */
	public Paquet getMain() {
		return this.main;
	}

	/**
	 * Rajoute une carte dans la main du joueur
	 * @param carte la carte ajoutee dans la main
	 * @return la carte ajoutee
	 */
	public Carte addCarte(Carte carte) {
		this.main.addCarte(carte);
		return carte;
	}
		
	/**
	 * 
	 * @return L'action choisit par le joueur
	 * 0 : Piocher
	 * 1 : Passer
	 */
	public abstract int getAction();
	
	/**
	 * Un joueur aura un certain gain une fois la partie terminee
	 * @param mise la mise que le joueur vient de recevoir
	 */
	public void setMise(Mise mise) {
		this.mise = mise;
	}	
	/**
	 * Donne la mise du joueur
	 * @return la mise
	 */
	
	public Mise getMise() {
		return this.mise;
	}
	
	/**
	 * Ce qui est important est la valeur totale de la main et le paquet du joueur
	 * grace a l'heritage, on sait si un joueur est humain, croupier ou ia
	 */
	public String toString() {
		String str = " : valeur : " + this.main.getPoidsTotal() + "\n";
		str+= this.main;
		return str;
	}
	
}
