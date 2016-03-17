package edu.avans.hartigehap.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import edu.avans.hartigehap.domain.RoomOption;

public interface RoomOptionRepository extends PagingAndSortingRepository<RoomOption, Long> {
    RoomOption findById(Long optionNr);
}
