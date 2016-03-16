package edu.avans.hartigehap.service.impl;

import edu.avans.hartigehap.domain.Reservation;
import edu.avans.hartigehap.service.State;

public class ConfirmedState extends State{

	@Override
	public void setReservationStatus(Reservation reservation) {
		// TODO Auto-generated method stub
		reservation.setState(new ConceptState);
	}

}
