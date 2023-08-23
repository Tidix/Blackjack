package factory;

import java.util.ArrayList;
import carte.Carte;
import carte.Couleurs;
import carte.Valeurs;

public class Factory32 implements Factory{
	
	/**
	 * Fais un paquet de 32 cartes, c'est-a-dire sans les chiffres 2,3,4,5
	 */
	@Override
	public ArrayList<Carte> FactoryMethod() {
		ArrayList<Carte> pac = new ArrayList<Carte>();
		int i = 0;
		for(Valeurs value : Valeurs.values()) {
			i = (i == 10 ? 10 : i+1);
			if(value != Valeurs.Deux && value != Valeurs.Trois && value != Valeurs.Quatre && value != Valeurs.Cinq)
				for(Couleurs color: Couleurs.values()) 
					pac.add(new Carte(value.toString(),color.toString(), i));				
		}
		return pac;
	}
}
