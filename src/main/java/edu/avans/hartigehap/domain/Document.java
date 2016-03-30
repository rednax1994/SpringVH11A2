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

    private int number;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @DateTimeFormat(iso = ISO.DATE)
    private DateTime eventDate;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @DateTimeFormat(iso = ISO.DATE)
    private DateTime expirationDate;

    @Enumerated(EnumType.STRING)
    private TimeOfDayEnum startTimeOfDay;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    @Enumerated(EnumType.STRING)
    // represented in database as integer
    private TimeOfDayEnum endTimeOfDay;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Room room;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Restaurant restaurant;

    private int amountOfPeople;

    @Enumerated(EnumType.ORDINAL)
    // represented in database as integer
    private Status status;
    
    public Invoice getInvoice(){
        return null;
    }
    
    public Quotation getQuotation(){
        return null;
    }
}
