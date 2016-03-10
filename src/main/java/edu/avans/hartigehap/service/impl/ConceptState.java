package edu.avans.hartigehap.service.impl;

import java.util.Date;

import edu.avans.hartigehap.domain.Reservation;
import edu.avans.hartigehap.service.State;

public class ConceptState extends State {

	@Override
	public void setReservationStatus(Reservation reservation) {
		if(reservation.date <= new Date.)
		reservation.setState(new ConfirmedState());
		
	}

}
