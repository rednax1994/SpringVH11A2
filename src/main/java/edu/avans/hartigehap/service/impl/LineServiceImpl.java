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
import edu.avans.hartigehap.domain.Line;
import edu.avans.hartigehap.domain.Quotation;
import edu.avans.hartigehap.repository.LineRepository;
import edu.avans.hartigehap.service.LineService;

@Service("lineService")
@Repository
@Transactional
public class LineServiceImpl implements LineService{

    @Autowired
    private LineRepository lineRepository;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(OwnerServiceImpl.class);
    
    @Override
    @Transactional(readOnly = true)
    public List<Line> findAll() {
        List<Line> retval = Lists.newLinkedList(lineRepository.findAll());
        LOGGER.info("" + retval);
        return retval;
    }

    @Override
    @Transactional(readOnly = true)
    public Line findById(Long id) {
        return lineRepository.findOne(id);
    }

    @Override
    public Line save(Line line) {
        return lineRepository.save(line);
    }

    @Override
    public void delete(Line line) {
        lineRepository.delete(line);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Line> findByQuotation(Quotation quotation) {
        return lineRepository.findByQuotation(quotation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Line> findByInvoice(Invoice invoice) {
        return lineRepository.findByInvoice(invoice);
    }

}
