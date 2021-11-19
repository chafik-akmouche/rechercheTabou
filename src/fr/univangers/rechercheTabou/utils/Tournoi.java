package fr.univangers.rechercheTabou.utils;

import java.util.Random;

public class Tournoi {
	private Random rand;
	private int nbr_equipe;
	private int nbr_semaine;
	private int nbr_periode;
	private Match [][] tournoi; // matrice planning
	private Match[] matchs; // liste des matchs à jouer
 	
	// constructeur
	public Tournoi(int nbr_equipe) {
		rand = new Random();
		this.nbr_equipe = nbr_equipe;
		nbr_semaine = nbr_equipe-1;
		nbr_periode = nbr_equipe/2;	
		tournoi = new Match [nbr_semaine][nbr_periode];
		matchs = generateMatch();
	}

	// génération des matchs
	public Match[] generateMatch () {
		int nb_match = 0;
		Match matchsList[] = new Match[((nbr_equipe*nbr_equipe)-nbr_equipe)/2];
		for (int i=0; i<nbr_equipe-1; i++) {
		    for (int j=i+1; j<nbr_equipe; j++) {
		    	matchsList[nb_match] = new Match(i,j);
		    	nb_match++;
		    }
		}
		return matchsList;
	}

	// création du squelette du planning
	public Tournoi createPlanning() {
		System.out.println("## Créatin du squelette du planning");
		for (int i=0; i<nbr_semaine; i++) {
			for (int j=0; j<nbr_periode; j++) {
				tournoi[i][j] = new Match(-1,-1);
			}
		}
		return this;
	}
	
	// configuration initiale - positionner les match aléatoirement sur le planning
	public Tournoi init() {
		System.out.println("## Configuration initiale");
		Match randMatch = null;
		for (int i=0; i<nbr_semaine; i++) {
			for (int j=0; j<nbr_periode; j++) {
				randMatch = selectMatch();
				while(existMatch(randMatch)) {
					randMatch = selectMatch();
				}
				tournoi[i][j] = randMatch;
				tournoi[i][j].setPosX(i);
				tournoi[i][j].setPosY(j);
			}
		}
		return this;
	}
	
	// selectionner aléatoirement un match à partir de la liste des matchs
	public Match selectMatch() {
		return getMatchs()[rand.nextInt(getMatchs().length)];
	}
	
	// verifier si un match existe déjà dans la matrice (planning)
	public boolean existMatch (Match match) {
		boolean exist = false;
		int i=0;
		while (i<nbr_semaine && exist==false) {
			for (int j=0; j<nbr_periode; j++) {
				if (tournoi[i][j] == match) {
					exist = true; break;
				}
			} i++;
		}
		return exist;
	}
	
	// renvoie la liste des matchs (générés)
	public Match[] getMatchs() {
		return matchs;
	}	
	// renvoie la matrice tournoi
	public Match[][] getTournoi() {
		return tournoi;
	}	
	public int getNbr_semaine() {
		return nbr_semaine;
	}	
	public int getNbr_periode() {
		return nbr_periode;
	}
}
