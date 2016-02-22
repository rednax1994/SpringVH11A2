package edu.avans.hartigehap.domain;

public class Margarita extends Cocktail{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Margarita(){
		description = "Margarita";
	}

	public int cost(){
		return 4;
	}
}
