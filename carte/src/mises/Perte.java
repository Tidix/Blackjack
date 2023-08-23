package mises;

public class Perte implements Mise{
		
	@Override
	public int getGain() { 
		return 0;
	}

	@Override
	public String getNomMise() {
		return "Perdu";
	}

}
