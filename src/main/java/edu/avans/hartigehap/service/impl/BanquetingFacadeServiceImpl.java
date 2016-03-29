package edu.avans.hartigehap.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.avans.hartigehap.domain.Invoice;
import edu.avans.hartigehap.domain.Quotation;
import edu.avans.hartigehap.domain.Restaurant;
import edu.avans.hartigehap.domain.RestaurantLocationObject;
import edu.avans.hartigehap.domain.Status;
import edu.avans.hartigehap.domain.exception.MyException;
import edu.avans.hartigehap.domain.reservationfactory.Reservation;
import edu.avans.hartigehap.domain.reservationfactory.ReservationFactory;
import edu.avans.hartigehap.repository.ReservationRepository;
import edu.avans.hartigehap.service.BanquetingFacadeService;
import edu.avans.hartigehap.service.InvoiceService;
import edu.avans.hartigehap.service.QuotationService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("banguetingFacadeService")
@Repository
@Transactional
public class BanquetingFacadeServiceImpl implements BanquetingFacadeService {
    
    @Autowired
    private InvoiceService invoiceService;
    
    @Autowired
    private QuotationService quotationService;
    
    @Autowired
    private ReservationRepository reservationRepository;
    
    public void acceptQuotation(Restaurant restaurant, Quotation quotation) {
        log.info("test?");
        Invoice invoice = new Invoice();
        invoice.createFromQuotation(restaurant, quotation);
        log.info(invoice.getEventDate().toString());
        invoiceService.save(invoice);
        
        try {
            RestaurantLocationObject rlo = quotation.getRoom();
            Reservation reservation = ReservationFactory.createReservation(quotation.getAmountOfPeople(),
                    quotation.getCustomer(), quotation.getStartTimeOfDay(), quotation.getStartTime(),
                    quotation.getEndTimeOfDay(), quotation.getEndTime(), rlo);
            reservationRepository.save(reservation);
        } catch (MyException e) {
            new MyException("error in Banqueting: ", e);
        }
        
        if (quotation.getStatus() != Status.ACCEPTED) {
            quotation.setStatus(Status.ACCEPTED);
            quotationService.save(quotation);
        }
    }
}
