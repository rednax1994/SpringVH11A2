package edu.avans.hartigehap.domain.states;

import edu.avans.hartigehap.domain.StateException;
import edu.avans.hartigehap.domain.reservationFactory.Reservation;

public class ApprovedState extends ReservationStatus {
    private static final long serialVersionUID = 1L;
    
    public ApprovedState(Reservation reservation) {
        super(reservation);
        reservationStatusId = ReservationStatusId.APPROVED;
    }
    
    @Override
    public void acceptReservation() throws StateException {
        throw new StateException("You are not allowed to go into this state from the approved state");
        
    }
    
    @Override
    public void endReservation() throws StateException {
        getReservation().setCurrentState(new FinishedState(getReservation()));
    }
    
}
