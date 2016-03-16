package edu.avans.hartigehap.service;

import java.util.List;

import edu.avans.hartigehap.domain.Invoice;

public interface InvoiceService {
    List<Invoice> findAll();
    Invoice findById(Long id);
    List<Invoice> findByNumber(int number);
    Invoice save(Invoice invoice);
    void delete(Invoice invoice);
}
