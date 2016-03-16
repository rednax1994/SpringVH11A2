package edu.avans.hartigehap.domain;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Getter @Setter
@ToString(callSuper=true, includeFieldNames=true)
@NoArgsConstructor
public class Invoice extends DomainObject{

    private static final long serialVersionUID = 1L;
    
    private int number;
    private Date date;
    private Date expirationDate;
    
//    @ManyToOne
//    private Room room;
    
    @ManyToOne
    private Customer customer;
    
//    private TemplateMailer templatemailer;
    
    @Enumerated(EnumType.ORDINAL)
    // represented in database as integer
    private Status status;
}
