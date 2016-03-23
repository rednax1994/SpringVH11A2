package edu.avans.hartigehap.domain;

import javax.persistence.Entity;
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
public class Invoice extends Document{

    private static final long serialVersionUID = 1L;
    

    public void createFromQuotation(Restaurant restaurant, Quotation quotation) {
        this.number = quotation.getNumber();
        this.eventDate = quotation.getEventDate();
        this.expirationDate = quotation.getExpirationDate();
        this.restaurant = restaurant;
        this.status = quotation.getStatus();
    }
}
