package edu.avans.hartigehap.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import edu.avans.hartigehap.domain.Quotation;

public interface QuotationRepository extends PagingAndSortingRepository<Quotation, Long>{
    List<Quotation> findByNumber(int number);
}
