package edu.avans.hartigehap.domain;

import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import edu.avans.hartigehap.domain.reservationfactory.Reservation.TimeOfDayEnum;
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
public class Quotation extends Document {
    
    private static final long serialVersionUID = 1L;
    
    @OneToMany(cascade = CascadeType.PERSIST)
    private Collection<Line> quotationLines = new ArrayList<Line>();
    
    @Transient
    private DisplayTemplate displayTemplate = new DisplayQuotation();
    
    private Quotation(QuotationBuilder builder) {
        this.setNumber(builder.number);
        this.setEventDate(builder.eventDate);
        this.setExpirationDate(builder.expirationDate);
        this.setStatus(builder.status);
        this.setStartTimeOfDay(builder.startTimeOfDay);
        this.setStartTime(builder.startTime);
        this.setEndTimeOfDay(builder.endTimeOfDay);
        this.setEndTime(builder.endTime);
        this.setCustomer(builder.customer);
        this.setRoom(builder.room);
        this.setAmountOfPeople(builder.amountOfPeople);
    }
    
    public String displayDocument(Quotation quotation) {
        return displayTemplate.displayDocument(quotation);
    }
    
    @Override
    public Quotation getQuotation() {
        return this;
    }
    
    public static class QuotationBuilder {
        
        private int amountOfPeople;
        private Room room;
        private Customer customer;
        private DateTime endTime;
        private TimeOfDayEnum endTimeOfDay;
        private DateTime startTime;
        private TimeOfDayEnum startTimeOfDay;
        private Status status;
        private DateTime expirationDate;
        private DateTime eventDate;
        private int number;
        
        public QuotationBuilder(int number) {
            this.number = number;
        }
        
        public QuotationBuilder startTime(DateTime startTime) {
            this.startTime = startTime;
            return this;
        }
        
        public QuotationBuilder startTimeOfDay(TimeOfDayEnum startTimeOfDay) {
            this.startTimeOfDay = startTimeOfDay;
            return this;
        }
        
        public QuotationBuilder customer(Customer customer) {
            this.customer = customer;
            return this;
        }
        
        public QuotationBuilder endTime(DateTime endTime) {
            this.endTime = endTime;
            return this;
        }
        
        public QuotationBuilder endTimeOfDay(TimeOfDayEnum endTimeOfDay) {
            this.endTimeOfDay = endTimeOfDay;
            return this;
        }
        
        public QuotationBuilder status(Status status) {
            this.status = status;
            return this;
        }
        
        public QuotationBuilder expirationDate(DateTime expirationDate) {
            this.expirationDate = expirationDate;
            return this;
        }
        
        public QuotationBuilder eventDate(DateTime eventDate) {
            this.eventDate = eventDate;
            return this;
        }
        
        public QuotationBuilder amountofPeople(int amountOfPeople) {
            this.amountOfPeople = amountOfPeople;
            return this;
        }
        
        public QuotationBuilder room(Room room) {
            this.room = room;
            return this;
        }
        
        public Quotation build() {
            return new Quotation(this);
        }
        
    }
    
    public void updateEditableFields(Quotation quotation) {
        this.setAmountOfPeople(quotation.getAmountOfPeople());
        this.setNumber(quotation.getNumber());
        
    }
    
}
