package edu.avans.hartigehap.domain.reservationFactory;

import java.util.Date;

import edu.avans.hartigehap.domain.DiningTable;
import edu.avans.hartigehap.domain.Room;
import edu.avans.hartigehap.domain.reservationFactory.Reservation.TimeOfDayEnum;
import edu.avans.hartigehap.exception.MyException;

public class ReservationFactory {

    public static Reservation createReservation(int amountOfPeople, String booker, TimeOfDayEnum startTimeOfDay, Date startTime, TimeOfDayEnum endTimeOfDay, Date endTime,Room room, DiningTable diningTable ) throws MyException{
        Reservation reservation = null;
        if((diningTable != null && room != null) || (diningTable == null && room == null)){
            throw new MyException("Fill one of the Reservation Types");
        } else if(diningTable != null){
            reservation = new TableReservation(amountOfPeople,booker,startTimeOfDay,startTime, endTimeOfDay, endTime, diningTable);
        } else if(room != null){
            reservation = new RoomReservation(amountOfPeople,booker,startTimeOfDay,startTime, endTimeOfDay, endTime, room);
        }
        return reservation;
    }
}
