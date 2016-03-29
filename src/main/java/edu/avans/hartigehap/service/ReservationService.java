package edu.avans.hartigehap.service;

import java.util.List;

import edu.avans.hartigehap.domain.DiningTable;
import edu.avans.hartigehap.domain.Room;
import edu.avans.hartigehap.domain.reservationFactory.RoomReservation;
import edu.avans.hartigehap.domain.reservationFactory.TableReservation;

public interface ReservationService {
    List<RoomReservation> findReservationsForRoom(Room room);
    
    List<TableReservation> findReservationsForDiningTable(DiningTable diningTable);
}
