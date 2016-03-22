package edu.avans.hartigehap.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.avans.hartigehap.domain.Invoice;
import edu.avans.hartigehap.domain.Quotation;
import edu.avans.hartigehap.domain.Restaurant;
import edu.avans.hartigehap.domain.exception.MyException;
import edu.avans.hartigehap.service.BanquetingFacadeService;
import edu.avans.hartigehap.service.InvoiceService;
import edu.avans.hartigehap.domain.reservationFactory.ReservationFactory;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service("banquetingFacade")
@Repository
@Transactional
public class BanquetingFacadeImpl implements BanquetingFacadeService{
    
   @Autowired
   private InvoiceService invoiceService;
    
    public void acceptQuotation(Restaurant restaurant, Quotation quotation){
        log.info("test?");
        Invoice invoice = new Invoice();
        invoice.createFromQuotation(restaurant, quotation);
        log.info(invoice.getDate().toString());
        invoiceService.save(invoice);
        
        try {
            ReservationFactory.createReservation(quotation.getAmountOfPeople(), quotation.getCustomer(), quotation.getStartTimeOfDay(), quotation.getStartTime(), quotation.getEndTimeOfDay(), quotation.getEndTime(), quotation.getRoom(), null);
        } catch (MyException e) {
            e.printStackTrace();
        }
        //notifyCustomer(quotation);
    }
}
