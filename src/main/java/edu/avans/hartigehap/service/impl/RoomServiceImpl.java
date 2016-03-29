package edu.avans.hartigehap.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import edu.avans.hartigehap.domain.Room;
import edu.avans.hartigehap.domain.exception.StateException;
import edu.avans.hartigehap.repository.RoomRepository;
import edu.avans.hartigehap.service.RoomService;
import lombok.extern.slf4j.Slf4j;

@Service("roomService")
@Repository
@Transactional(rollbackFor = StateException.class)
@Slf4j
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomRepository roomRepository;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(RoomServiceImpl.class);
    
    @Override
    @Transactional(readOnly = true)
    public List<Room> findAll() {
        List<Room> retval = Lists.newLinkedList(roomRepository.findAll());
        LOGGER.info("" + retval);
        return retval;
    }
    
    @Override
    @Transactional(readOnly = true)
    public Room findById(long id) {
        return roomRepository.findOne(id);
    }
    
    @Override
    public Room save(Room room) {
        return roomRepository.save(room);
    }
    
    @Override
    public void delete(Room room) {
        roomRepository.delete(room);
    }
    
    // to be able to follow associations outside the context of a transaction,
    // prefetch the associated entities by traversing the associations
    @Transactional(readOnly = true)
    public Room fetchWarmedUp(Long id) {
        log.info("(fetchWarmedUp) room id: " + id);
        
        // finding an item using find
        Room room = roomRepository.findOne(id);
        
        // the following code will deliberately cause a null pointer exception,
        // if something is wrong
        log.info("diningTable = " + room.getId());
        
        return room;
    }
    
}
