package edu.avans.hartigehap.domain;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

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
public abstract class DecoratedOrderItem extends OrderItem{
	private static final long serialVersionUID = 1L;
	
	@OneToOne
	private OrderItem orderItem;
    
    public DecoratedOrderItem(OrderItem orderItem, MenuItem menuItem, int quantity) {
        super(menuItem, quantity);
        this.orderItem = orderItem;
    }
	
	public String description(){
		return  orderItem.description() + " " + getMenuItem().getId() + "(" + getQuantity() + ")";
	}
	
	@Transient
	public int getPrice(){
		return orderItem.getPrice() + getMenuItem().getPrice() * getQuantity();
	}
}
