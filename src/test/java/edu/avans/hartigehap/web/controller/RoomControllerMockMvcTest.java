package edu.avans.hartigehap.web.controller;

import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import edu.avans.hartigehap.domain.Restaurant;
import edu.avans.hartigehap.domain.Room;
import edu.avans.hartigehap.service.RestaurantService;
import edu.avans.hartigehap.service.RoomService;
import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RoomControllerMockMvcTest.class })
@WebAppConfiguration
@ImportResource({ "classpath:/test-root-context.xml", "classpath:*servlet-context.xml" })
@Slf4j
public class RoomControllerMockMvcTest {
    
    private static final String RESTAURANT_ID = "De Plak";
    
    @Autowired
    private RoomController roomController;
    
    @Autowired
    private WebApplicationContext webApplicationContext;
    
    private MockMvc mockMvc;
    
    @Autowired
    private RoomService roomServiceMock;
    @Autowired
    private RestaurantService restaurantServiceMock;
    
    @Before
    public void setUp() {
        // Thanks to: Petri Kinulainen:
        // http://www.petrikainulainen.net/programming/spring-framework/unit-testing-of-spring-mvc-controllers-normal-controllers/
        // https://github.com/pkainulainen/spring-mvc-test-examples/tree/master/controllers-unittest
        
        // We have to reset our mock between tests because the mock objects
        // are managed by the Spring container. If we would not reset them,
        // stubbing and verified behavior would "leak" from one test to another.
        Mockito.reset(roomServiceMock);
        Mockito.reset(restaurantServiceMock);
        
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    
    @Bean
    public RoomService roomService() {
        return Mockito.mock(RoomService.class);
    }
    
    @Bean
    public RestaurantService restaurantService() {
        return Mockito.mock(RestaurantService.class);
    }
    
    /**
     * verifies the 'wiring' of the test case:
     * <ul>
     * <li>autowiring of DiningTableController.</li>
     * <li>autowiring of DiningTableService mock.</li>
     * <li>autowiring of RestaurantService mock.</li>
     * </ul>
     * 
     * @throws Exception
     */
    @Test
    public void dummy() throws Exception {
        log.debug("test the configuration of the test case, 'the wiring'");
        
        assertNotNull(roomController);
        Object roomServiceMock = ReflectionTestUtils.getField(roomController, "roomService");
        assertTrue(roomServiceMock instanceof RoomService);
        String roomServiceMockClassName = roomServiceMock.getClass().getName();
        log.debug("diningTableServiceMockClassName: {}", roomServiceMockClassName);
        assertTrue("diningTableServiceMockClassName contains 'Mock' since it is a mockito mock",
                roomServiceMockClassName.indexOf("Mock") >= 0);
        
        Object restaurantServiceMock = ReflectionTestUtils.getField(roomController, "restaurantService");
        assertTrue(restaurantServiceMock instanceof RestaurantService);
        String restaurantServiceMockClassName = restaurantServiceMock.getClass().getName();
        log.debug("restaurantServiceMockClassname: {}", restaurantServiceMockClassName);
        assertTrue("restaurantServiceMockClassname contains 'Mock' since it is a mockito mock",
                restaurantServiceMockClassName.indexOf("Mock") >= 0);
    }
    
    /**
     * verifies the following qualities of the
     * DiningTableController.showTable():
     * <ul>
     * <li>@RequestMapping annotation.</li>
     * <li>HTTP return code</li>
     * <li>view resolving: the correct view name is returned.</li>
     * <li>model content</li>
     * </ul>
     * 
     * @throws Exception
     */
    @Test
    public void showRoom() throws Exception {
        
        // prepare
        LinkedList<Restaurant> restaurants = new LinkedList<Restaurant>();
        Restaurant restaurant = new Restaurant();
        restaurant.setId(RESTAURANT_ID);
        restaurants.add(restaurant);
        
        Room room = new Room();
        room.setId(1L);
        restaurant.getRooms().add(room);
        room.setRestaurant(restaurant);
        
        // execute
        Mockito.when(restaurantServiceMock.findAll()).thenReturn(restaurants);
        Mockito.when(roomServiceMock.fetchWarmedUp(1L)).thenReturn(room);
        Mockito.when(restaurantServiceMock.fetchWarmedUp(RESTAURANT_ID)).thenReturn(restaurant);
        mockMvc.perform(get("/diningTables/1")).andExpect(status().isOk())
                .andExpect(view().name("hartigehap/diningtable"))
                .andExpect(model().attribute("restaurants", hasItems(restaurants.toArray(new Restaurant[] {}))));
    }
    
    /**
     * verifies the following qualities of the
     * DiningTableController.addMenuItem():
     * <ul>
     * <li>@RequestMapping annotation.</li>
     * <li>HTTP return code</li>
     * <li>view resolving: the correct view name is returned.</li>
     * <li>model content</li>
     * </ul>
     * 
     * @throws Exception
     */
    @Test
    public void addMenuItem() throws Exception {
        
        // prepare
        Room room = new Room();
        room.setId(1L);
        
        // execute
        Mockito.when(roomServiceMock.fetchWarmedUp(1L)).thenReturn(room);
        mockMvc.perform(post("/diningTables/1/menuItems").param("menuItemName", "spaghetti"))
                // isFound() checks for HTTP return code 302 "redirect"
                .andExpect(status().isFound()).andExpect(view().name("redirect:/diningTables/1"))
                .andExpect(model().attribute("diningTable", room));
    }
}
