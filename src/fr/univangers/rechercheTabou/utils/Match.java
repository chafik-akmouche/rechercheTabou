package fr.univangers.rechercheTabou.utils;

public class Match {	
	private int equipe1;
	private int equipe2;
	private int posX;
	private int posY;
	
	// constructeur 1
	public Match(int equipe1, int equipe2) {
		this.equipe1 = equipe1;
		this.equipe2 = equipe2;
	}
	
	// constructeur 2
	public Match(int equipe1, int equipe2, int posX, int posY) {
		this.equipe1 = equipe1;
		this.equipe2 = equipe2;
		this.posX = posX;
		this.posY = posY;
	}

	public int getEquipe1() {
		return equipe1;
	}

	public void setEquipe1(int equipe1) {
		this.equipe1 = equipe1;
	}

	public int getEquipe2() {
		return equipe2;
	}

	public void setEquipe2(int equipe2) {
		this.equipe2 = equipe2;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}
}
