package edu.avans.hartigehap.domain.reservationFactory;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import edu.avans.hartigehap.domain.Customer;
import edu.avans.hartigehap.domain.Room;
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
public class RoomReservation extends Reservation {
    private static final long serialVersionUID = 1L;
    
    @ManyToOne
    private Room room;
    
    public RoomReservation(int amountOfPeople, Customer customer, TimeOfDayEnum startTimeOfDay, Date startTime,
            TimeOfDayEnum endTimeOfDay, Date endTime, Room room){
        super(amountOfPeople, customer, startTimeOfDay, startTime, endTimeOfDay, endTime);
        this.room = room;
    };
}
