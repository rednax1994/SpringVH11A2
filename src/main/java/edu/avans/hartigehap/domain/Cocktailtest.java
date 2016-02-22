package edu.avans.hartigehap.domain;

public class Cocktailtest {

	
	public static void main(String args[]){
		Cocktail cocktail = new BloodyMary();
		
		System.out.println(cocktail.getDescription() + "€" + cocktail.cost());
		
		Cocktail cocktail2 = new Margarita();
		cocktail2 = new ExtraMilk(cocktail2);
		cocktail2 = new ExtraRum(cocktail2);
		cocktail2 = new ExtraRum(cocktail2);
		
		System.out.println(cocktail2.getDescription() + "€" + cocktail2.cost());
		
	}
}
