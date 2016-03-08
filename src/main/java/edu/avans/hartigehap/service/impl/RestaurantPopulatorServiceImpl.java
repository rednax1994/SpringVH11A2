package edu.avans.hartigehap.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.avans.hartigehap.domain.ConcreteOrderItem;
import edu.avans.hartigehap.domain.Customer;
import edu.avans.hartigehap.domain.DiningTable;
import edu.avans.hartigehap.domain.Drink;
import edu.avans.hartigehap.domain.FoodCategory;
import edu.avans.hartigehap.domain.Meal;
import edu.avans.hartigehap.domain.OrderOption;
import edu.avans.hartigehap.domain.Restaurant;
import edu.avans.hartigehap.repository.CustomerRepository;
import edu.avans.hartigehap.repository.FoodCategoryRepository;
import edu.avans.hartigehap.repository.MenuItemRepository;
import edu.avans.hartigehap.repository.OrderItemRepository;
import edu.avans.hartigehap.repository.RestaurantRepository;
import edu.avans.hartigehap.service.RestaurantPopulatorService;
import lombok.extern.slf4j.Slf4j;

@Service("restaurantPopulatorService")
@Repository
@Transactional
@Slf4j
public class RestaurantPopulatorServiceImpl implements RestaurantPopulatorService {

    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private FoodCategoryRepository foodCategoryRepository;
    @Autowired
    private MenuItemRepository menuItemRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    private List<Meal> meals = new ArrayList<>();
    private List<Meal> mealOptions = new ArrayList<>();
    private List<FoodCategory> foodCats = new ArrayList<>();
    private List<Drink> drinks = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();

    /**
     * menu items, food categories and customers are common to all restaurants
     * and should be created only once. Although we can safely assume that the
     * are related to at least one restaurant and therefore are saved via the
     * restaurant, we save them explicitly anyway
     */
    private void createCommonEntities() {
        // create FoodCategories
        createFoodCategory("Low fat");
        createFoodCategory("High energy");
        createFoodCategory("Vegatarian");
        createFoodCategory("Italian");
        createFoodCategory("Asian");
        createFoodCategory("Alcoholic drinks");
        createFoodCategory("Energizing drinks");
        createFoodCategory("Extra options");

        // create Meals
        createMeal("Spaghetti", "spaghetti.jpg", 8, "easy", Arrays.<FoodCategory> asList(foodCats.get(3), foodCats.get(1)));
        createMeal("Macaroni", "macaroni.jpg", 8, "easy", Arrays.<FoodCategory> asList(foodCats.get(3), foodCats.get(1)));
        createMeal("Canneloni", "canneloni.jpg", 9, "easy", Arrays.<FoodCategory> asList(foodCats.get(3), foodCats.get(1)));
        createMeal("Pizza", "pizza.jpg", 9, "easy", Arrays.<FoodCategory> asList(foodCats.get(3), foodCats.get(1)));
        createMeal("Carpaccio", "carpaccio.jpg", 7, "easy", Arrays.<FoodCategory> asList(foodCats.get(3), foodCats.get(0)));
        createMeal("Ravioli", "ravioli.jpg", 8, "easy", Arrays.<FoodCategory> asList(foodCats.get(3), foodCats.get(1), foodCats.get(2)));

        createMealOption("bell pepper", "pizza.jpg", 2, "easy", Arrays.<FoodCategory> asList(foodCats.get(7)));
        createMealOption("mushrooms", "pizza.jpg", 3, "easy", Arrays.<FoodCategory> asList(foodCats.get(7)));
        
        // create Drinks
        createDrink("Large beer", "beer.jpg", 1, Drink.Size.LARGE, Arrays.<FoodCategory> asList(foodCats.get(5)));
        createDrink("Medium beer", "beer.jpg", 1, Drink.Size.MEDIUM, Arrays.<FoodCategory> asList(foodCats.get(5)));
        createDrink("Small beer", "beer.jpg", 1, Drink.Size.SMALL, Arrays.<FoodCategory> asList(foodCats.get(5)));
        createDrink("Large Red Bull", "redbull.jpg", 1, Drink.Size.LARGE, Arrays.<FoodCategory> asList(foodCats.get(6)));
        createDrink("Small Red Bull", "redbull.jpg", 1, Drink.Size.SMALL, Arrays.<FoodCategory> asList(foodCats.get(6)));
        createDrink("Large coffee", "coffee.jpg", 1, Drink.Size.LARGE, Arrays.<FoodCategory> asList(foodCats.get(6)));
        createDrink("Medium coffee", "coffee.jpg", 1, Drink.Size.MEDIUM, Arrays.<FoodCategory> asList(foodCats.get(6)));

        // create Customers
        byte[] photo = new byte[] { 127, -128, 0 };
        createCustomer("Peter", "Limonade", new DateTime(), 1, "description", photo);
        createCustomer("Barry", "Batsbak", new DateTime(), 1, "description", photo);
        createCustomer("Piet", "Bakker", new DateTime(), 1, "description", photo);
        createCustomer("Piet", "Bakker", new DateTime(), 1, "description", photo);
        createCustomer("Piet", "Bakker", new DateTime(), 1, "description", photo);
    }

