package edu.avans.hartigehap.web.controller;

import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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

import edu.avans.hartigehap.domain.Invoice;
import edu.avans.hartigehap.domain.Quotation;
import edu.avans.hartigehap.domain.Restaurant;
import edu.avans.hartigehap.service.BanquetingFacadeService;
import edu.avans.hartigehap.service.InvoiceService;
import edu.avans.hartigehap.service.QuotationService;
import edu.avans.hartigehap.service.RestaurantService;
import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { BanquetingControllerMockMvcTest.class })
@WebAppConfiguration
@ImportResource({ "classpath:/test-root-context.xml", "classpath:*servlet-context.xml" })
@Slf4j
public class BanquetingControllerMockMvcTest {
    private static final String RESTAURANT_ID = "De Plak";
    
    @Autowired
    private BanquetingController banquetingController;
    
    @Autowired
    private WebApplicationContext webApplicationContext;
    
    private MockMvc mockMvc;
    
    @Autowired
    private BanquetingFacadeService banquetingFacadeServiceMock;
    @Autowired
    private RestaurantService restaurantServiceMock;
    @Autowired
    private QuotationService quotationServiceMock;
    @Autowired
    private InvoiceService invoiceServiceMock;
    
    @Before
    public void setUp() {
        Mockito.reset(banquetingFacadeServiceMock);
        Mockito.reset(restaurantServiceMock);
        Mockito.reset(quotationServiceMock);
        Mockito.reset(invoiceServiceMock);
        
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    
    @Bean
    public BanquetingFacadeService banquetingFacadeService() {
        return Mockito.mock(BanquetingFacadeService.class);
    }
    
    @Bean
    public RestaurantService restaurantService() {
        return Mockito.mock(RestaurantService.class);
    }
    
    @Bean
    public QuotationService quotationService() {
        return Mockito.mock(QuotationService.class);
    }
    
    @Bean
    public InvoiceService invoiceService() {
        return Mockito.mock(InvoiceService.class);
    }
    
    @Test
    public void dummy() throws Exception {
        log.debug("test the configuration of the test case, 'the wiring'");
        
        assertNotNull(banquetingController);
        Object banquetingFacadeServiceMock = ReflectionTestUtils.getField(banquetingController,
                "banquetingFacadeService");
        assertTrue(banquetingFacadeServiceMock instanceof BanquetingFacadeService);
        String banquetingFacadeServiceMockClassName = banquetingFacadeServiceMock.getClass().getName();
        log.debug("banquetingFacadeServiceMockClassName: {}", banquetingFacadeServiceMockClassName);
        assertTrue("banquetingFacadeServiceMockClassName contains 'Mock' since it is a mockito mock",
                banquetingFacadeServiceMockClassName.indexOf("Mock") >= 0);
        
        Object restaurantServiceMock = ReflectionTestUtils.getField(banquetingController, "restaurantService");
        assertTrue(restaurantServiceMock instanceof RestaurantService);
        String restaurantServiceMockClassName = restaurantServiceMock.getClass().getName();
        log.debug("restaurantServiceMockClassname: {}", restaurantServiceMockClassName);
        assertTrue("restaurantServiceMockClassname contains 'Mock' since it is a mockito mock",
                restaurantServiceMockClassName.indexOf("Mock") >= 0);
        
        Object quotationServiceMock = ReflectionTestUtils.getField(banquetingController, "quotationService");
        assertTrue(quotationServiceMock instanceof QuotationService);
        String quotationServiceMockClassName = quotationServiceMock.getClass().getName();
        log.debug("quotationServiceMockClassName: {}", quotationServiceMockClassName);
        assertTrue("quotationServiceMockClassName contains 'Mock' since it is a mockito mock",
                quotationServiceMockClassName.indexOf("Mock") >= 0);
        
        Object invoiceServiceMock = ReflectionTestUtils.getField(banquetingController, "invoiceService");
        assertTrue(invoiceServiceMock instanceof InvoiceService);
        String invoiceServiceMockClassName = invoiceServiceMock.getClass().getName();
        log.debug("invoiceServiceMockClassName: {}", invoiceServiceMockClassName);
        assertTrue("invoiceServiceMockClassName contains 'Mock' since it is a mockito mock",
                invoiceServiceMockClassName.indexOf("Mock") >= 0);
    }
    
    @Test
    public void listQuotationAndInvoicesTest() throws Exception {
        // prepare
        LinkedList<Restaurant> restaurants = new LinkedList<Restaurant>();
        Restaurant restaurant = new Restaurant();
        restaurant.setId(RESTAURANT_ID);
        restaurants.add(restaurant);
        
        List<Quotation> quotations = new ArrayList<Quotation>();
        Quotation quotation = new Quotation();
        quotation.setId(1L);
        quotations.add(quotation);
        
        List<Invoice> invoices = new ArrayList<Invoice>();
        Invoice invoice = new Invoice();
        invoice.setId(1L);
        invoices.add(invoice);
        
        // execute
        Mockito.when(restaurantServiceMock.findAll()).thenReturn(restaurants);
        Mockito.when(restaurantServiceMock.fetchWarmedUp(RESTAURANT_ID)).thenReturn(restaurant);
        Mockito.when(quotationServiceMock.findQuotationsForRestaurant(restaurant)).thenReturn(quotations);
        Mockito.when(invoiceServiceMock.findInvoicesForRestaurant(restaurant)).thenReturn(invoices);
        mockMvc.perform(get("/restaurants/" + RESTAURANT_ID + "/banqueting")).andExpect(status().isOk())
                .andExpect(view().name("hartigehap/banquetinglist"))
                .andExpect(model().attribute("restaurants", hasItems(restaurants.toArray(new Restaurant[] {}))))
                .andExpect(model().attribute("quotations", hasItems(quotations.toArray(new Quotation[] {}))))
                .andExpect(model().attribute("invoices", hasItems(invoices.toArray(new Invoice[] {}))));
    }
    
    @Test
    public void showQuotationTest() throws Exception {
        // prepare
        LinkedList<Restaurant> restaurants = new LinkedList<Restaurant>();
        Restaurant restaurant = new Restaurant();
        restaurant.setId(RESTAURANT_ID);
        restaurants.add(restaurant);
        
        Quotation quotation = new Quotation();
        quotation.setId(1L);
        
        // execute
        Mockito.when(restaurantServiceMock.findAll()).thenReturn(restaurants);
        Mockito.when(quotationServiceMock.findById(1L)).thenReturn(quotation);
        
        mockMvc.perform(get("/restaurants/" + RESTAURANT_ID + "/banqueting/quotations/1")).andExpect(status().isOk())
                .andExpect(view().name("hartigehap/showquotation"))
                .andExpect(model().attribute("restaurants", hasItems(restaurants.toArray(new Restaurant[] {}))))
                .andExpect(model().attribute("quotation", quotation));
    }
    
}
