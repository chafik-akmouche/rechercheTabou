package fr.univangers.rechercheTabou.utils;

public class Solution {
	private Tournoi tournoi;
	private int coup;
	//private Mouvement mouvement;
	public Solution(Tournoi tournoi, int coup/*, Mouvement mouvement*/) {
		this.tournoi = tournoi;
		this.coup = coup;
		//this.mouvement = mouvement;
	}

	public Tournoi getTournoi() {
		return tournoi;
	}

	public void setTournoi(Tournoi tournoi) {
		this.tournoi = tournoi;
	}

	public int getCoup() {
		return coup;
	}

	public void setCoup(int coup) {
		this.coup = coup;
	}

//	public Mouvement getMouvement() {
//		return mouvement;
//	}
//
//	public void setMouvement(Mouvement mouvement) {
//		this.mouvement = mouvement;
//	}

}
