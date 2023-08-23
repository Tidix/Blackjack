package joueurs;


import java.util.Scanner;
import carte.Carte;
import carte.Paquet;


public class Humain extends Joueur{
	
	/**
	 * Un humain a besoin de 2 cartes pour faire son paquet
	 * @param c1 carte1
 	 * @param c2 carte2
	 */
	public Humain(Carte c1, Carte c2) {
		this.main = new Paquet(c1, c2);
	}
	
	/**
	 * Le joueur humain choisit s'il pioche ou s'il passe
	 * Pour l'application, on utilise des boutons plutot que cette fonction
	 */
	@Override
	public int getAction() {
		if(this.main.getPoidsTotal() >= 21)
			return 1;
		Scanner scanner = new Scanner(System.in);
        System.out.print("0: Piocher 1: Passer -> ");
        int choix = scanner.nextInt();
		return (choix == 0 || choix == 1 ? choix : getAction());
	}

	@Override
	public String toString() {
		return "Joueur"+super.toString();
	}
	
	

}
