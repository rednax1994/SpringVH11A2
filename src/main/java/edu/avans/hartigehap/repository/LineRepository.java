package edu.avans.hartigehap.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import edu.avans.hartigehap.domain.Invoice;
import edu.avans.hartigehap.domain.Line;
import edu.avans.hartigehap.domain.Quotation;

public interface LineRepository extends PagingAndSortingRepository<Line, Long>{
    List<Line> findByQuotation(Quotation quotation);
    List<Line> findByInvoice(Invoice invoice);
}
