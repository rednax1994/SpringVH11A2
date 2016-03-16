package edu.avans.hartigehap.repository;

import edu.avans.hartigehap.domain.Reservation;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ReservationRepository extends PagingAndSortingRepository<Reservation, Long>  {

}
