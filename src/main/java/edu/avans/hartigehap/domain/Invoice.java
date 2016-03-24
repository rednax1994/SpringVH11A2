package edu.avans.hartigehap.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

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
@ToString(callSuper = true, includeFieldNames = true)
@NoArgsConstructor
public class Invoice extends DomainObject {
    
    private static final long serialVersionUID = 1L;
    
    private int number;
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @DateTimeFormat(iso = ISO.DATE)
    private DateTime date;
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @DateTimeFormat(iso = ISO.DATE)
    private DateTime expirationDate;
    
    @ManyToOne
    private Room room;
    
    @ManyToOne
    private Customer customer;
    
    @ManyToOne
    private Restaurant restaurant;
    
    @Enumerated(EnumType.ORDINAL)
    // represented in database as integer
    private Status status;
    
    public void createFromQuotation(Restaurant restaurant, Quotation quotation) {
        this.number = quotation.getNumber();
        this.date = quotation.getEventDate();
        this.expirationDate = quotation.getExpirationDate();
        this.restaurant = restaurant;
        this.status = quotation.getStatus();
    }
}
