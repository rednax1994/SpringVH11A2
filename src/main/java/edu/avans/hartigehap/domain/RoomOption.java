package edu.avans.hartigehap.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

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
public class RoomOption extends DomainObject {

	private static final long serialVersionUID = 1L;
	
	@ManyToOne()
	private Restaurant restaurant;
	
	@ManyToMany(mappedBy="rooms")
	private List<Room> rooms;

	private long optionNr;
	private String name;
	private String description;
	private long price;
	private boolean isUsed;

	public RoomOption(String name, String description, long price, boolean isUsed) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.isUsed = isUsed;
	}
}
