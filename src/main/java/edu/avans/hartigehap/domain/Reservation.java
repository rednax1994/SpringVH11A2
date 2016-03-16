package edu.avans.hartigehap.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import edu.avans.hartigehap.domain.states.ReservationState;
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

public class Reservation {
	
	@OneToOne
	private ReservationState currentState;
	
	private int amountOfPeople;
	
	private List<String> timeOfDay;
	
	private String booker;
	
	private Date startDate;
	
	private Date endDate;
	
	private Reservation(ReservationState currentState, int amountOfPeople, String booker){
	   
	}
	
}
