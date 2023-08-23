package mises;

public class Gagner implements Mise{
	
	@Override
	public int getGain() {
		return 1;
	}

	@Override
	public String getNomMise() {
		return "Gagne";
	}

}
