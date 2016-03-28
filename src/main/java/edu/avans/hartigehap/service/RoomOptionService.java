package edu.avans.hartigehap.service;

import java.util.List;

import edu.avans.hartigehap.domain.RoomOption;

public interface RoomOptionService {
    List<RoomOption> findAll();
    
    RoomOption findById(long id);
    
    RoomOption save(RoomOption roomOption);
    
    void delete(RoomOption roomOption);
    
    RoomOption fetchWarmedUp(Long roomId);
}
