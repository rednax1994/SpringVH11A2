package edu.avans.hartigehap.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.avans.hartigehap.domain.Invoice;
import edu.avans.hartigehap.domain.Quotation;
import edu.avans.hartigehap.domain.Restaurant;
import edu.avans.hartigehap.domain.Status;
import edu.avans.hartigehap.domain.exception.MyException;
import edu.avans.hartigehap.service.BanquetingFacadeService;
import edu.avans.hartigehap.service.InvoiceService;
import edu.avans.hartigehap.service.QuotationService;
import edu.avans.hartigehap.domain.reservationFactory.Reservation;
import edu.avans.hartigehap.domain.reservationFactory.ReservationFactory;
import edu.avans.hartigehap.repository.ReservationRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("banquetingFacade")
@Repository
@Transactional
public class BanquetingFacadeImpl implements BanquetingFacadeService {

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
            Reservation reservation = ReservationFactory.createReservation(quotation.getAmountOfPeople(),
                    quotation.getCustomer(), quotation.getStartTimeOfDay(), quotation.getStartTime(),
                    quotation.getEndTimeOfDay(), quotation.getEndTime(), quotation.getRoom(), null);
            reservationRepository.save(reservation);
        } catch (MyException e) {
            e.printStackTrace();
        }
        if (quotation.getStatus() != Status.ACCEPTED) {
            quotation.setStatus(Status.ACCEPTED);
            quotationService.save(quotation);
        }
    }
}
