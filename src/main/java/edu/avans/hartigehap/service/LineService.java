package edu.avans.hartigehap.service;

import java.util.List;

import edu.avans.hartigehap.domain.Invoice;
import edu.avans.hartigehap.domain.Line;
import edu.avans.hartigehap.domain.Quotation;

public interface LineService {
    List<Line> findAll();
    Line findById(Long id);
    Line save(Line line);
    void delete(Line line);
    List<Line> findByQuotation(Quotation quotation);
    List<Line> findByInvoice(Invoice invoice);
}
