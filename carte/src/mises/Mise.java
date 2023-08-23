package mises;

public interface Mise {
	/*
	 * Une mise est un Etat pour un joueur
	 * Par exemple, s'il gagne, alors sa mise est de x2 et est juste une gagne
	 * Alors que s'il perd, sa mise est de x0 est est une perte
	 */
	
	/**
	 * Une mise possede un certain gain
	 * @return le gain
	 */
	public int getGain();
	/**
	 * Une mise a un nom
	 * @return le nom de la mise
	 */
	public String getNomMise();
}
