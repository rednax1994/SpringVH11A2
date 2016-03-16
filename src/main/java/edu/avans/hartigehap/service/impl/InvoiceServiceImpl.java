package edu.avans.hartigehap.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import edu.avans.hartigehap.domain.Invoice;
import edu.avans.hartigehap.repository.InvoiceRepository;
import edu.avans.hartigehap.service.InvoiceService;

@Service("invoiceService")
@Repository
@Transactional
public class InvoiceServiceImpl implements InvoiceService{

private static final Logger LOGGER = LoggerFactory.getLogger(OwnerServiceImpl.class);
    
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private InvoiceService invoiceService;
    
    @Override
    @Transactional(readOnly = true)
    public List<Invoice> findAll() {
        List<Invoice> retval = Lists.newLinkedList(invoiceRepository.findAll());
        LOGGER.info("" + retval);
        return retval;
    }
    
    @Override
    @Transactional(readOnly = true)
    public Invoice findById(Long id) {
        return invoiceRepository.findOne(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Invoice> findByNumber(int number) {
        return invoiceRepository.findByNumber(number);
    }

    @Override
    public Invoice save(Invoice invoice) {
        return invoiceService.save(invoice);
    }

    @Override
    public void delete(Invoice invoice) {
       invoiceService.delete(invoice);
    }

}
