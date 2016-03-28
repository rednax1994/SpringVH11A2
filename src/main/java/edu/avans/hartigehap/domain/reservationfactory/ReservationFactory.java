package edu.avans.hartigehap.domain.reservationfactory;

import java.util.Date;

import edu.avans.hartigehap.domain.Customer;
import edu.avans.hartigehap.domain.DiningTable;
import edu.avans.hartigehap.domain.RestaurantLocationObject;
import edu.avans.hartigehap.domain.Room;
import edu.avans.hartigehap.domain.exception.MyException;
import edu.avans.hartigehap.domain.reservationfactory.Reservation.TimeOfDayEnum;

public class ReservationFactory {
    
    private ReservationFactory() {
        
    }
    
    public static Reservation createReservation(int amountOfPeople, Customer customer, TimeOfDayEnum startTimeOfDay,
            Date startTime, TimeOfDayEnum endTimeOfDay, Date endTime, RestaurantLocationObject rlo) throws MyException {
        Reservation reservation = null;
        if (rlo instanceof DiningTable) {
            reservation = new TableReservation(amountOfPeople, customer, startTimeOfDay, startTime, endTimeOfDay,
                    endTime, rlo.getDiningTable());
        } else if (rlo instanceof Room) {
            reservation = new RoomReservation(amountOfPeople, customer, startTimeOfDay, startTime, endTimeOfDay,
                    endTime, rlo.getRoom());
        } else {
            return null;
        }
        return reservation;
    }
}
