package edu.avans.hartigehap.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
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
	private OrderItem orderitem;
    
    public DecoratedOrderItem(OrderItem orderitem, MenuItem menuItem, int quantity) {
        super(menuItem, quantity);
        this.orderitem = orderitem;
    }
	
	public String description(){
		return getMenuItem().getId() + "(" + getQuantity() + ")" + " " + orderitem.description();
	}
	
	@Transient
	public int getPrice(){
		return getMenuItem().getPrice() * getQuantity() + orderitem.getPrice();
		
	}
	
}
