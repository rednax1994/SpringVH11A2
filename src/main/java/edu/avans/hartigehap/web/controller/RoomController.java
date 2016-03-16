package edu.avans.hartigehap.web.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.avans.hartigehap.domain.Restaurant;
import edu.avans.hartigehap.domain.Room;
import edu.avans.hartigehap.service.RestaurantService;
import edu.avans.hartigehap.service.RoomService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class RoomController {
    
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private RoomService roomService;
    
    @RequestMapping(value = "/rooms/{roomId}", method = RequestMethod.GET)
    public String showTable(@PathVariable("roomId") String roomId, Model uiModel) {
        log.info("room = " + roomId);
        
        warmupRestaurant(roomId, uiModel);
        
        return "hartigehap/room";
    }
    
    private Room warmupRestaurant(String roomId, Model uiModel) {
        Collection<Restaurant> restaurants = restaurantService.findAll();
        uiModel.addAttribute("restaurants", restaurants);
        Room room = roomService.fetchWarmedUp(Long.valueOf(roomId));
        uiModel.addAttribute("room", room);
        Restaurant restaurant = restaurantService.fetchWarmedUp(room.getRestaurant().getId());
        uiModel.addAttribute("restaurant", restaurant);
        
        return room;
    }
}
