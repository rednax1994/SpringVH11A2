package edu.avans.hartigehap.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import edu.avans.hartigehap.domain.Invoice;
import edu.avans.hartigehap.domain.Restaurant;

public interface InvoiceRepository extends PagingAndSortingRepository<Invoice, Long> {
    List<Invoice> findByNumber(int number);
    
    List<Invoice> findByRestaurant(Restaurant restaurant);
}
