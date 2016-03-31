package edu.avans.hartigehap.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.avans.hartigehap.domain.DiningTable;
import edu.avans.hartigehap.domain.Room;
import edu.avans.hartigehap.domain.reservationfactory.RoomReservation;
import edu.avans.hartigehap.domain.reservationfactory.TableReservation;
import edu.avans.hartigehap.repository.RoomReservationRepository;
import edu.avans.hartigehap.repository.TableReservationRepository;
import edu.avans.hartigehap.service.ReservationService;

@Service("ReservationService")
@Repository
@Transactional
public class ReservationServiceImpl implements ReservationService {
    
    @Autowired
    private RoomReservationRepository roomReservationRepository;
    
    @Autowired
    private TableReservationRepository tableReservationRepository;
    
    @Override
    @Transactional(readOnly = true)
    public List<RoomReservation> findReservationsForRoom(Room room) {
        return roomReservationRepository.findByRoom(room);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<TableReservation> findReservationsForDiningTable(DiningTable diningTable) {
        return tableReservationRepository.findByDiningTable(diningTable);
    }
}
