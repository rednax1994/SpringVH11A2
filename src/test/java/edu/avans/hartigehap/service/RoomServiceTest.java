package edu.avans.hartigehap.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import edu.avans.hartigehap.domain.Restaurant;
import edu.avans.hartigehap.domain.Room;
import edu.avans.hartigehap.repository.RoomRepository;
import edu.avans.hartigehap.service.testutil.AbstractTransactionRollbackTest;

public class RoomServiceTest extends AbstractTransactionRollbackTest {
    
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private RoomRepository roomRepository;
    
    @Test
    public void create() {
        // execute
        Room room = createRoom();
        roomRepository.save(room);
        
        // verify
        List<Room> rooms = (List<Room>) roomRepository.findAll();
        assertNotNull(rooms);
        assertTrue("created room in list", rooms.contains(room));
    }
    
    @Test
    public void update() {
        // execute
        Room room = createRoom();
        String roomState = roomRepository.save(room).getOccupied().toString();
        
        room.setOccupied(true);
        roomRepository.save(room);
        
        // verify
        assertFalse(roomState == roomRepository.findOne(room.getId()).getOccupied().toString());
    }
    
    @Test
    public void delete() {
        // execute
        Room room = createRoom();
        roomRepository.save(room);
        
        Room createdRoom = roomRepository.findById((long) 1);
        assertNotNull(createdRoom);
        
        roomRepository.delete(createdRoom);
        
        // verify
        assertNotSame(createdRoom, roomRepository.findOne(createdRoom.getId()));
    }
    
    private Room createRoom() {
        Room room = new Room(1, false, 20);
        List<Restaurant> restaurants = restaurantService.findAll();
        room.setRestaurant(restaurants.get(0));
        restaurants.get(0).getRooms().add(room);
        return room;
    }
    
}
