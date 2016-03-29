package edu.avans.hartigehap.service.impl;

import java.util.List;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import edu.avans.hartigehap.domain.Quotation;
import edu.avans.hartigehap.domain.Restaurant;
import edu.avans.hartigehap.repository.QuotationRepository;
import edu.avans.hartigehap.service.QuotationService;
import lombok.extern.slf4j.Slf4j;

@Service("quotationService")
@Repository
@Transactional
@Slf4j
public class QuotationServiceImpl implements QuotationService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(QuotationServiceImpl.class);
    
    @Autowired
    private QuotationRepository quotationRepository;
    
    @Override
    @Transactional(readOnly = true)
    public List<Quotation> findAll() {
        List<Quotation> retval = Lists.newLinkedList(quotationRepository.findAll());
        LOGGER.info("" + retval);
        return retval;
    }
    
    @Override
    @Transactional(readOnly = true)
    public Quotation findById(Long id) {
        return quotationRepository.findOne(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Quotation> findByNumber(int number) {
        return quotationRepository.findByNumber(number);
    }
    
    @Override
    public Quotation save(Quotation quotation) {
        return quotationRepository.save(quotation);
    }
    
    @Override
    public void delete(Quotation quotation) {
        quotationRepository.delete(quotation);
    }
    
    @Transactional(readOnly = true)
    public List<Quotation> findQuotationsForRestaurant(Restaurant restaurant) {
        
        List<Quotation> quotationsForRestaurants = quotationRepository.findByRestaurant(restaurant);
        ListIterator<Quotation> it = quotationsForRestaurants.listIterator();
        while (it.hasNext()) {
            Quotation quotation = it.next();
            log.info("quotation = " + quotation);
        }
        
        return quotationsForRestaurants;
    }
    
}
