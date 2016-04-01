package edu.avans.hartigehap.web.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.avans.hartigehap.domain.DiningTable;
import edu.avans.hartigehap.domain.Restaurant;
import edu.avans.hartigehap.domain.Room;
import edu.avans.hartigehap.domain.reservationfactory.RoomReservation;
import edu.avans.hartigehap.domain.reservationfactory.TableReservation;
import edu.avans.hartigehap.service.DiningTableService;
import edu.avans.hartigehap.service.ReservationService;
import edu.avans.hartigehap.service.RestaurantService;
import edu.avans.hartigehap.service.RoomService;
import lombok.extern.slf4j.Slf4j;

@Controller
@PreAuthorize("hasRole('ROLE_EMPLOYEE')")
@Slf4j
public class ReservationController {
    
    @Autowired
    private RoomService roomService;
    
    @Autowired
    private DiningTableService diningTableService;
    
    @Autowired
    private RestaurantService restaurantService;
    
    @Autowired
    private ReservationService reservationService;
    
    @RequestMapping(value = "/restaurants/{restaurantName}/reservation", method = RequestMethod.GET)
    public String listRoomsAndTables(@PathVariable("restaurantName") String restaurantName, Model uiModel) {
        Restaurant restaurant = warmupRestaurant(restaurantName, uiModel);
        
        log.info("Listing quotations and invoices");
        Collection<Room> rooms = restaurant.getRooms();
        uiModel.addAttribute("rooms", rooms);
        log.info("No. of rooms: " + rooms.size());
        
        Collection<DiningTable> tables = restaurant.getDiningTables();
        uiModel.addAttribute("tables", tables);
        log.info("No. of dining tables: " + tables.size());
        
        uiModel.addAttribute("restaurant", restaurant);
        
        return "hartigehap/listRoomsAndTables";
    }
    
    @RequestMapping(value = "/restaurants/{restaurantName}/reservation/room/{id}", method = RequestMethod.GET)
    public String showRoomReservations(@PathVariable("restaurantName") String restaurantName,
            @PathVariable("id") Long id, Model uiModel) {
        
        warmupRestaurant(restaurantName, uiModel);
        
        log.info("Show room: " + id);
        
        Room room = roomService.findById(id);
        uiModel.addAttribute("room", room);
        
        List<RoomReservation> roomreservations = reservationService.findReservationsForRoom(room);
        uiModel.addAttribute("roomReservations", roomreservations);
        
        return "hartigehap/showRoomReservations";
    }
    
    @RequestMapping(value = "/restaurants/{restaurantName}/reservation/diningTable/{id}", method = RequestMethod.GET)
    public String showTableReservations(@PathVariable("restaurantName") String restaurantName,
            @PathVariable("id") Long id, Model uiModel) {
        
        warmupRestaurant(restaurantName, uiModel);
        
        log.info("Show diningTable: " + id);
        DiningTable diningTable = diningTableService.findById(id);
        List<TableReservation> tablereservations = reservationService.findReservationsForDiningTable(diningTable);
        uiModel.addAttribute("diningTableReservations", tablereservations);
        uiModel.addAttribute("diningTable", diningTable);
        return "hartigehap/showTableReservations";
    }
    
    private Restaurant warmupRestaurant(String restaurantName, Model uiModel) {
        Collection<Restaurant> restaurants = restaurantService.findAll();
        uiModel.addAttribute("restaurants", restaurants);
        Restaurant restaurant = restaurantService.fetchWarmedUp(restaurantName);
        uiModel.addAttribute("restaurant", restaurant);
        return restaurant;
    }
    
}
