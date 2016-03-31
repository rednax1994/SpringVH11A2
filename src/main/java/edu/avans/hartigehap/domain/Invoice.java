package edu.avans.hartigehap.domain;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Getter
@Setter
@ToString(callSuper = false, includeFieldNames = true)
@NoArgsConstructor
public class Invoice extends Document {
    
    private static final long serialVersionUID = 1L;
    
    @OneToMany(mappedBy = "invoice")
    private Collection<Line> invoiceLines = new ArrayList<Line>();
    
    @Transient
    private DisplayTemplate displayTemplate = new DisplayInvoice();
    
    public void createFromQuotation(Restaurant restaurant, Quotation quotation) {
        this.setNumber(quotation.getNumber());
        this.setEventDate(quotation.getEventDate());
        this.setExpirationDate(quotation.getExpirationDate());
        this.setRestaurant(restaurant);
        this.setStatus(quotation.getStatus());
        this.setRoom(quotation.getRoom());
        this.setCustomer(quotation.getCustomer());
        this.setAmountOfPeople(quotation.getAmountOfPeople());
        this.setEndTime(quotation.getEndTime());
        this.setEndTimeOfDay(quotation.getEndTimeOfDay());
        this.setStartTime(quotation.getStartTime());
        this.setStartTimeOfDay(quotation.getStartTimeOfDay());
    }
    
    public String displayDocument(Invoice invoice) {
        return displayTemplate.displayDocument(invoice);
    }
    
    @Override
    public Invoice getInvoice() {
        return this;
    }
    
}
