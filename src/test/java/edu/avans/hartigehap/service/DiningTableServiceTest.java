package edu.avans.hartigehap.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import edu.avans.hartigehap.domain.DiningTable;
import edu.avans.hartigehap.domain.Restaurant;
import edu.avans.hartigehap.repository.DiningTableRepository;
import edu.avans.hartigehap.service.testutil.AbstractTransactionRollbackTest;

public class DiningTableServiceTest extends AbstractTransactionRollbackTest {
    
    @Autowired
    private DiningTableRepository diningTableRepository;
    @Autowired
    private RestaurantService restaurantService;
    
    @Test
    public void create() {
        // execute
        DiningTable table = createDiningTables();
        diningTableRepository.save(table);
        
        // verify
        List<DiningTable> tables = (List<DiningTable>) diningTableRepository.findAll();
        assertNotNull(tables);
        assertTrue("created diningTable in list", tables.contains(table));
        
    }
    
    private DiningTable createDiningTables() {
        List<Restaurant> restaurants = restaurantService.findAll();
        for (int i = 0; i < 5; i++) {
            DiningTable diningTable = new DiningTable(i + 1);
            diningTable.setRestaurant(restaurants.get(0));
            restaurants.get(0).getDiningTables().add(diningTable);
            diningTableRepository.save(diningTable);
        }
        return diningTableRepository.findOne((long) 1);
    }
}
