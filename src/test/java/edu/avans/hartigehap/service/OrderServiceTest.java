package edu.avans.hartigehap.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import edu.avans.hartigehap.domain.Customer;
import edu.avans.hartigehap.domain.MenuItem;
import edu.avans.hartigehap.domain.Order;
import edu.avans.hartigehap.domain.StateException;
import edu.avans.hartigehap.repository.MenuItemRepository;
import edu.avans.hartigehap.repository.OrderRepository;

public class OrderServiceTest {
	@Autowired
	private OrderRepository orderRepository;
	private MenuItemRepository menuItemRepository;
	
    @Test
    public void create() throws StateException {
        // execute
        Order order = createOrder();
        order.submit();

        // verify
        List<Order> orders = (List<Order>) orderRepository.findAll();
        assertNotNull(orders);
        assertTrue("created order in list", orders.contains(order));
    }
    
    private Order createOrder() {
    	Order order = new Order();
    	MenuItem bier = menuItemRepository.findOne("Bier");
    	MenuItem spaghetti = menuItemRepository.findOne("Spaghetti");    	
    	order.addOrderItem(spaghetti);
    	order.addOrderItem(bier);
    	return order;
    }
}
