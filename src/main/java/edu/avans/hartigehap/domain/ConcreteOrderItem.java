package edu.avans.hartigehap.domain;

import javax.persistence.Entity;

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
<<<<<<< HEAD
public class ConcreteOrderItem extends OrderItem{  
    private static final long serialVersionUID = 1L;

=======
public class ConcreteOrderItem extends OrderItem {
    private static final long serialVersionUID = 1L;
    
>>>>>>> master
    public ConcreteOrderItem(MenuItem menuItem, int quantity) {
        super(menuItem, quantity);
    }
}
