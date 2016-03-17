package edu.avans.hartigehap.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import edu.avans.hartigehap.domain.Restaurant;
import edu.avans.hartigehap.domain.RoomOption;

public interface RoomOptionRepository extends PagingAndSortingRepository<RoomOption, Long>{
	RoomOption findById(Long optionNr);

	List<RoomOption> findByRestaurant(Restaurant restaurant, Sort sort);
}
