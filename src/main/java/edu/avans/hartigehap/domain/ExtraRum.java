package edu.avans.hartigehap.domain;

public class ExtraRum extends CocktailDecorator{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Cocktail cocktail;
	
	public ExtraRum(Cocktail cocktail){
		this.cocktail = cocktail;
	}
	
	public String getDescription()
	{
		return cocktail.getDescription() + ", Extra Rum";
	}
	
	public int cost(){
		return 2 + cocktail.cost();
	}
}
