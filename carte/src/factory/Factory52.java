package factory;

import java.util.ArrayList;
import carte.Carte;
import carte.Couleurs;
import carte.Valeurs;

public class Factory52 implements Factory{
	
	/**
	 * Fait un paquet complet de 52 cartes
	 * 4 couleurs et 13 figures
	 */
	@Override
	public ArrayList<Carte> FactoryMethod() {
		ArrayList<Carte> pac = new ArrayList<Carte>();
		int i = 0;
		for(Valeurs value : Valeurs.values()) {
			i = (i == 10 ? 10 : i+1);
			for(Couleurs color: Couleurs.values()) 
				pac.add(new Carte(value.toString(),color.toString(), i));			
		}
		return pac;
	}

}
