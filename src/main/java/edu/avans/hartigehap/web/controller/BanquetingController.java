package edu.avans.hartigehap.web.controller;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.avans.hartigehap.domain.Invoice;
import edu.avans.hartigehap.domain.Quotation;
import edu.avans.hartigehap.domain.Restaurant;
import edu.avans.hartigehap.domain.Status;
import edu.avans.hartigehap.service.BanquetingFacadeService;
import edu.avans.hartigehap.service.InvoiceService;
import edu.avans.hartigehap.service.QuotationService;
import edu.avans.hartigehap.service.RestaurantService;
import edu.avans.hartigehap.web.form.Message;
import edu.avans.hartigehap.web.util.UrlUtil;
import lombok.extern.slf4j.Slf4j;

@Controller
@PreAuthorize("hasRole('ROLE_MANAGER')")
@Slf4j
public class BanquetingController {
    @Autowired
    private MessageSource messageSource;
    
    @Autowired
    private RestaurantService restaurantService;
    
    @Autowired
    private QuotationService quotationService;
    
    @Autowired
    private InvoiceService invoiceService;
    
    @Autowired
    private BanquetingFacadeService banquetingFacadeService;
    
    @RequestMapping(value = "/restaurants/{restaurantName}/banqueting", method = RequestMethod.GET)
    public String listQuotationsAndInvoices(@PathVariable("restaurantName") String restaurantName, Model uiModel) {
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
    
    @RequestMapping(value = "/restaurants/{restaurantName}/banqueting/quotations/{id}", method = RequestMethod.GET)
    public String showQuotation(@PathVariable("restaurantName") String restaurantName, @PathVariable("id") Long id,
            Model uiModel) {
        
        warmupRestaurant(restaurantName, uiModel);
        
        log.info("Show quotation: " + id);
        
        Quotation quotation = quotationService.findById(id);
        uiModel.addAttribute("quotation", quotation);
        return "hartigehap/showquotation";
    }
    
    @RequestMapping(value = "/restaurants/{restaurantName}/banqueting/quotations/{id}", params = "submit", method = RequestMethod.GET)
    public String acceptQuotation(@PathVariable("restaurantName") String restaurantName, @PathVariable("id") Long id,
            Model uiModel) {
        
        warmupRestaurant(restaurantName, uiModel);
        
        log.info("Quotation update form for quotation: " + id);
        
        Quotation quotation = quotationService.findById(id);
        Restaurant restaurant = restaurantService.fetchWarmedUp(restaurantName);
        banquetingFacadeService.acceptQuotation(restaurant, quotation);
        return "redirect:/restaurants/" + restaurantName + "/banqueting/";
    }
    
    @RequestMapping(value = "/restaurants/{restaurantName}/banqueting/quotations/{id}", params = "preview", method = RequestMethod.GET)
    public String previewQuotation(@PathVariable("restaurantName") String restaurantName, @PathVariable("id") Long id,
            Model uiModel) {
        warmupRestaurant(restaurantName, uiModel);
        
        Quotation quotation = quotationService.findById(id);
        String message = quotation.displayDocument(quotation);
        uiModel.addAttribute("message", message);
        return "hartigehap/previewdocument";
    }
    
    @RequestMapping(value = "/restaurants/{restaurantName}/banqueting/quotations/{id}", params = "delete", method = RequestMethod.GET)
    public String deleteQuotation(@PathVariable("restaurantName") String restaurantName, @PathVariable("id") Long id) {
        
        log.info("Deleting quotation: " + id);
        Quotation quotation = quotationService.findById(id);
        quotationService.delete(quotation);
        return "redirect:/restaurants/" + restaurantName + "/banqueting/";
    }
    
    @RequestMapping(value = "/restaurants/{restaurantName}/banqueting/quotations/{id}", params = "form", method = RequestMethod.GET)
    public String updateQuotationForm(@PathVariable("restaurantName") String restaurantName,
            @PathVariable("id") Long id, Model uiModel) {
        
        warmupRestaurant(restaurantName, uiModel);
        
        log.info("Quotation update form for quotation: " + id);
        
        Quotation quotation = quotationService.findById(id);
        uiModel.addAttribute("quotation", quotation);
        log.info("updatingQuotationForm(" + quotation.getNumber() + ")");
        return "hartigehap/editquotation";
    }
    
    @RequestMapping(value = "/restaurants/{restaurantName}/banqueting/quotations", params = "form", method = RequestMethod.GET)
    public String createQuotationForm(@PathVariable("restaurantName") String restaurantName, Model uiModel) {
        
        warmupRestaurant(restaurantName, uiModel);
        
        log.info("Create quotation form");
        
        Quotation quotation = new Quotation();
        uiModel.addAttribute("quotation", quotation);
        return "hartigehap/editquotation";
    }
    
    private String handleCreateOrUpdateQuotation(boolean isCreate, String restaurantName, Quotation quotation,
            BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest,
            RedirectAttributes redirectAttributes, Locale locale) {
        
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("message",
                    new Message("error", messageSource.getMessage("quotation_save_fail", new Object[] {}, locale)));
            uiModel.addAttribute("quotation", quotation);
            return "hartigehap/editquotation";
        }
        uiModel.asMap().clear();
        redirectAttributes.addFlashAttribute("message",
                new Message("success", messageSource.getMessage("quotation_save_success", new Object[] {}, locale)));
        
        if (isCreate) {
            Restaurant restaurant = warmupRestaurant(restaurantName, uiModel);
            quotation.setRestaurant(restaurant);
            quotation.setStatus(Status.CONCEPT);
            quotation = quotationService.save(quotation); // NOSONAR
        } else {
            Quotation existingQuotation = quotationService.findById(quotation.getId());
            assert existingQuotation != null : "quotation should exist";
            existingQuotation.updateEditableFields(quotation);
            quotationService.save(existingQuotation);
        }
        
        return "redirect:/restaurants/" + restaurantName + "/banqueting/quotations/"
                + UrlUtil.encodeUrlPathSegment(quotation.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/restaurants/{restaurantName}/banqueting/quotations/{id}", params = "form", method = RequestMethod.PUT)
    public String updateQuotation(@PathVariable("restaurantName") String restaurantName, @Valid Quotation quotation,
            BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest,
            RedirectAttributes redirectAttributes, Locale locale) {
        
        return handleCreateOrUpdateQuotation(false, restaurantName, quotation, bindingResult, uiModel,
                httpServletRequest, redirectAttributes, locale);
    }
    
    @RequestMapping(value = "/restaurants/{restaurantName}/banqueting/quotations", params = "form", method = RequestMethod.POST)
    public String createQuotation(@PathVariable("restaurantName") String restaurantName, @Valid Quotation quotation,
            BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest,
            RedirectAttributes redirectAttributes, Locale locale) {
        
        log.info("Creating quotation: " + quotation.getAmountOfPeople() + " " + quotation.getStatus());
        log.info("Binding Result target: " + (Quotation) bindingResult.getTarget());
        log.info("Binding Result: " + bindingResult);
        
        return handleCreateOrUpdateQuotation(true, restaurantName, quotation, bindingResult, uiModel,
                httpServletRequest, redirectAttributes, locale);
    }
    
    @RequestMapping(value = "/restaurants/{restaurantName}/banqueting/invoices/{id}", method = RequestMethod.GET)
    public String showInvoice(@PathVariable("restaurantName") String restaurantName, @PathVariable("id") Long id,
            Model uiModel) {
        
        warmupRestaurant(restaurantName, uiModel);
        
        log.info("Show invoice: " + id);
        
        Invoice invoice = invoiceService.findById(id);
        uiModel.addAttribute("invoice", invoice);
        return "hartigehap/showinvoice";
    }
    
    @RequestMapping(value = "/restaurants/{restaurantName}/banqueting/invoices/{id}", params = "preview", method = RequestMethod.GET)
    public String previewInvoice(@PathVariable("restaurantName") String restaurantName, @PathVariable("id") Long id,
            Model uiModel) {
        warmupRestaurant(restaurantName, uiModel);
        
        Invoice invoice = invoiceService.findById(id);
        String message = invoice.displayDocument(invoice);
        uiModel.addAttribute("message", message);
        return "hartigehap/previewdocument";
    }
    
    @RequestMapping(value = "/restaurants/{restaurantName}/banqueting/invoices/{id}", params = "delete", method = RequestMethod.GET)
    public String deleteInvoice(@PathVariable("restaurantName") String restaurantName, @PathVariable("id") Long id) {
        
        log.info("Deleting invoice: " + id);
        Invoice invoice = invoiceService.findById(id);
        invoiceService.delete(invoice);
        return "redirect:/restaurants/" + restaurantName + "/banqueting/";
    }
    
    private Restaurant warmupRestaurant(String restaurantName, Model uiModel) {
        Collection<Restaurant> restaurants = restaurantService.findAll();
        uiModel.addAttribute("restaurants", restaurants);
        Restaurant restaurant = restaurantService.fetchWarmedUp(restaurantName);
        uiModel.addAttribute("restaurant", restaurant);
        return restaurant;
    }
}
