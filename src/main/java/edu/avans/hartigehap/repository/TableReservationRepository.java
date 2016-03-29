package edu.avans.hartigehap.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import edu.avans.hartigehap.domain.DiningTable;
import edu.avans.hartigehap.domain.reservationFactory.Reservation;
import edu.avans.hartigehap.domain.reservationFactory.TableReservation;

public interface TableReservationRepository extends PagingAndSortingRepository<TableReservation, Long>{
    List<TableReservation> findByDiningTable(DiningTable diningTable);
}
