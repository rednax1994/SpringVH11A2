package edu.avans.hartigehap.domain.reservationfactory;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import edu.avans.hartigehap.domain.Customer;
import edu.avans.hartigehap.domain.Room;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Getter
@Setter
@NoArgsConstructor
public class RoomReservation extends Reservation {
    private static final long serialVersionUID = 1L;
    
    @ManyToOne
    private Room room;
    
    public RoomReservation(int amountOfPeople, Customer customer, TimeOfDayEnum startTimeOfDay, DateTime startTime,
            TimeOfDayEnum endTimeOfDay, DateTime endTime, Room room) {
        super(amountOfPeople, customer, startTimeOfDay, startTime, endTimeOfDay, endTime);
        this.room = room;
    }
}
