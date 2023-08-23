package carte;

public class Carte {

	private int poids;
	private String valeur;
	private String couleur;

	/**
	 * Une carte possede une valeur (As, deux), une couleur (Trefle, Coeur) et un poid (deux = 2, trois = 3)
	 * @param valeur la valeur de la carte
	 * @param couleur la couleur de la carte
	 * @param i le poid de la valeur
	 */
	public Carte(String valeur, String couleur, int i) {
		this.valeur = valeur;
		this.couleur = couleur;
		this.poids = i;
	}

	/**
	 * L'as est une carte speciale, elle vaut soit 1, soit 11, selon la main du joueur
	 * @return
	 */
	public boolean ace() {
		return this.poids==1;
	}

	/**
	 * Chaque carte possede une valeur, qui chacune est presente 4 fois dans un paquet classique
	 * @return la valeur, que ce soit un chiffre ou une tete
	 */
	public String getValeur() {
		return valeur;
	}

	/**
	 * Chaque carte possede une couleur, qui chacune est presente 13 fois dans un paquet
	 * @return la couleur de la carte
	 */
	public String getCouleur() {
		return couleur;
	}

	/**
	 * 
	 * @return le poid de la carte
	 */
	public int getPoids() {
		return this.poids;
	}

	/**
	 * Si la carte est un as, il compte pour 1 ou 11, dependament de la valeur total du joueur.
	 * Il n'est pas sense pouvoir perdre a cause d'un as qui donne 11
	 */
	public int getPoids(int val){
		return (ace() ? (val + this.poids > 21 ? 1 : 11) : this.poids);
	}

	/**
	 * ex : 1. As de pique
	 */
	public String toString() {
		return getPoids() +". " + getValeur() + " de " + getCouleur();
	}



	public static void main(String args[]) {
		Carte c1= new Carte(Valeurs.As.toString(), Couleurs.Pique.toString(), 1);
		System.out.println(c1);
	}

}
