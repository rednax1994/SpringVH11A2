package edu.avans.hartigehap.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import edu.avans.hartigehap.domain.reservationFactory.Reservation;

public interface ReservationRepository extends PagingAndSortingRepository<Reservation, Long>  {

}