    private void createMealOption(String name, String image, int price, String recipe, List<FoodCategory> foodCats) {
    	Meal meal = new Meal(name, image, price, recipe);
    	meal.addFoodCategories(foodCats);
    	meal = menuItemRepository.save(meal);
    	mealOptions.add(meal);
    }

    
    private void createFoodCategory(String tag) {
        FoodCategory foodCategory = new FoodCategory(tag);
        foodCategory = foodCategoryRepository.save(foodCategory);
        foodCats.add(foodCategory);
    }

    private void createMeal(String name, String image, int price, String recipe, List<FoodCategory> foodCats) {
        
        Meal meal = new Meal(name, image, price, recipe);
        // as there is no cascading between FoodCategory and MenuItem (both
        // ways), it is important to first
        // save foodCategory and menuItem before relating them to each other,
        // otherwise you get errors
        // like "object references an unsaved transient instance - save the
        // transient instance before flushing:"
        meal.addFoodCategories(foodCats);
        meal = menuItemRepository.save(meal);
        meals.add(meal);
    }

    private void createDrink(String name, String image, int price, Drink.Size size, List<FoodCategory> foodCats) {
        Drink drink = new Drink(name, image, price, size);
        drink = menuItemRepository.save(drink);
        drink.addFoodCategories(foodCats);
        drinks.add(drink);
    }

    private void createCustomer(String firstName, String lastName, DateTime birthDate, int partySize,
            String description, byte[] photo) {
        Customer customer = new Customer(firstName, lastName, birthDate, partySize, description, photo);
        customers.add(customer);
        customerRepository.save(customer);
    }

    private void createDiningTables(int numberOfTables, Restaurant restaurant) {
        for (int i = 0; i < numberOfTables; i++) {
            DiningTable diningTable = new DiningTable(i + 1);
            diningTable.setRestaurant(restaurant);
            restaurant.getDiningTables().add(diningTable);
        }
    }

    private Restaurant populateRestaurant(Restaurant restaurant) {

        // will save everything that is reachable by cascading
        // even if it is linked to the restaurant after the save
        // operation
        restaurant = restaurantRepository.save(restaurant);

        // every restaurant has its own dining tables
        createDiningTables(5, restaurant);

        // for the moment every restaurant has all available food categories
        for (FoodCategory foodCat : foodCats) {
            restaurant.getMenu().getFoodCategories().add(foodCat);
        }

        // for the moment every restaurant has the same menu
        for (Meal meal : meals) {
            restaurant.getMenu().getMeals().add(meal);
        }

        // for the moment every restaurant has the same menu
        for (Drink drink : drinks) {
            restaurant.getMenu().getDrinks().add(drink);
        }

        // for the moment, every customer has dined in every restaurant
        // no cascading between customer and restaurant; therefore both
        // restaurant and customer
        // must have been saved before linking them one to another
        for (Customer customer : customers) {
            customer.getRestaurants().add(restaurant);
            restaurant.getCustomers().add(customer);
        }

        return restaurant;
    }

    public void createRestaurantsWithInventory() {

        createCommonEntities();
        
        Restaurant restaurant = new Restaurant(HARTIGEHAP_RESTAURANT_NAME, "deHartigeHap.jpg");
        restaurant = populateRestaurant(restaurant);

        restaurant = new Restaurant(PITTIGEPANNEKOEK_RESTAURANT_NAME, "dePittigePannekoek.jpg");
        restaurant = populateRestaurant(restaurant);

        restaurant = new Restaurant(HMMMBURGER_RESTAURANT_NAME, "deHmmmBurger.jpg");
        restaurant = populateRestaurant(restaurant);
        
        ConcreteOrderItem orderItem = new ConcreteOrderItem(meals.get(3), 1); // pizza
        orderItemRepository.save(orderItem);
        OrderOption orderOption = new OrderOption(orderItem, mealOptions.get(1), 1); // mushrooms
        orderItemRepository.save(orderOption);
        //OrderOption orderOption2 = new OrderOption(orderOption, mealOptions.get(0), 1); // mushrooms
        //orderItemRepository.save(orderOption2);
//        OrderOption orderOption3 = new OrderOption(orderOption2, mealOptions.get(0), 1); // bell pepper
//        orderItemRepository.save(orderOption3);

        //log.info("***************************** description: " + orderOption2.description());
        //log.info("***************************** price: " + orderOption2.getPrice());


        // add the decorated pizza to the current order to table 1 of the hmmm burger (to show it in the GUI)
        Collection<DiningTable> diningTables = restaurant.getDiningTables(); // dining tables of the hmmm burger
        DiningTable t = null;
        Iterator<DiningTable> it = diningTables.iterator();
        if(it.hasNext()) {
        t = it.next(); // this is dining table 1
        }

        t.getCurrentBill().getCurrentOrder().getOrderItems().add(orderOption);

    }
}
