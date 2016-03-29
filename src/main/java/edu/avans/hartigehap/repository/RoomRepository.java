package edu.avans.hartigehap.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import edu.avans.hartigehap.domain.Restaurant;
import edu.avans.hartigehap.domain.Room;

public interface RoomRepository extends PagingAndSortingRepository<Room, Long> {
    Room findById(Long roomNr);
    
    List<Room> findByRestaurant(Restaurant restaurant, Sort sort);
}