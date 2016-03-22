package edu.avans.hartigehap.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import edu.avans.hartigehap.domain.Quotation;
import edu.avans.hartigehap.domain.Restaurant;

public interface QuotationRepository extends PagingAndSortingRepository<Quotation, Long>{
    List<Quotation> findByNumber(int number);

    List<Quotation> findByRestaurant(Restaurant restaurant);
}
