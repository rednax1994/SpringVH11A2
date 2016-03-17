package edu.avans.hartigehap.domain;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

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
public class Room extends DomainObject {
    private static final long serialVersionUID = 1L;
    
    private long roomNr;
    
    private Boolean occupied;
    
    private int capacity;
    
    @OneToOne(cascade = javax.persistence.CascadeType.ALL)
    private Bill currentBill;
    
    @OneToMany()
    private Collection<Reservation> reservations = new ArrayList<Reservation>();
    
    @ManyToOne()
    private Restaurant restaurant;
    
    public Room(long roomNr, Boolean occupied, int capacity) {
        this.roomNr = roomNr;
        this.occupied = occupied;
        this.capacity = capacity;
    }
}
