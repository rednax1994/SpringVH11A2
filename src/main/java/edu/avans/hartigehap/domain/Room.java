package edu.avans.hartigehap.domain;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "room")
    private Collection<Reservation> reservations = new ArrayList<Reservation>();
    
    @ManyToOne()
    private Restaurant restaurant;
    
    public Room(long roomNr, Boolean occupied, int capacity) {
        this.roomNr = roomNr;
        this.occupied = occupied;
        this.capacity = capacity;
    }
}
