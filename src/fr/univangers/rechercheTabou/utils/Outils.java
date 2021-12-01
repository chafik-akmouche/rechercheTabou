package fr.univangers.rechercheTabou.utils;

import java.util.ArrayList;
import java.util.Random;

public class Outils {
	
	public static Solution meilleurSolution(ArrayList<Solution> solutions){
		Solution bestSolution = solutions.get(0);
		for(int i=0; i<solutions.size(); i++) {
			if(bestSolution.getCoup() > solutions.get(i).getCoup()) {
				bestSolution = solutions.get(i);
			}
		}
		return bestSolution;
	}
	
	// renvoie le meilleur voisin après les avoir testé
	public static Solution meilleurVoisin(ArrayList<Solution> voisins) {
		Solution meilleurVoisin = voisins.get(0);
		for (int i=0; i<voisins.size(); i++) {
			if(meilleurVoisin.getCoup() > voisins.get(i).getCoup()) {
				meilleurVoisin = voisins.get(i);
			}
		}
		return meilleurVoisin;
	}
	
	// exploration voisinage
	public static Mouvement explorationVoisinage(Tournoi tournoi) {
		ArrayList<Match> sousEnsemble = sousEnsembleMatch(tournoi); // liste contenant le sous-ensemble sélectionné
		/*System.out.println("## sous ensemble : "+sousEnsemble.size());
		for(int i=0; i<sousEnsemble.size(); i++) {
				System.out.println("["+sousEnsemble.get(i).getEquipe1()+" "+sousEnsemble.get(i).getEquipe2()+"]");
		}*/
		
		ArrayList<Mouvement> voisinsTestes = new ArrayList<Mouvement>(); //liste contenant les configurations testées
		Mouvement bestVoisin = null;
		
		int nb_voisinsTestes = 0;
		while( nb_voisinsTestes < ((sousEnsemble.size()*sousEnsemble.size())/4) ) {
			Match match1 = selectMatch1(tournoi, sousEnsemble);
			Match match2 = selectMatch2(tournoi, sousEnsemble, match1);
			int iter=0;
			while((match1==null || match2==null) && iter<200) {
				match1 = selectMatch1(tournoi, sousEnsemble);
				match2 = selectMatch2(tournoi, sousEnsemble, match1);
				iter++;
			}
			if (match1 != null && match2 != null) {
				//System.out.println("---------------------------------------------------");
				//System.out.println("***** [" + match1.getEquipe1()+" "+match1.getEquipe2()+"] <-> ["+match2.getEquipe1()+" "+match2.getEquipe2()+"]");
				echangeMatch(tournoi, match1, match2);
				//System.out.println("Coup de cet echange = "+Coup.coutConfiguration(tournoi));
				voisinsTestes.add(new Mouvement(match1, match2, Coup.coutConfiguration(tournoi)));
				echangeMatch(tournoi, match1, match2); // err-it akken i yella
				//System.out.println("---------------------------------------------------");
			} else { 
				System.out.println("Oops <-> 200 iterations <-> Match1==null ou Match2==null");
			}
			nb_voisinsTestes++;
		}
		
		bestVoisin = voisinsTestes.get(0);
		for(int i=0; i<voisinsTestes.size(); i++) {
			if(bestVoisin.getCoup() > voisinsTestes.get(i).getCoup()) {
				bestVoisin = voisinsTestes.get(i);
			}
		}
		
		return bestVoisin;	
	}
	
	// sélectionne et renvoie un sous-ensemble de matchs
	@SuppressWarnings("unused")
	public static ArrayList<Match> sousEnsembleMatch(Tournoi tournoi) {
		ArrayList<Match> sousEnsembleMatch = new ArrayList<Match>();
		Match match = selectMatch(tournoi);
		
		if(((Test.NBR_EQUIPE*Test.NBR_EQUIPE)-Test.NBR_EQUIPE) > 1) {
			int cpt=0;
			while(cpt < (((Test.NBR_EQUIPE*Test.NBR_EQUIPE)-Test.NBR_EQUIPE)/4)) {
				if(!sousEnsembleMatch.contains(match)) {
					sousEnsembleMatch.add(match);
					cpt++;
				} else {
					match = selectMatch(tournoi);
				}
			}
		} 
		else {
			int tcpt=0;
			while(tcpt < (((Test.NBR_EQUIPE*Test.NBR_EQUIPE)-(Test.NBR_EQUIPE))/2)) {
				if(!sousEnsembleMatch.contains(match)) {
					sousEnsembleMatch.add(match);
					tcpt++;
				} else {
					match = selectMatch(tournoi);
				}	
			}
		}
		return sousEnsembleMatch;
	}
	
