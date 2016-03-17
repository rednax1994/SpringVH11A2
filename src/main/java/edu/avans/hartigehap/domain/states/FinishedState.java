package edu.avans.hartigehap.domain.states;

import edu.avans.hartigehap.domain.StateException;
import edu.avans.hartigehap.domain.reservationFactory.Reservation;

public class FinishedState extends ReservationStatus {
    private static final long serialVersionUID = 1L;
    
    public FinishedState(Reservation reservation) {
        super(reservation);
        reservationStatusId = ReservationStatusId.FINISHED;
    }
    
    @Override
    public void acceptReservation() throws StateException {
        throw new StateException("You are not allowed to go into this state from the finished state");
        
    }
    
    @Override
    public void endReservation() throws StateException {
        throw new StateException("You are not allowed to go into this state from the finished state");
        
    }
    
}
