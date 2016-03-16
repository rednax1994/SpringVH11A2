package edu.avans.hartigehap.domain.states;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import edu.avans.hartigehap.domain.Reservation;
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
public class StateConcept extends ReservationState{
    private static final long serialVersionUID = 1L;
    
    public StateConcept(Reservation reservation, String name){
        super(reservation, name);
    }
    
    public void getCurrentState(){
        getReservation().setCurrentState(new StateConcept());
        
    }
    
}
