package edu.avans.hartigehap.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Getter @Setter
@ToString(callSuper = true, includeFieldNames = true)
public class Line extends DomainObject {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String description;
    private double price;
    private int quantity;
    private int discount;
    private double total;
    
    @ManyToOne
    private Quotation quotation;
    
    @ManyToOne
    private Invoice invoice;
}
