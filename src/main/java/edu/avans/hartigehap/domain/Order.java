package edu.avans.hartigehap.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * 
 * @author Erco
 */
@Entity
@NamedQuery(name = "Order.findSubmittedOrders", query = "SELECT o FROM Order o "
        + "WHERE o.orderStatus = edu.avans.hartigehap.domain.Order$OrderStatus.SUBMITTED "
        + "AND o.bill.diningTable.restaurant = :restaurant " + "ORDER BY o.submittedTime")
// to prevent collision with MySql reserved keyword
@Table(name = "ORDERS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Getter @Setter
@Slf4j
@ToString(callSuper = true, includeFieldNames = true, of = { "orderStatus", "orderItems" })
public class Order extends DomainObject {
    private static final long serialVersionUID = 1L;

    public enum OrderStatus {
        CREATED, SUBMITTED, PLANNED, PREPARED, SERVED
    }

    @Enumerated(EnumType.ORDINAL)
    // represented in database as integer
    private OrderStatus orderStatus;

    @Temporal(TemporalType.TIMESTAMP)
    private Date submittedTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date plannedTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date preparedTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date servedTime;

    // unidirectional one-to-many relationship.
    @OneToMany(cascade = javax.persistence.CascadeType.ALL)
    private Collection<OrderItem> orderItems = new ArrayList<OrderItem>();

    @ManyToOne()
    private Bill bill;

    public Order() {
        orderStatus = OrderStatus.CREATED;
    }

    /* business logic */

    @Transient
    public boolean isSubmittedOrSuccessiveState() {
        return orderStatus != OrderStatus.CREATED;
    }

    // transient annotation, because methods starting with are recognized by JPA
    // as properties
    @Transient
    public boolean isEmpty() {
        return orderItems.isEmpty();
    }

    public void addOrderItem(MenuItem menuItem) {
        Iterator<OrderItem> orderItemIterator = orderItems.iterator();
        boolean found = false;
        while (orderItemIterator.hasNext()) {
            OrderItem orderItem = orderItemIterator.next();
            //log.info(orderItem.toString());
            if (orderItem.getMenuItem().equals(menuItem)) {
                orderItem.incrementQuantity();
                found = true;
                break;
            }
        }
        if (!found) {
            OrderItem orderItem = new ConcreteOrderItem(menuItem, 1);
            orderItems.add(orderItem);
        }
    }
    
    //Only works on the last added item
    public void addOrderOption(MenuItem menuItem, OrderItem orderItem){
    	log.info("started addOrderOption with menuItem: " + menuItem.getId() + " - and orderItem: " + orderItem.getId());
    	OrderOption orderOption = new OrderOption(orderItem, menuItem, 1); 
    	if(orderItem.getMenuItem().equals(menuItem)){
	    	for(OrderItem var : orderItems){
	    		if(menuItem.getId() == var.getMenuItem().getId()){
	    			orderItems.remove(orderItem);
	    			var.incrementQuantity();
	    			orderItems.add(var);
	    		}
	    	}
    	}else {
    		orderItems.remove(orderItem);
    		orderItems.add(orderOption);
    	}
    }
    
    public void removeOrderOption(MenuItem menuItem, OrderItem orderItem){
    	log.info("started addOrderOption with menuItem: " + menuItem.getId() + " - and orderItem: " + orderItem.getId());
    	
    }
    
    
    public void deleteOrderItem(MenuItem menuItem) {
        Iterator<OrderItem> orderItemIterator = orderItems.iterator();
        boolean found = false;
        while (orderItemIterator.hasNext()) {
            OrderItem orderItem = orderItemIterator.next();
            if (orderItem.getMenuItem().equals(menuItem)) {
                found = true;
                if (orderItem.getQuantity() > 1) {
                    orderItem.decrementQuantity();
                } else {
                    // orderItem.getQuantity() == 1
                    orderItemIterator.remove();
                }
                break;
            }
        }
        if (!found) {
            // do nothing
        }
    }

    public void submit() throws StateException {
        if (isEmpty()) {
            throw new StateException("not allowed to submit an empty order");
        }

        // this can only happen by directly invoking HTTP requests, so not via
        // GUI
        if (orderStatus != OrderStatus.CREATED) {
            throw new StateException("not allowed to submit an already submitted order");
        }
        submittedTime = new Date();
        orderStatus = OrderStatus.SUBMITTED;
    }

    public void plan() throws StateException {

        // this can only happen by directly invoking HTTP requests, so not via
        // GUI
        if (orderStatus != OrderStatus.SUBMITTED) {
            throw new StateException("not allowed to plan an order that is not in the submitted state");
        }

        plannedTime = new Date();
        orderStatus = OrderStatus.PLANNED;
    }

    public void prepared() throws StateException {

        // this can only happen by directly invoking HTTP requests, so not via
        // GUI
        if (orderStatus != OrderStatus.PLANNED) {
            throw new StateException(
                    "not allowed to change order state to prepared, if it is not in the planned state");
        }

        preparedTime = new Date();
        orderStatus = OrderStatus.PREPARED;
    }

    public void served() throws StateException {

        // this can only happen by directly invoking HTTP requests, so not via
        // GUI
        if (orderStatus != OrderStatus.PREPARED) {
            throw new StateException("not allowed to change order state to served, if it is not in the prepared state");
        }

        servedTime = new Date();
        orderStatus = OrderStatus.SERVED;
    }

    @Transient
    public int getPrice() {
        int price = 0;
        Iterator<OrderItem> orderItemIterator = orderItems.iterator();
        while (orderItemIterator.hasNext()) {
            price += orderItemIterator.next().getPrice();
        }
        return price;
    }

}
