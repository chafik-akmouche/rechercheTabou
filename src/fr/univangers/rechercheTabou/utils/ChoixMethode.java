package fr.univangers.rechercheTabou.utils;

import java.util.ArrayList;

public class ChoixMethode {
	
	public static void tabou(Tournoi tournoi, int max_iteration) {		
		long startTime = 0;
		long endTime = 0;
		ArrayList<Mouvement> listeTabou = new ArrayList<Mouvement>();
		Solution bestSolution;		
		bestSolution = new Solution(tournoi, Coup.coutConfiguration(tournoi));
		afficherTournoi(bestSolution.getTournoi());		
		System.out.println("## f(s0) = " + bestSolution.getCoup());
		System.out.println("---------------------------------------------------");
		System.out.println("###############################");
		System.out.println("####### Recherche Tabou #######");
		System.out.println("###############################");
		int it=0;
		while(it < max_iteration && bestSolution.getCoup() > 0) {
			startTime = System.currentTimeMillis();
			Mouvement meilleurMouvement = null;
			meilleurMouvement = Outils.explorationVoisinage(tournoi);
			Outils.echangeMatch(tournoi, meilleurMouvement.getMatch1(), meilleurMouvement.getMatch2());	
			int preEvaluationCoup = Coup.coutConfiguration(tournoi);
			Outils.echangeMatch(tournoi, meilleurMouvement.getMatch1(), meilleurMouvement.getMatch2());	
			
				if(preEvaluationCoup <= bestSolution.getCoup()/2) {
					Outils.echangeMatch(tournoi, meilleurMouvement.getMatch1(), meilleurMouvement.getMatch2());
					bestSolution = new Solution(tournoi, Coup.coutConfiguration(tournoi));					
					afficherTournoi(bestSolution.getTournoi());
					System.out.println("## f(s') = " + bestSolution.getCoup()+" mouvement = "+it+" | taille de la liste tabou = "+listeTabou.size());
					System.out.println("Solution très améliorante => accepter la solution même si elle est dans la liste tabou");
					
					if(!contient(listeTabou, meilleurMouvement)) {
						listeTabou.add(meilleurMouvement);
						System.out.println("Ajout du mouvement ["+meilleurMouvement.getMatch1().getEquipe1()+" "+meilleurMouvement.getMatch1().getEquipe2()+
							"] <-> ["+meilleurMouvement.getMatch2().getEquipe1()+" "+meilleurMouvement.getMatch2().getEquipe2()+"] à la liste tabou");
					} else {
						System.out.println("Mouvement ["+meilleurMouvement.getMatch1().getEquipe1()+" "+meilleurMouvement.getMatch1().getEquipe2()+
							"] <-> ["+meilleurMouvement.getMatch2().getEquipe1()+" "+meilleurMouvement.getMatch2().getEquipe2()+"] existe déjà dans la liste tabou");
					}
					
					System.out.println("---------------------------------------------------");
				} else {
					if(!contient(listeTabou, meilleurMouvement)) {
						
						Outils.echangeMatch(tournoi, meilleurMouvement.getMatch1(), meilleurMouvement.getMatch2());	
						Outils.echangeMatch(tournoi, meilleurMouvement.getMatch1(), meilleurMouvement.getMatch2());
						
						if(preEvaluationCoup >= bestSolution.getCoup() ) {
							System.out.println("Le cout ne s'améliore pas !");
							if(listeTabou.size() > 0 && listeTabou.get(0)!=null) {
								System.out.println("Levée du statut tabou au mouvement ["+listeTabou.get(0).getMatch1().getEquipe1()+" "+listeTabou.get(0).getMatch1().getEquipe2()+
													"] <-> ["+listeTabou.get(0).getMatch2().getEquipe1()+" "+listeTabou.get(0).getMatch2().getEquipe2()+"]");
								listeTabou.remove(0);
							}
						}
						
						Outils.echangeMatch(tournoi, meilleurMouvement.getMatch1(), meilleurMouvement.getMatch2());
						bestSolution = new Solution(tournoi, Coup.coutConfiguration(tournoi));
						afficherTournoi(bestSolution.getTournoi());
						System.out.println("## f(s') = " + bestSolution.getCoup()+" | mouvement = "+it+" | taille de la liste tabou = "+listeTabou.size());
						
						listeTabou.add(meilleurMouvement);
						
						System.out.println("## Ajout du mouvement ["+meilleurMouvement.getMatch1().getEquipe1()+" "+meilleurMouvement.getMatch1().getEquipe2()+
								"] <-> ["+meilleurMouvement.getMatch2().getEquipe1()+" "+meilleurMouvement.getMatch2().getEquipe2()+"] à la liste tabou");
						System.out.println("---------------------------------------------------");
					} else {
						System.out.println("## Mouvement tabou ["+meilleurMouvement.getMatch1().getEquipe1()+" "+meilleurMouvement.getMatch1().getEquipe2()+
								"] <-> ["+meilleurMouvement.getMatch2().getEquipe1()+" "+meilleurMouvement.getMatch2().getEquipe2()+"]");
					}
				}
			it++;
		}
		endTime = System.currentTimeMillis();
		System.out.println("MEILLEURE SOLUTION\nf(s*) = " + bestSolution.getCoup() + " | iteration = " + it +" | temps d'execution = "+(endTime-startTime)+" ms");
		afficherTournoi(bestSolution.getTournoi());
	}
	
	public static void descente(Tournoi tournoi, int relance) {
		long startTime = 0;
		long endTime = 0;
		Solution bestSolution;		
		bestSolution = new Solution(tournoi, Coup.coutConfiguration(tournoi));
		afficherTournoi(bestSolution.getTournoi());		
		System.out.println("## f(s0) = " + bestSolution.getCoup());
		System.out.println("---------------------------------------------------");
		System.out.println("##########################################");
		System.out.println("####### Recherche Descente stricte #######");
		System.out.println("##########################################");
		int it=0;
		while(it < relance && bestSolution.getCoup() > 0) {
			startTime = System.currentTimeMillis();
			Mouvement meilleurVoisin = Outils.explorationVoisinage(tournoi);
			Outils.echangeMatch(tournoi, meilleurVoisin.getMatch1(), meilleurVoisin.getMatch2());
			if(Coup.coutConfiguration(tournoi) < bestSolution.getCoup()) {
				bestSolution = new Solution(tournoi, Coup.coutConfiguration(tournoi));
				afficherTournoi(bestSolution.getTournoi());
				System.out.println("## f(s') = " + bestSolution.getCoup()+" | mouvement = "+it+" | (S* = S')");
				System.out.println("---------------------------------------------------");
			}
			it++;
		}
		endTime = System.currentTimeMillis();
		System.out.println("MEILLEURE SOLUTION\nf(s*) = " + bestSolution.getCoup() + " | iteration = " + it +" | temps d'execution = "+(endTime-startTime)+" ms");
		afficherTournoi(bestSolution.getTournoi());
	}

	public static boolean contient (ArrayList<Mouvement> liste, Mouvement element) {
		boolean trouve = false;
		for (int i=0; i<liste.size(); i++) {
			if(liste.get(i).getMatch1() == element.getMatch1() && liste.get(i).getMatch2() == element.getMatch2()) {				
				trouve = true;
				break;
			}
		}
		return trouve;
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
}
