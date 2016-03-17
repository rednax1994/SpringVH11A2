package edu.avans.hartigehap.web.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.avans.hartigehap.domain.Invoice;
import edu.avans.hartigehap.domain.Quotation;
import edu.avans.hartigehap.domain.Restaurant;
import edu.avans.hartigehap.service.InvoiceService;
import edu.avans.hartigehap.service.QuotationService;
import edu.avans.hartigehap.service.RestaurantService;
import lombok.extern.slf4j.Slf4j;

@Controller
@PreAuthorize("hasRole('ROLE_MANAGER')")
@Slf4j
public class BanquetingController {
    
    @Autowired
    private RestaurantService restaurantService;
    
    @Autowired
    private QuotationService quotationService;
    
    @Autowired
    private InvoiceService invoiceService;
    
    @RequestMapping(value = "/restaurants/{restaurantName}/banqueting", method = RequestMethod.GET)
    public String listQuotationsAndInvoices(@PathVariable("restaurantName") String restaurantName, Model uiModel){
        Restaurant restaurant = warmupRestaurant(restaurantName, uiModel);
        
        log.info("Listing quotations and invoices");
        List<Quotation> quotations = quotationService.findQuotationsForRestaurant(restaurant);
        uiModel.addAttribute("quotations", quotations);
        log.info("No. of quotations: " + quotations.size());
        
        List<Invoice> invoices = invoiceService.findInvoicesForRestaurant(restaurant);
        uiModel.addAttribute("invoices", invoices);
        log.info("No. of invoices: " + invoices.size());
        
        return "hartigehap/banquetinglist";
    }

    private Restaurant warmupRestaurant(String restaurantName, Model uiModel) {
        Collection<Restaurant> restaurants = restaurantService.findAll();
        uiModel.addAttribute("restaurants", restaurants);
        Restaurant restaurant = restaurantService.fetchWarmedUp(restaurantName);
        uiModel.addAttribute("restaurant", restaurant);
        return restaurant;
    }
}
