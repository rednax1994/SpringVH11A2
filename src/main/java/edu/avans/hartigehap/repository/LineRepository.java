package edu.avans.hartigehap.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import edu.avans.hartigehap.domain.Line;

public interface LineRepository extends PagingAndSortingRepository<Line, Long> {
}
