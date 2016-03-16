package edu.avans.hartigehap.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import edu.avans.hartigehap.domain.Owner;
import edu.avans.hartigehap.domain.Quotation;
import edu.avans.hartigehap.repository.OwnerRepository;
import edu.avans.hartigehap.repository.QuotationRepository;
import edu.avans.hartigehap.service.QuotationService;
import edu.avans.hartigehap.service.RestaurantService;

@Service("quotationService")
@Repository
@Transactional
public class QuotationServiceImpl implements QuotationService{

private static final Logger LOGGER = LoggerFactory.getLogger(OwnerServiceImpl.class);
    
    @Autowired
    private QuotationRepository quotationRepository;
    @Autowired
    private QuotationService quotationService;
    
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
        return quotationService.save(quotation);
    }

    @Override
    public void delete(Quotation quotation) {
       quotationService.delete(quotation);
    }

}
