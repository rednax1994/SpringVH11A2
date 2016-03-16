package edu.avans.hartigehap.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import edu.avans.hartigehap.domain.Invoice;

public interface InvoiceRepository extends PagingAndSortingRepository<Invoice, Long>{
    List<Invoice> findByNumber(int number);
}
