package blackjack;

import java.util.ArrayList;
import carte.Carte;
import factory.Factory;
import carte.Paquet;
import joueurs.Croupier;
import joueurs.Humain;
import joueurs.Ia;
import joueurs.Joueur;
import mises.Egalite;
import mises.Gagner;
import mises.Perte;
import mises.VingtEtUn;

public class Blackjack {
	private ArrayList<Joueur> joueurs;
	private ArrayList<Joueur> pass;
	private Joueur croupier;
	private Paquet paquetDispo;
	private int tour;

	/**
	 * 
	 * @param j nombre d'ia (entre 0 et 4)
	 * @param c nombre de cartes (11, 32 ou 52)
	 */
	public Blackjack(int j, Factory c) {
		this.tour = 0;
		this.paquetDispo = new Paquet(c);
		this.croupier = new Croupier(piocher(), piocher()); 
		this.joueurs = new ArrayList<Joueur>();
		this.pass = new ArrayList<Joueur>();

		//this.joueurs.add(new Humain(piocher(), piocher()));
		this.joueurs.add(new Humain(piocher(), piocher()));

		//this.joueurs.add(new Humain(piocher(), piocher()));
		for(;j != 0; j--) 
			this.joueurs.add(new Ia(piocher(), piocher(),this.paquetDispo));
		this.tour = 0;
	}

	/**
	 * Le paquet disponible est celui dans lequel on va piocher les cartes
	 * @return
	 */
	public Paquet getPaquetDispo() {
		return this.paquetDispo;
	}

	/**
	 * Recupere tout les joueurs de la partie
	 * @return la liste des joueurs de la partie
	 */
	public ArrayList<Joueur> getJoueurs(){
		return this.joueurs;
	}

	/**
	 * le joueur qui joue depend du tour
	 * @return le joueur qui doit jouer
	 */
	public Joueur getJoueurTour() {
		return this.joueurs.get(tour%(this.joueurs.size()));
	}

	/**
	 * Recupere le tour actuel
	 * @return le tour actuel
	 */
	public int getTour() {
		return this.tour;
	}

	/**
	 * Recupere l'arrayList pass
	 * @return l'arrayList pass
	 */
	public ArrayList<Joueur> getPass() {
		return this.pass;
	}

	/**
	 * Recupere le croupier
	 * @return le croupier
	 */
	public Joueur getCroupier() {
		return this.croupier;
	}


	/**
	 * Si un joueur passe, une fois revenu a lui apres que tout les joueurs aient passe,
	 * la partie se termine
	 */
	public void passer() {
		if(!this.pass.contains(getJoueurTour()))
			this.pass.add(getJoueurTour());
		this.tour++;
	}

	/**
	 * La partie se termine une fois un tour complet ou tout le monde passe
	 * @return true : partie s'arrete
	 * false : partie continue
	 */
	public boolean finPartie() {
		return this.pass.size() == this.joueurs.size();
	}

	/**
	 * La partie se lance dans la boite de commande
	 */
	public void jouer() {
		System.out.println(croupier);
		Joueur jactuel;
		while(!finPartie()) {
			jactuel = getJoueurTour();
			if(!pass.contains(jactuel)) {
				System.out.println(jactuel);
				int choix = jactuel.getAction();
				System.out.println(choix);
				if(choix == 0)
					System.out.println("carte piochee : " + piocher(jactuel));
				if(choix == 1)
					passer();
			}
			else
				tour++;
		}
		System.out.println(croupier);
		while(croupier.getAction()!=1) {
			croupier.addCarte(piocher());
			System.out.println(croupier);
		}
		System.out.println("winner : " + getWinner());
	}

	/**
	 * Donner une carte a un joueur revient a l'enlever du paquet
	 * Si un joueur pioche, la partie ne se terminera pas avant le prochain tour passe
	 * @return
	 */
	public Carte piocher() {
		return paquetDispo.giveCarte();
	}

	/**
	 * 
	 * @param joueur le joueur qui pioche
	 * @return la carte piochee (qui est enlevee du paquet)
	 */
	public Carte piocher(Joueur joueur) {
		this.tour++;
		return joueur.addCarte(piocher());
	}
	
	/**
	 * Strategie pattern pour donner le State de la mise
	 * Un joueur gagne s'il a plus qu'un croupier qui a moins de 21 et qui a lui-meme moins de 21
	 * Il a egalite s'il a le meme score que le croupier, sauf si le croupier a un blackjack et que lui non
	 * Il perd s'il a en dessous un croupier qui gagne ou a plus que 21
	 */
	public void setWinner() {
		int pcroupier = croupier.getMain().getPoidsTotal();
		croupier.setMise(croupier.getMain().getPaquet().size()==2 && pcroupier == 21 ? 
									new VingtEtUn() : 
										(pcroupier <= 21 ? new Gagner() : new Perte())	);
		for(Joueur joueur : this.joueurs) {
			int poid = joueur.getMain().getPoidsTotal();
			/*
			 * Le joueur perd s'il le croupier gagne 
			 */
			if((poid < pcroupier && pcroupier <= 21) || poid > 21)
				joueur.setMise(new Perte());
			/*
			 * Le joueur a une egalite
			 * Si le croupier a un blackjack et que le joueur a un 21, il a quand meme perdu
			 */
			else if(poid == pcroupier)
				joueur.setMise((croupier.getMise() instanceof VingtEtUn ? new Perte() : 
					poid == 21 && joueur.getMain().getPaquet().size() == 2 && croupier.getMain().getPaquet().size() != 2 ? new VingtEtUn() : new Egalite()));
			
			else {
				/*
				 * Le joueur a un blackjack. Il a une egalite si le croupier aussi
				 */
				if(poid == 21 && joueur.getMain().getPaquet().size() == 2)
					joueur.setMise(croupier.getMise() instanceof VingtEtUn ? new Egalite() : new VingtEtUn());
				/*
				 * Le joueur a un poid supperieur au croupier, mais pas un blackjack
				 */
				else
					joueur.setMise(new Gagner());
				croupier.setMise(new Perte());
			}
			System.out.println((joueur instanceof Humain ? "humain " : "ia ") + " : " + joueur.getMise().getNomMise());
		}
		System.out.println("croupier:" + croupier.getMise().getNomMise());
	}

	/**
	 * La partie se termine lors que tous les joueurs passent leur tour,
	 * ce qui fait que la partie ne se termine pas automatiquement mais a un moment choisi par les joueurs
	 * @return les gagnants (croupier si personne ne gagne)
	 */
	public ArrayList<Joueur> getWinner() {
		this.setWinner();
		ArrayList<Joueur> gagnants = new ArrayList<Joueur>();
		for(Joueur j : joueurs) 
			if(j.getMise() instanceof Gagner || j.getMise() instanceof VingtEtUn)
				gagnants.add(j);
		if(gagnants.isEmpty())
			gagnants.add(croupier);
		return gagnants;
	}

	public String toString() {
		String str ="";
		int i = 1;
		str += croupier;
		for(Joueur j : this.joueurs) {
			str += "Joueur n."+i+" : " + j.getMain().getPoidsTotal() + "\n";
			str += j.getMain();
			i++;
		}
		return str;
	}



}
