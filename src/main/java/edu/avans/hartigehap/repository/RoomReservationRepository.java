package edu.avans.hartigehap.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import edu.avans.hartigehap.domain.Room;
import edu.avans.hartigehap.domain.reservationfactory.RoomReservation;


public interface RoomReservationRepository extends PagingAndSortingRepository<RoomReservation, Long>  {

    List<RoomReservation> findByRoom(Room room);
 
}
