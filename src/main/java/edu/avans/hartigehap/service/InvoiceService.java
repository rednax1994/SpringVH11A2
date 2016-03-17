package edu.avans.hartigehap.service;

import java.util.List;

import edu.avans.hartigehap.domain.Invoice;
import edu.avans.hartigehap.domain.Restaurant;

public interface InvoiceService {
    List<Invoice> findAll();
    Invoice findById(Long id);
    List<Invoice> findByNumber(int number);
    Invoice save(Invoice invoice);
    void delete(Invoice invoice);
    List<Invoice> findInvoicesForRestaurant(Restaurant restaurant);
}
