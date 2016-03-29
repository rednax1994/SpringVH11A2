package edu.avans.hartigehap.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import edu.avans.hartigehap.domain.reservationfactory.Reservation.TimeOfDayEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@MappedSuperclass
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Getter
@Setter
@ToString(includeFieldNames = true)
public abstract class Document extends DomainObject{
    
    private static final long serialVersionUID = 1L;

    protected int number;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @DateTimeFormat(iso = ISO.DATE)
    protected DateTime eventDate;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @DateTimeFormat(iso = ISO.DATE)
    protected DateTime expirationDate;

    @Enumerated(EnumType.STRING)
    protected TimeOfDayEnum startTimeOfDay;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date startTime;

    @Enumerated(EnumType.STRING)
    // represented in database as integer
    protected TimeOfDayEnum endTimeOfDay;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date endTime;

    @ManyToOne(cascade = CascadeType.ALL)
    protected Room room;

    @ManyToOne
    protected Customer customer;

    @ManyToOne
    protected Restaurant restaurant;

    protected int amountOfPeople;

    @Enumerated(EnumType.ORDINAL)
    // represented in database as integer
    protected Status status;
    
    public Invoice getInvoice(){
        return null;
    }
    
    public Quotation getQuotation(){
        return null;
    }
}
