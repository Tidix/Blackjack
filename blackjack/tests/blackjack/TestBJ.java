package blackjack;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import carte.Carte;
import carte.Couleurs;
import carte.Paquet;
import carte.Valeurs;
import joueurs.Croupier;
import joueurs.Humain;
import joueurs.Ia;
import joueurs.Joueur;

class TestBJ {

	Carte dame = new Carte(Valeurs.Dame.toString(),Couleurs.Carreau.toString(),10);
	Carte as1 = new Carte(Valeurs.As.toString(),Couleurs.Pique.toString(),1);
	Carte as2 = new Carte(Valeurs.As.toString(),Couleurs.Coeur.toString(),1);
	Carte deux = new Carte(Valeurs.Deux.toString(),Couleurs.Pique.toString(),2);
	Carte sept = new Carte(Valeurs.Sept.toString(),Couleurs.Trefle.toString(),7);
	Carte quatre = new Carte(Valeurs.Quatre.toString(),Couleurs.Pique.toString(),4);
	
	@Test
	void testWinner() {	
		Joueur j1 = new Humain(dame,as1);
		Joueur j2 = new Humain(sept, quatre);	
		assert(j1.getMain().getPoidsTotal()>j2.getMain().getPoidsTotal());		
	}

	@Test
	void testAddCartd() {
		Joueur j1 = new Humain(dame,deux);
		int poids1 = j1.getMain().getPoidsTotal();
		assertTrue(j1.getMain().getPaquet().size()==2);
		j1.addCarte(sept);
		assertTrue(poids1<j1.getMain().getPoidsTotal());	
		assertTrue(j1.getMain().getPoidsTotal()==19);
		assertTrue(j1.getMain().getPaquet().size()==3);
	}
	
	@Test
	void testAs() {
		Joueur j1 = new Humain(as1,deux);
		assertTrue(j1.getMain().getPoidsTotal()==13);
		j1.addCarte(as2);
		System.out.println(j1.getMain().getPoidsTotal());
		assertTrue(j1.getMain().getPoidsTotal()==14);
	}
	
	@Test
	void testPiocheIA() {
		Paquet p = new Paquet(as1, as2);
		Ia ia = new Ia(as1,as2,p);
		for(int i = 0; i<30;i++) {
			p.addCarte(new Carte("As","Pique",1));
		}
		for(int i = 0;i<30;i++) {
			if(ia.getAction()==0)
				ia.addCarte(p.getPaquet().remove(i));
				
		}
		assertTrue(ia.getMain().getPoidsTotal()<22);
	}
	
	@Test
	void testPiocheCroupier(){
		Paquet p = new Paquet(as1, as2);
		Croupier c = new Croupier(as1,as2);
		for(int i = 0; i<30;i++) {
			p.addCarte(new Carte("As","Pique",1));
		}
		for(int i = 0;i<30;i++) {
			if(c.getAction()==0)
				c.addCarte(p.getPaquet().remove(i));
				
		}
		assertTrue(c.getMain().getPoidsTotal()==17);

	}
	
	@Test
	void TestHumainPiocheAt21(){
		Humain h = new Humain(as1,dame);//15 ou 4
		Paquet p = new Paquet(as1, as2);
		for(int i = 0; i<30;i++) {
			p.addCarte(new Carte("As","Pique",1));
		}
		for(int i = 0;i<30;i++) {
			assertTrue(h.getAction()==1);
				
		}
	}
	
}
