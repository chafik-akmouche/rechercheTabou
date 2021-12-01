package fr.univangers.rechercheTabou.utils;

public class Mouvement {
	
	private Match match1;
	private Match match2;
	private int coup;
	
	public Mouvement(Match match1, Match match2) {
		this.match1 = match1;
		this.match2 = match2;
	}
	
	public Mouvement(Match match1, Match match2, int coup) {
		this.match1 = match1;
		this.match2 = match2;
		this.coup = coup;
	}

	public int getCoup() {
		return coup;
	}

	public void setCoup(int coup) {
		this.coup = coup;
	}

	public Match getMatch1() {
		return match1;
	}

	public void setMatch1(Match match1) {
		this.match1 = match1;
	}

	public Match getMatch2() {
		return match2;
	}

	public void setMatch2(Match match2) {
		this.match2 = match2;
	}
	
}
