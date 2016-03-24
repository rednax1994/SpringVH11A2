package edu.avans.hartigehap.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import edu.avans.hartigehap.domain.reservationfactory.Reservation.TimeOfDayEnum;
import edu.avans.hartigehap.service.impl.BanquetingFacadeImpl;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Getter
@Setter
@ToString(callSuper = true, includeFieldNames = true)
@NoArgsConstructor
public class Quotation extends DomainObject {
    
    private Quotation(QuotationBuilder builder) {
        this.number = builder.number;
        this.eventDate = builder.eventDate;
        this.expirationDate = builder.expirationDate;
        this.status = builder.status;
        this.startTimeOfDay = builder.startTimeOfDay;
        this.startTime = builder.startTime;
        this.endTimeOfDay = builder.endTimeOfDay;
        this.endTime = builder.endTime;
        this.customer = builder.customer;
        this.room = builder.room;
        this.amountOfPeople = builder.amountOfPeople;
    }
    
    private static final long serialVersionUID = 1L;
    
    private int number;
    
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @DateTimeFormat(iso = ISO.DATE)
    private DateTime eventDate;
    
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @DateTimeFormat(iso = ISO.DATE)
    private DateTime expirationDate;
    
    @Enumerated(EnumType.STRING)
    protected TimeOfDayEnum startTimeOfDay;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    
    @Enumerated(EnumType.STRING)
    // represented in database as integer
    protected TimeOfDayEnum endTimeOfDay;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;
    
    @ManyToOne(cascade = CascadeType.ALL)
    private Room room;
    
    @ManyToOne
    private Customer customer;
    
    @ManyToOne
    private Restaurant restaurant;
    
    private int amountOfPeople;
    
    @Transient
    private BanquetingFacadeImpl banquetingfacade = new BanquetingFacadeImpl();
    
    @Enumerated(EnumType.ORDINAL)
    // represented in database as integer
    private Status status;
    
    @OneToMany(mappedBy = "quotation")
    private Collection<Line> quotationLines = new ArrayList<Line>();
    
    @OneToMany(mappedBy = "invoice")
    private Collection<Line> invoiceLines = new ArrayList<Line>();
    
    public static class QuotationBuilder {
        
        private int amountOfPeople;
        private Room room;
        private Customer customer;
        private Date endTime;
        private TimeOfDayEnum endTimeOfDay;
        private Date startTime;
        private TimeOfDayEnum startTimeOfDay;
        private Status status;
        private DateTime expirationDate;
        private DateTime eventDate;
        private int number;
        
        public QuotationBuilder(int number) {
            this.number = number;
        }
        
        public QuotationBuilder startTime(Date startTime) {
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
        
        public QuotationBuilder endTime(Date endTime) {
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
    
}
