package edu.avans.hartigehap.service;

import java.util.List;

import edu.avans.hartigehap.domain.Room;

public interface RoomService {
	List<Room> findAll();

	Room findById(long id);

	Room save(Room room);

	void delete(Room room);
}
