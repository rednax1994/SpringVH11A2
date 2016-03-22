package edu.avans.hartigehap.domain.states;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import edu.avans.hartigehap.domain.exception.StateException;
import edu.avans.hartigehap.domain.reservationFactory.Reservation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Getter
@Setter
@NoArgsConstructor
public class ConceptState extends ReservationStatus {
    private static final long serialVersionUID = 1L;
    
    public ConceptState(Reservation reservation) {
        super(reservation);
        reservationStatusId = ReservationStatusId.CONCEPT;
    }
    
    @Override
    public void acceptReservation() throws StateException {
        getReservation().setCurrentState(new ApprovedState(getReservation()));
    }
    
    @Override
    public void endReservation() throws StateException {
        throw new StateException("You are not allowed to go into this state from the Concept State");
        
    }
    
}
