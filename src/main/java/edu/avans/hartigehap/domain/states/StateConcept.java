package edu.avans.hartigehap.domain.states;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import edu.avans.hartigehap.domain.Reservation;
import edu.avans.hartigehap.domain.StateException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Getter
@Setter
@ToString(callSuper = true, includeFieldNames = true)
@NoArgsConstructor
public class StateConcept extends ReservationStatus {
    private static final long serialVersionUID = 1L;
    
    public StateConcept(Reservation reservation) {
        super(reservation);
        reservationStatusId = ReservationStatusId.CONCEPT;
    }
    
    public void getCurrentState() {
        getReservation().setCurrentState(new StateConcept());
        
    }
    
    @Override
    public void acceptReservation() throws StateException {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void approveReservation() throws StateException {
        throw new StateException("");
        
    }
    
    @Override
    public void endReservation() throws StateException {
        throw new StateException("");
        
    }
    
}
