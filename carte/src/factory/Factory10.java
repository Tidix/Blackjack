package factory;

import java.util.ArrayList;

import carte.Carte;
import carte.Couleurs;
import carte.Valeurs;

public class Factory10 implements Factory{
	

	@Override
	public ArrayList<Carte> FactoryMethod() {
		ArrayList<Carte> pac = new ArrayList<Carte>();
		int i = 0;
		for(Valeurs value : Valeurs.values()) {
			i = (i == 10 ? 10 : i+1);
			if(value != Valeurs.Valet && value != Valeurs.Dame && value != Valeurs.Roi)
				pac.add(new Carte(value.toString(),Couleurs.Pique.toString(), i));
		}
		return pac;
	}
}
