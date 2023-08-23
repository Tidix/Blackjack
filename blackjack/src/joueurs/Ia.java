package joueurs;

import java.util.ArrayList;

import carte.Carte;
import carte.Paquet;

public class Ia extends Joueur{
	
	private Paquet paquetM;
	private final int chance =3;// Mettre 2 si on veux que l'ia joue a 1 chanche sur 2 ou mettre 3 pour 1 chance sur 3
	
	/**
	 * L'ia possede son paquet et peut savoir les cartes restantes dans le paquet pour savoir quelle est la meilleur action a effectuer
	 * Donc pas besoin d'Observer pattern, etant donnee que le paquet disponible est directement dans la classe
	 * @param c1 carte 1
	 * @param c2 carte 2
	 * @param paquetM paquet dispo
	 */
	public Ia(Carte c1, Carte c2,Paquet paquetM) {
		this.main = new Paquet(c1,c2);
		this.paquetM=paquetM;
	}
	
	/**
	 * L'ia va prendre le paquet actuel et regarder le nombre de possibilite de paquet qui lui est favorable et
	 *  cela est superieur ou egale a la moitier des carte du paquet il piochera (0) sinon il passera (1)
	 *  On creer un nouveau paquet prototype pour tester chaques cartes du paquet
	 *  @return 0 s'il peut piocher ou 1 s'il ne peut pas piocher
	 */
	@Override
	public int getAction() {
		int prob =0;		
		if(main.getPoidsTotal()==21 || main.getPoidsTotal()>21) {
			return 1;
		}		
		ArrayList<Carte> pModif = this.paquetM.getPaquet();		
		for (Carte c : pModif) {

			Paquet paquetPrototype = new Paquet(this.main);
			paquetPrototype.addCarte(c);
			int pCartem =paquetPrototype.getPoidsTotal();
			if(pCartem<=21 && pCartem>main.getPoidsTotal()) {
				prob+=1;
			}
		}
		if(prob>=(pModif.size()/chance)) {
			return 0; 
		}
		return 1;		
	}
	
	@Override
	public String toString() {
		return "Bot"+super.toString();
	}
	


}
