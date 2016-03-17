package edu.avans.hartigehap.domain.reservationFactory;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import edu.avans.hartigehap.domain.DiningTable;
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
public class TableReservation extends Reservation{
    private static final long serialVersionUID = 1L;
    
    @ManyToOne
    private DiningTable diningTable;
    
    public TableReservation(int amountOfPeople, String booker, TimeOfDayEnum startTimeOfDay, Date startTime,
            TimeOfDayEnum endTimeOfDay, Date endTime, DiningTable diningTable){
        super(amountOfPeople, booker, startTimeOfDay, startTime, endTimeOfDay, endTime);
        this.diningTable = diningTable;
    };
}
