package edu.avans.hartigehap.domain;

import javax.persistence.Entity;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Getter
@Setter
@NoArgsConstructor
public class Line extends DomainObject {
    private static final long serialVersionUID = 1L;
    
    private String description;
    private double price;
    private int quantity;
    private int discount;
    private double total;
    
    public Line(String description, double price, int quantity, int discount){
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.discount = discount;
        this.total = this.price * this.quantity;
    }
}
