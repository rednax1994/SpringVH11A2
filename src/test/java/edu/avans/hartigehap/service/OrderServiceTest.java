package edu.avans.hartigehap.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import edu.avans.hartigehap.domain.MenuItem;
import edu.avans.hartigehap.domain.Order;
import edu.avans.hartigehap.domain.Restaurant;
import edu.avans.hartigehap.domain.exception.StateException;
import edu.avans.hartigehap.repository.OrderRepository;
import edu.avans.hartigehap.service.testutil.AbstractTransactionRollbackTest;

public class OrderServiceTest extends AbstractTransactionRollbackTest {
    
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private OrderRepository orderRepository;
    
    @Test
    public void create() {
        // execute
        Order order = createOrder();
        orderRepository.save(order);
        
        // verify
        List<Order> orders = (List<Order>) orderRepository.findAll();
        assertNotNull(orders);
        assertTrue("created order in list", orders.contains(order));
    }
    
    @Test
    public void read() {
        // execute
        Restaurant restaurant = restaurantService.findAll().get(0);
        List<Order> orders = orderRepository.findSubmittedOrdersForRestaurant(restaurant);
        
        // verify
        assertNotNull(orders);
    }
    
    @Test
    public void update() throws StateException {
        // execute
        Order order = createOrder();
        String orderState1 = orderRepository.save(order).getOrderStatus().toString();
        
        order.plan();
        orderRepository.save(order);
        
        // verify
        assertFalse(orderState1 == orderRepository.findOne(order.getId()).getOrderStatus().toString());
    }
    
    @Test
    public void delete() {
        // execute
        Order order = createOrder();
        orderRepository.save(order);
        
        Order createdOrder = orderRepository.findOne(order.getId());
        assertNotNull(createdOrder);
        
        orderRepository.delete(createdOrder);
        
        // verify
        assertNotSame(createdOrder, orderRepository.findOne(createdOrder.getId()));
    }
    
    private Order createOrder() {
        Order order = new Order();
        List<Restaurant> restaurants = restaurantService.findAll();
        List<MenuItem> meals = new ArrayList<>(restaurants.get(0).getMenu().getMeals());
        for (MenuItem item : meals) {
            order.addOrderItem(item);
        }
        try {
            order.submit();
        } catch (StateException e) {
            return null;
        }
        return order;
    }
    
}
