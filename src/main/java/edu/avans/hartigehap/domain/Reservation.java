package edu.avans.hartigehap.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import edu.avans.hartigehap.domain.states.ReservationStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Getter
@Setter
@NoArgsConstructor

public class Reservation {
    
    @OneToOne
    private ReservationStatus currentState;
    
    private int amountOfPeople;
    
    private List<String> timeOfDay;
    
    private String booker;
    
    private Date startDate;
    
    private Date endDate;
    
    private Reservation(ReservationStatus currentState, int amountOfPeople, String booker) {
        
    }
    
}
