package edu.avans.hartigehap.domain;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Getter @Setter
@ToString(callSuper=true, includeFieldNames=true)
public class Quotation extends DomainObject{

    private static final long serialVersionUID = 1L;
    
    private int number;
    private Date eventDate;
    private Date expirationDate;
    
//    @OneToMany
//    private Room room;
    
    @ManyToOne(cascade = javax.persistence.CascadeType.ALL)
    private Customer customer;
}
