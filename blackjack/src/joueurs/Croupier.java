package joueurs;

import carte.Carte;
import carte.Paquet;

public class Croupier extends Joueur{
		
	public Croupier(Carte c1, Carte c2) {
		this.main = new Paquet(c1, c2);
	}
	
	/**
	 * Le croupier a une IA tres simple, il pioche s'il est en dessous de 17
	 */
	@Override
	public int getAction() {
		return (main.getPoidsTotal() < 17 ? 0 : 1);
	}
	
	@Override
	public String toString() {
		return "Croupier"+super.toString();
	}
	

	

}
