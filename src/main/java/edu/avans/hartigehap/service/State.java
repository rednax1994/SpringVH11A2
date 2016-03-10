package edu.avans.hartigehap.service;

import edu.avans.hartigehap.domain.Reservation;

	public abstract class State {
	
	public void setReservationStatus(Reservation reservation){
		throw new IllegalStateException("Action can not be executed from current state");
	}
}
