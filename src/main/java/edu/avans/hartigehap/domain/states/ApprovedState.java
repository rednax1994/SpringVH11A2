package edu.avans.hartigehap.domain.states;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import edu.avans.hartigehap.domain.exception.StateException;
import edu.avans.hartigehap.domain.reservationfactory.Reservation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Getter
@Setter
@NoArgsConstructor
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
