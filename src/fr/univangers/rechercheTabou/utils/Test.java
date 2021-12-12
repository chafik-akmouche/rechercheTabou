package fr.univangers.rechercheTabou.utils;

public class Test {	
	public static final int NBR_EQUIPE = 8;
	public static final int MAX_ITERATION = 500;
	public static final int NBR_RELANCE = 500;
	
	public static void main(String[] args) {
		
		Tournoi tournoi = new Tournoi(NBR_EQUIPE);
		System.out.println("## Nombre d'équipes : " + NBR_EQUIPE);
		System.out.println("## Nombre de semaines : " + tournoi.getNbr_semaine());
		System.out.println("## Nombre de périodes : " + tournoi.getNbr_periode());
		tournoi.creerPlanning();
		ChoixMethode.afficherTournoi(tournoi);
		System.out.print("## Matchs à jouer (" + tournoi.getMatchs().length+"): ");
		tournoi.genererMatch();
		ChoixMethode.afficherMatchsAjouer(tournoi);
		tournoi.init();
		
		ChoixMethode.tabou(tournoi, MAX_ITERATION);
		//ChoixMethode.descente(tournoi, NBR_RELANCE);
	}
}
