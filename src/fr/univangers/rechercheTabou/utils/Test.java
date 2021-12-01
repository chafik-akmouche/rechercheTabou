package fr.univangers.rechercheTabou.utils;

public class Test {	
	public static final int NBR_EQUIPE = 4;
	public static final int MAX_ITERATION = 100;
	public static final int NBR_RELANCE = 2; //pour la méthode de déscente
	
	/*
	 * Reste à faire :
	 * corriger la vérification listeTabou.contains(mouvement)
	 * retrait d'un mouvement tabou de la liste
	 * acceptation d'une solution même si elle dans la liste tabou (si elle est très améliorante)
	 */
	
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
		
		/*
		 * Recherche tabou :
		 * repeter
		 * 		diviser le planning en plusieurs sous-ensembles, tester les sous-ensembles, évaluer le gain de chaque solution locale
		 * 		sélectionner le meilleur voisin (s') même s'il n'améliore pas, vérifier s'il n'est pas dans la liste tabou, effectuer le mouvement, évaluer le coup f(s')
		 * 		ajouter le mouvement à la liste tabou
		 * jusqu'à (condition d'arrêt) 
		 */
		ChoixMethode.tabou(tournoi, MAX_ITERATION);
		
		/*
		 * diviser le planning en plusieurs sous-ensembles, tester les sous-ensembles, évaluer le gain de chaque solution locale
		 * sélectionner le meilleur voisin (s') qui améliore le coup, effectuer le mouvement, évaluer le coup f(s')
		 * arrêter
		 */
		//ChoixMethode.descente(tournoi, NBR_RELANCE);
	}
}
