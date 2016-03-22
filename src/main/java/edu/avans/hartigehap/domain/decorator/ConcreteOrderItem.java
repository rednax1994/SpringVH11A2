package edu.avans.hartigehap.domain.decorator;

import javax.persistence.Entity;

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

public class ConcreteOrderItem extends OrderItem {
    private static final long serialVersionUID = 1L;


    public ConcreteOrderItem(MenuItem menuItem, int quantity) {
        super(menuItem, quantity);
    }
}
