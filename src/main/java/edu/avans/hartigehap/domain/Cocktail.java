package edu.avans.hartigehap.domain;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Getter
@Setter
@ToString(callSuper = true, includeFieldNames = true, of = {})
@NoArgsConstructor
public abstract class Cocktail extends Drink {
    private static final long serialVersionUID = 1L;
    
    int cost = 0;
    
    String description = "Unknown cocktail";

    public abstract int cost();
    
    public String getDescription(){
    	return description;
    }
}
