package edu.avans.hartigehap.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.joda.time.DateTime;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import edu.avans.hartigehap.domain.reservationFactory.Reservation.TimeOfDayEnum;
import edu.avans.hartigehap.service.impl.BanquetingFacadeImpl;
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

    @OneToMany(mappedBy = "quotation")
    private Collection<Line> quotationLines = new ArrayList<Line>();
    @Transient
    private BanquetingFacadeImpl banquetingfacade = new BanquetingFacadeImpl();

    @Transient
    private DisplayTemplate displayTemplate = new DisplayQuotation();
    
    public String displayDocument(Quotation quotation){
        String message = displayTemplate.displayDocument(quotation);
        return message;
    }
    
    @Override
    public Quotation getQuotation(){
        return this;
    }

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
