package edu.avans.hartigehap.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import edu.avans.hartigehap.domain.states.ConceptState;
import edu.avans.hartigehap.domain.states.ReservationStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Getter
@Setter
@NoArgsConstructor
public class Reservation extends DomainObject {
    private static final long serialVersionUID = 1L;
    
    public enum TimeOfDayEnum {
        MORNING, NOON, EVENING
    }
    
    @OneToOne(cascade = javax.persistence.CascadeType.ALL)
    private ReservationStatus currentState;
    
    private int amountOfPeople;
    
    protected TimeOfDayEnum startTimeOfDay;
    
    private Date startTime;
    
    protected TimeOfDayEnum endTimeOfDay;
    
    private Date endTime;
    
    private String booker;
    
    @ManyToOne
    private Room room;
    
    @ManyToOne
    private DiningTable diningTable;
    
    public Reservation(int amountOfPeople, String booker, TimeOfDayEnum startTimeOfDay, Date startTime,
            TimeOfDayEnum endTimeOfDay, Date endTime) {
        this.currentState = new ConceptState(this);
        this.amountOfPeople = amountOfPeople;
        this.booker = booker;
        this.startTimeOfDay = startTimeOfDay;
        this.startTime = startTime;
        this.endTimeOfDay = endTimeOfDay;
        this.endTime = endTime;
    }
}
