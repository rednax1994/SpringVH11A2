package edu.avans.hartigehap.service;

import java.util.List;

import edu.avans.hartigehap.domain.Quotation;
import edu.avans.hartigehap.domain.Restaurant;

public interface QuotationService {
    List<Quotation> findAll();
    
    Quotation findById(Long id);
    
    List<Quotation> findByNumber(int number);
    
    Quotation save(Quotation quotation);
    
    void delete(Quotation quotation);
    
    List<Quotation> findQuotationsForRestaurant(Restaurant restaurant);
}
