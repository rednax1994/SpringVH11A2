package edu.avans.hartigehap.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import lombok.extern.slf4j.Slf4j;

@Service("ReservationService")
@Repository
@Transactional
@Slf4j
public class ReservationServiceImpl implements ReservationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationServiceImpl.class);

    @Autowired
    private RoomReservationRepository roomReservationRepository;

    @Autowired
    private TableReservationRepository tableReservationRepository;

    @Override
    @Transactional(readOnly = true)
    public List<RoomReservation> findReservationsForRoom(Room room) {

        List<RoomReservation> reservationsForRoom = roomReservationRepository.findByRoom(room);
        return reservationsForRoom;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TableReservation> findReservationsForDiningTable(DiningTable diningTable){
        List<TableReservation> reservationsForDiningTable = tableReservationRepository.findByDiningTable(diningTable);
        return reservationsForDiningTable;
    }
}
