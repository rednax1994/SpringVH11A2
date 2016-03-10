package edu.avans.hartigehap.domain;

import java.util.Date;

import javax.persistence.OneToOne;

import edu.avans.hartigehap.service.State;

public class Reservation {

	int amountOfPeople;
	@OneToOne
	private State currentState;
	public Date date;

	public void setState(State state){
		currentState = state;	
	}
	
	public State getState(){
		return currentState;
	}
}
