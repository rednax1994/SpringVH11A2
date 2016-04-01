package edu.avans.hartigehap.domain.reservationfactory;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import edu.avans.hartigehap.domain.Customer;
import edu.avans.hartigehap.domain.DomainObject;
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
// @Inheritance(strategy=InheritanceType.JOINED)
// @DiscriminatorColumn(name = "DTYPE")
public abstract class Reservation extends DomainObject {
    private static final long serialVersionUID = 1L;
    
    public enum TimeOfDayEnum {
        MORNING, NOON, EVENING
    }
    
    @OneToOne(cascade = javax.persistence.CascadeType.ALL)
    private ReservationStatus currentState;
    
    private int amountOfPeople;
    
    @Enumerated(EnumType.STRING)
    // represented in database as integer
    protected TimeOfDayEnum startTimeOfDay;
    
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @DateTimeFormat(iso = ISO.DATE)
    private DateTime startTime;
    
    @Enumerated(EnumType.STRING)
    // represented in database as integer
    protected TimeOfDayEnum endTimeOfDay;
    
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @DateTimeFormat(iso = ISO.DATE)
    private DateTime endTime;
    
    @ManyToOne
    private Customer customer;
    
    public Reservation(int amountOfPeople, Customer customer, TimeOfDayEnum startTimeOfDay, DateTime startTime,
            TimeOfDayEnum endTimeOfDay, DateTime endTime) {
        currentState = new ConceptState(this);
        this.amountOfPeople = amountOfPeople;
        this.customer = customer;
        this.startTimeOfDay = startTimeOfDay;
        this.startTime = startTime;
        this.endTimeOfDay = endTimeOfDay;
        this.endTime = endTime;
    }
}
