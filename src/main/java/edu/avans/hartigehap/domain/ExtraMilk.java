package edu.avans.hartigehap.domain;

public class ExtraMilk extends CocktailDecorator{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Cocktail cocktail;

	public ExtraMilk(Cocktail cocktail) {
		this.cocktail = cocktail;
	}
	
	public String getDescription(){
		return cocktail.getDescription() + ", Extra Milk";
	}
	
	public int cost(){
		return 1 + cocktail.cost();
	}
}
