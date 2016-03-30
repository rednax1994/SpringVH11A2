package edu.avans.hartigehap.service;


import java.util.List;

import edu.avans.hartigehap.domain.DiningTable;
import edu.avans.hartigehap.domain.Room;
import edu.avans.hartigehap.domain.reservationfactory.RoomReservation;
import edu.avans.hartigehap.domain.reservationfactory.TableReservation;


public interface ReservationService {
    List<RoomReservation> findReservationsForRoom(Room room);
    
    List<TableReservation> findReservationsForDiningTable(DiningTable diningTable);

}
