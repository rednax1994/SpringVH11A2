package edu.avans.hartigehap.domain.states;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import edu.avans.hartigehap.domain.DomainObject;
import edu.avans.hartigehap.domain.Reservation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Getter
@Setter
@ToString(callSuper = true, includeFieldNames = true, of = { "name" })
@NoArgsConstructor
public abstract class ReservationState extends DomainObject{
    private static final long serialVersionUID = 1L;
    
    @OneToOne(mappedBy="currentState")
    private Reservation reservation;
    //CreateEnum
    private String name;
    
    public ReservationState(Reservation reservation, String name){
        this.reservation = reservation;
        this.name = name;
    }
}
