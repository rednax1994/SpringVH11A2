package edu.avans.hartigehap.domain.states;

import edu.avans.hartigehap.domain.Reservation;
import edu.avans.hartigehap.domain.StateException;

public class StateApproved extends ReservationStatus {
    private static final long serialVersionUID = 1L;
    
    public StateApproved(Reservation reservation) {
        super(reservation);
        reservationStatusId = ReservationStatusId.APPROVED;
    }
    
    @Override
    public void acceptReservation() throws StateException {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void endReservation() throws StateException {
        // TODO Auto-generated method stub
        
    }
    
}
