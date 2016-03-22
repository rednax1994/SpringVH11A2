package edu.avans.hartigehap.service;

import edu.avans.hartigehap.domain.Quotation;
import edu.avans.hartigehap.domain.Restaurant;

public interface BanquetingFacadeService {
    void acceptQuotation(Restaurant restaurant, Quotation quotation);
}