	// selectionner aléatoirement un match à partir de la liste de tous les matchs
	public static Match selectMatch(Tournoi tournoi) {
		Random rand = new Random();
		return tournoi.getMatchs()[rand.nextInt(tournoi.getMatchs().length)];
	}
	
	// selectionner un match à partir d'un sous ensemble
	public static Match selectMatch1(Tournoi tournoi, ArrayList<Match> sousEnsemble) {
		Match match = selectMatchFromSubset(sousEnsemble);
		//System.out.println("Match 1 : ["+match.getEquipe1()+" "+match.getEquipe2()+"]");
		int iter = 0;
		while(ligneValable(tournoi, match.getPosX(), match) && colonneValable(tournoi, match.getPosY(), match) && iter<200) {
			match = selectMatchFromSubset(sousEnsemble);
			iter++;
		}
		return match;
	}
	
	// selectionner un deuxieme match différent du premier
	public static Match selectMatch2(Tournoi tournoi, ArrayList<Match> sousEnsemble, Match match1) {
		Match match2 = selectMatchFromSubset(sousEnsemble);
		//System.out.println("Match 2 : ["+match2.getEquipe1()+" "+match2.getEquipe2()+"]");
		int iter = 0;
		while((ligneValable(tournoi, match2.getPosX(), match2) && colonneValable(tournoi, match2.getPosY(), match2) && iter<200) || match2==match1) {
			match2 = selectMatchFromSubset(sousEnsemble);
		}
		return match2;
	}
	
	// selectionner aléatoirement un match à partir d'un sous-ensemble
	public static Match selectMatchFromSubset(ArrayList<Match> sousEnsemble) {
		Random rand = new Random();
		return sousEnsemble.get(rand.nextInt(sousEnsemble.size()));
	}
	
	// verifier si une ligne de la matrice est valable pour positionner un match
	public static boolean ligneValable(Tournoi tournoi, int i, Match match) {
		boolean valable = true;
		for(int j=0; j<tournoi.getNbr_periode(); j++) {
			if((match.getEquipe1() == tournoi.getTournoi()[i][j].getEquipe1()) || (match.getEquipe1() == tournoi.getTournoi()[i][j].getEquipe2()) || 
			   (match.getEquipe2() == tournoi.getTournoi()[i][j].getEquipe1()) || (match.getEquipe2() == tournoi.getTournoi()[i][j].getEquipe2())) {
				valable = false;
				//System.out.println("["+match.getEquipe1()+" "+match.getEquipe2()+"] viole la contrainte ligne " + i);
				break;
			}
		}
		return valable;
	}
	
	// verifier si une ligne de la matrice est valable pour positionner un match
	public static boolean colonneValable(Tournoi tournoi, int j, Match match) {
		boolean valable = true;
		int occurrence = 0;
		for(int i=0; i<tournoi.getNbr_semaine(); i++) {
			if((match.getEquipe1() == tournoi.getTournoi()[i][j].getEquipe1()) || (match.getEquipe1() == tournoi.getTournoi()[i][j].getEquipe2()) || 
			   (match.getEquipe2() == tournoi.getTournoi()[i][j].getEquipe1()) || (match.getEquipe2() == tournoi.getTournoi()[i][j].getEquipe2())) {
				occurrence++;
				if (occurrence >= 2) {			
					valable = false;
					//System.out.println("["+match.getEquipe1()+" "+match.getEquipe2()+"] viole la contrainte colonne " + j);
					break;
				}
			}
		}
		return valable;
	}
	
	// echanger deux matchs
	public static Tournoi echangeMatch(Tournoi tournoi, Match match1, Match match2) {
		int match1PsX = match1.getPosX();
		int match1PsY = match1.getPosY();
		
		int match2PsX = match2.getPosX();
		int match2PsY = match2.getPosY();
		
		tournoi.getTournoi()[match2PsX][match2PsY] = match1;
		tournoi.getTournoi()[match1PsX][match1PsY] = match2;
		match1.setPosX(match2PsX);
		match1.setPosY(match2PsY);
		match2.setPosX(match1PsX);
		match2.setPosY(match1PsY);
		return tournoi;
	}

}
