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
@Getter @Setter
@ToString(callSuper=false, includeFieldNames=true)
@NoArgsConstructor
public class Invoice extends Document{

    private static final long serialVersionUID = 1L;
    
    @OneToMany(mappedBy = "invoice")
    private Collection<Line> invoiceLines = new ArrayList<Line>();
    
    @Transient
    private DisplayTemplate displayTemplate = new DisplayInvoice();
   
    public void createFromQuotation(Restaurant restaurant, Quotation quotation) {
        this.number = quotation.getNumber();
        this.eventDate = quotation.getEventDate();
        this.expirationDate = quotation.getExpirationDate();
        this.restaurant = restaurant;
        this.status = quotation.getStatus();
        this.room = quotation.getRoom();
        this.customer = quotation.getCustomer();
        this.amountOfPeople = quotation.getAmountOfPeople();
        this.endTime = quotation.getEndTime();
        this.endTimeOfDay = quotation.getEndTimeOfDay();
        this.startTime = quotation.getStartTime();
        this.startTimeOfDay = quotation.getStartTimeOfDay();
    }

    public String displayDocument(Invoice invoice) {
        String message = displayTemplate.displayDocument(invoice);
        return message;
    }
    @Override
    public Invoice getInvoice(){
        return this;
    }

}
