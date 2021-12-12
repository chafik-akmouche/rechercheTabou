package fr.univangers.rechercheTabou.utils;

public class Solution {
	private Tournoi tournoi;
	private int coup;
	public Solution(Tournoi tournoi, int coup) {
		this.tournoi = tournoi;
		this.coup = coup;
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
}
