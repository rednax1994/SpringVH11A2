package edu.avans.hartigehap.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import edu.avans.hartigehap.domain.states.ReservationStatus;
import edu.avans.hartigehap.domain.states.StateConcept;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Getter
@Setter
@NoArgsConstructor
public class Reservation implements Serializable{
    private static final long serialVersionUID = 1L;

    @OneToOne(cascade = javax.persistence.CascadeType.ALL)
    private ReservationStatus currentState;
    
    private int amountOfPeople;
    
    private TimeOfDay startTime;
    
    private TimeOfDay endTime;
    
    private String booker;
    
    public Reservation(int amountOfPeople, String booker, TimeOfDay startTime, TimeOfDay endTime) {
        this.currentState = new StateConcept(this);
        this.amountOfPeople = amountOfPeople;
        this.booker = booker;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    
}
