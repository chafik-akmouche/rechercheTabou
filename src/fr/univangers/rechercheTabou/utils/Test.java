package fr.univangers.rechercheTabou.utils;

import java.util.ArrayList;

public class Test {	
	public static final int NBR_EQUIPE = 6;
	
	public static void main(String[] args) {
		Tournoi tournoi = new Tournoi(NBR_EQUIPE);
		ArrayList<Solution> solutions = new ArrayList<Solution>();
	/* --------------------------------------------------------------------------------------------------- */
	
		System.out.println("## Nombre d'équipes : " + NBR_EQUIPE);
		System.out.println("## Nombre de semaines : " + tournoi.getNbr_semaine());
		System.out.println("## Nombre de périodes : " + tournoi.getNbr_periode());		
		/* --------------------------------------------------------------------------------------------------- */
		
		tournoi.createPlanning();
		affichageTournoi(tournoi);
		/* --------------------------------------------------------------------------------------------------- */
		
		System.out.print("## Matchs à jouer (" + tournoi.getMatchs().length+"): ");
		tournoi.generateMatch();
		afficheMatchsAjouer(tournoi);
		/* --------------------------------------------------------------------------------------------------- */
		
		tournoi.init();
		affichageTournoi(tournoi);	
		System.out.println("## Coût de la configuration initiale = " + Coup.coutConfiguration(tournoi));
		/* --------------------------------------------------------------------------------------------------- */
		
		int it=0;
		while(it < 300000 && Coup.coutConfiguration(tournoi) > 0) {
			Mouvement meilleurVoisin = Tabou.explorationVoisinage(tournoi);
			/*System.out.println("Meilleur voisin = "+meilleurVoisin.getCoup()+" ["+
								meilleurVoisin.getMatch1().getEquipe1()+"-"+meilleurVoisin.getMatch1().getEquipe2()+"] ["+
								meilleurVoisin.getMatch2().getEquipe1()+"-"+meilleurVoisin.getMatch2().getEquipe2()+"]");*/
			//affichageTournoi(tournoi);
			Tabou.echangeMatch(tournoi, meilleurVoisin.getMatch1(), meilleurVoisin.getMatch2());
			//System.out.println("Après swap : ");
			affichageTournoi(tournoi);
			System.out.println("## Coût = " + Coup.coutConfiguration(tournoi));
			solutions.add(new Solution(tournoi, Coup.coutConfiguration(tournoi)));
			it++;
		}
		
		System.out.println("====================================================");
		System.out.println("MEILLEURE SOLUTION : " + Tabou.meilleurSolution(solutions).getCoup() + " | iteration=" + it);
		affichageTournoi(Tabou.meilleurSolution(solutions).getTournoi());
		System.out.println("====================================================");

	}

	// affichage du tournoi
	public static void affichageTournoi (Tournoi tournoi) {
		for (int i=0; i<tournoi.getNbr_semaine(); i++) {
			for (int j=0; j<tournoi.getNbr_periode(); j++) {
				if (j != tournoi.getNbr_periode()-1) {						
					System.out.print("["+tournoi.getTournoi()[i][j].getEquipe1()+" "+tournoi.getTournoi()[i][j].getEquipe2()+"]\t");
				} else {
					System.out.println("["+tournoi.getTournoi()[i][j].getEquipe1()+" "+tournoi.getTournoi()[i][j].getEquipe2()+"]\t");
				}				
			}
		}			
	}
		
	// affichage des matchs à jouer
	public static void afficheMatchsAjouer(Tournoi tournoi) {
		for (int x=0; x<tournoi.getMatchs().length; x++) {			
			System.out.print("["+tournoi.getMatchs()[x].getEquipe1()+"-"+tournoi.getMatchs()[x].getEquipe2()+"]");
		}
		System.out.println("\n");
	}
	
	public static int getNbrEquipe() {
		return NBR_EQUIPE;
	}
	
}
