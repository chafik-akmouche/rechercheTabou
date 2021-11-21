package fr.univangers.rechercheTabou.utils;

import java.util.ArrayList;

public class Test {	
	public static final int NBR_EQUIPE = 8;
	
	public static void main(String[] args) {
		Tournoi tournoi = new Tournoi(NBR_EQUIPE);
		long startTime = 0;
		long endTime = 0;
		//ArrayList<Solution> solutions = new ArrayList<Solution>();
		ArrayList<Tournoi> listeTabou = new ArrayList<Tournoi>();
		Solution bestSolution;
	/* --------------------------------------------------------------------------------------------------- */
	
		System.out.println("## Nombre d'équipes : " + NBR_EQUIPE);
		System.out.println("## Nombre de semaines : " + tournoi.getNbr_semaine());
		System.out.println("## Nombre de périodes : " + tournoi.getNbr_periode());		
		/* --------------------------------------------------------------------------------------------------- */
		
		tournoi.creerPlanning();
		afficherTournoi(tournoi);
		/* --------------------------------------------------------------------------------------------------- */
		
		System.out.print("## Matchs à jouer (" + tournoi.getMatchs().length+"): ");
		tournoi.genererMatch();
		afficherMatchsAjouer(tournoi);
		/* --------------------------------------------------------------------------------------------------- */
		
		tournoi.init();
		// s = s0
		bestSolution = new Solution(tournoi, Coup.coutConfiguration(tournoi));
		//affichageTournoi(tournoi);
		afficherTournoi(bestSolution.getTournoi());
		System.out.println("## f(s0) = " + bestSolution.getCoup());
		System.out.println("---------------------------------------------------");
		/* --------------------------------------------------------------------------------------------------- */
		
		int it=0;
		while(/*it < 1000 &&*/ bestSolution.getCoup() > 0 /*Coup.coutConfiguration(tournoi) > 0*/) {
			startTime = System.nanoTime();
			Mouvement meilleurVoisin = Tabou.explorationVoisinage(tournoi);
			/*System.out.println("Meilleur voisin = "+meilleurVoisin.getCoup()+" ["+
								meilleurVoisin.getMatch1().getEquipe1()+"-"+meilleurVoisin.getMatch1().getEquipe2()+"] ["+
								meilleurVoisin.getMatch2().getEquipe1()+"-"+meilleurVoisin.getMatch2().getEquipe2()+"]");*/
			//affichageTournoi(tournoi);
			Tabou.echangeMatch(tournoi, meilleurVoisin.getMatch1(), meilleurVoisin.getMatch2());
			//System.out.println("Après swap : ");
			//affichageTournoi(tournoi);
			//System.out.println("## Coût = " + Coup.coutConfiguration(tournoi));
			//solutions.add(new Solution(tournoi, Coup.coutConfiguration(tournoi)));
			if(Coup.coutConfiguration(tournoi) < bestSolution.getCoup()) {
				if(Coup.coutConfiguration(tournoi) <= bestSolution.getCoup()/2) {
					bestSolution = new Solution(tournoi, Coup.coutConfiguration(tournoi));
					//listeTabou.add(bestSolution.getTournoi());
					afficherTournoi(bestSolution.getTournoi());
					System.out.println("## f(s') = " + bestSolution.getCoup()+" mouvement = "+it);
					System.out.println("f(s') très amélioré => prendre comme solution sans vérifier la liste tabou");
					System.out.println("---------------------------------------------------");
				} else {
					if(!listeTabou.contains(tournoi)) {
						bestSolution = new Solution(tournoi, Coup.coutConfiguration(tournoi));
						afficherTournoi(bestSolution.getTournoi());
						System.out.println("## f(s') = " + bestSolution.getCoup()+" mouvement = "+it);
						//listeTabou.add(bestSolution.getTournoi());
						System.out.println("Ajout à la liste tabou...");
						System.out.println("---------------------------------------------------");
					} else {
						//it++;
						System.out.println("Configuration interdite !");
					}
				}
				//it++;
			}
			//afficherTournoi(bestSolution.getTournoi());
			//System.out.println("## Coût = " + bestSolution.getCoup());
			it++;
		}
		endTime = System.nanoTime();

		//System.out.println("MEILLEURE SOLUTION\nCoup : " + Tabou.meilleurSolution(solutions).getCoup() + " | iteration = " + it +" | temps d'execution = "+(endTime-startTime)+" ns");
		//affichageTournoi(Tabou.meilleurSolution(solutions).getTournoi());
		System.out.println("MEILLEURE SOLUTION\nf(s*) = " + bestSolution.getCoup() + " | iteration = " + it +" | temps d'execution = "+(endTime-startTime)+" ns");
		afficherTournoi(bestSolution.getTournoi());

	}

	// affichage du tournoi
	public static void afficherTournoi (Tournoi tournoi) {
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
	public static void afficherMatchsAjouer(Tournoi tournoi) {
		for (int x=0; x<tournoi.getMatchs().length; x++) {			
			System.out.print("["+tournoi.getMatchs()[x].getEquipe1()+"-"+tournoi.getMatchs()[x].getEquipe2()+"]");
		}
		System.out.println("\n");
	}
	
	public static int getNbrEquipe() {
		return NBR_EQUIPE;
	}
	
}
