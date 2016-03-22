package edu.avans.hartigehap.domain.decorator;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import edu.avans.hartigehap.domain.MenuItem;
import edu.avans.hartigehap.domain.OrderItem;
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
public abstract class DecoratedOrderItem extends OrderItem {
    private static final long serialVersionUID = 1L;
    
    @OneToOne
    private OrderItem orderItem;
    
    public DecoratedOrderItem(OrderItem orderItem, MenuItem menuItem, int quantity) {
        super(menuItem, quantity);
        this.orderItem = orderItem;
    }
    
    public String description() {
        return orderItem.description() + " + extra " + super.description();
    }
    
    @Transient
    public int getPrice() {
        return orderItem.getPrice() + getMenuItem().getPrice() * getQuantity();
    }
}
