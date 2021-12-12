package fr.univangers.rechercheTabou.utils;

public class Coup {
	private static int poidS = 2;
	private static int poidP = 1;
	
	// cout d'une configuration (solution)
	public static int coutConfiguration (Tournoi tournoi) {
		int coutConfiguration = 0;
		for (int i=0; i<tournoi.getNbr_semaine(); i++) {
			for (int j=0; j<tournoi.getNbr_periode(); j++) {
				coutConfiguration = coutConfiguration + pinaliteMatch(tournoi, tournoi.getTournoi()[i][j], i, j);
			}
		}
		return coutConfiguration;
	}
	
	// pinalité d'un match
	public static int pinaliteMatch (Tournoi tournoi, Match match, int ligne, int colonne) {
		//System.out.println("["+match.getEquipe1()+"-"+match.getEquipe2()+"] = " + (pinaliteEquipe(tournoi, match.getEquipe1(), ligne, colonne) + pinaliteEquipe(tournoi, match.getEquipe2(), ligne, colonne)));
		return (pinaliteEquipe(tournoi, match.getEquipe1(), ligne, colonne) + pinaliteEquipe(tournoi, match.getEquipe2(), ligne, colonne));
	}
	
	// pinalité d'une equipe
	public static int pinaliteEquipe (Tournoi tournoi, int equipe, int ligne, int colonne) {
		int occPeriode = 0;
		for(int i=0; i<tournoi.getNbr_semaine(); i++) {
			if(equipe == tournoi.getTournoi()[i][colonne].getEquipe1() || equipe == tournoi.getTournoi()[i][colonne].getEquipe2()) {
				occPeriode++;
			}
		}
		occPeriode = occPeriode-1;
		if(occPeriode > 1) {
			occPeriode = occPeriode-1;
		} else {
			occPeriode = 0;
		}		
		int occSemaine = 0;
		for(int j=0; j<tournoi.getNbr_periode(); j++) {
			if(equipe == tournoi.getTournoi()[ligne][j].getEquipe1() || equipe == tournoi.getTournoi()[ligne][j].getEquipe2()) {
				occSemaine++;
			}
		}
		occSemaine = occSemaine-1;
		return ((poidP*occPeriode) + (poidS*occSemaine));
	}

}
