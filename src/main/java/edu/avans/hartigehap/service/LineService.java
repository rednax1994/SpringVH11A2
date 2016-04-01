package edu.avans.hartigehap.service;

import java.util.List;

import edu.avans.hartigehap.domain.Line;

public interface LineService {
    List<Line> findAll();
    
    Line findById(Long id);
    
    Line save(Line line);
    
    void delete(Line line);
}
