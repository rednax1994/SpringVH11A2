package edu.avans.hartigehap.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import edu.avans.hartigehap.domain.Bill;
import edu.avans.hartigehap.domain.Room;

public interface RoomRepository extends PagingAndSortingRepository<Room, Long>{

}
