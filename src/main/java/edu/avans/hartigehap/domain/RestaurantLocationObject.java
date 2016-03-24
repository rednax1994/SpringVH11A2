package edu.avans.hartigehap.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@MappedSuperclass
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public abstract class RestaurantLocationObject extends DomainObject{
    private static final long serialVersionUID = 1L;
    
    public DiningTable getDiningTable(){
        return null;
    }
    public Room getRoom(){
        return null;
    }
}