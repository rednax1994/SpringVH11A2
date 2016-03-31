package edu.avans.hartigehap.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.avans.hartigehap.domain.Customer;
import edu.avans.hartigehap.domain.DiningTable;
import edu.avans.hartigehap.domain.Drink;
import edu.avans.hartigehap.domain.FoodCategory;
import edu.avans.hartigehap.domain.Meal;
import edu.avans.hartigehap.domain.Quotation;
import edu.avans.hartigehap.domain.Restaurant;
import edu.avans.hartigehap.domain.RestaurantLocationObject;
import edu.avans.hartigehap.domain.Room;
import edu.avans.hartigehap.domain.RoomOption;
import edu.avans.hartigehap.domain.Status;
import edu.avans.hartigehap.domain.decorator.ConcreteOrderItem;
import edu.avans.hartigehap.domain.decorator.OrderOption;
import edu.avans.hartigehap.domain.exception.MyException;
import edu.avans.hartigehap.domain.exception.StateException;
import edu.avans.hartigehap.domain.reservationfactory.Reservation;
import edu.avans.hartigehap.domain.reservationfactory.Reservation.TimeOfDayEnum;
import edu.avans.hartigehap.domain.reservationfactory.ReservationFactory;
import edu.avans.hartigehap.repository.CustomerRepository;
import edu.avans.hartigehap.repository.FoodCategoryRepository;
import edu.avans.hartigehap.repository.MenuItemRepository;
import edu.avans.hartigehap.repository.OrderItemRepository;
import edu.avans.hartigehap.repository.ReservationRepository;
import edu.avans.hartigehap.repository.RestaurantRepository;
import edu.avans.hartigehap.repository.RoomOptionRepository;
import edu.avans.hartigehap.repository.RoomRepository;
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
	@Autowired
	private ReservationRepository reservationRepository;
	@Autowired
	private RoomOptionRepository roomOptionRepository;
	@Autowired
	private RoomRepository roomRepository;

	private List<Meal> meals = new ArrayList<>();
	private List<Meal> mealOptions = new ArrayList<>();
	private List<FoodCategory> foodCats = new ArrayList<>();
	private List<Drink> drinks = new ArrayList<>();
	private List<Customer> customers = new ArrayList<>();

	public static final int ONE = 1;
	public static final int TWO = 2;
	public static final int THREE = 3;
	public static final int FOUR = 4;
	public static final int FIVE = 5;
	public static final int SIX = 6;
	public static final int SEVEN = 7;
	public static final int EIGHT = 8;
	public static final int NINE = 9;
	public static final int HUNDEREDTWENTYSEVEN = 127;
	public static final int NEGATIVEHUNDEREDTWENTYEIGHT = -128;

	/**
	 * menu items, food categories and customers are common to all restaurants
	 * and should be created only once. Although we can safely assume that the
	 * are related to at least ONE restaurant and therefore are saved via the
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
		createMeal("Spaghetti", "spaghetti.jpg", EIGHT, "easy",
				Arrays.<FoodCategory> asList(foodCats.get(THREE), foodCats.get(ONE)));
		createMeal("Macaroni", "macaroni.jpg", EIGHT, "easy",
				Arrays.<FoodCategory> asList(foodCats.get(THREE), foodCats.get(ONE)));
		createMeal("Canneloni", "canneloni.jpg", NINE, "easy",
				Arrays.<FoodCategory> asList(foodCats.get(THREE), foodCats.get(ONE)));
		createMeal("Pizza", "pizza.jpg", NINE, "easy",
				Arrays.<FoodCategory> asList(foodCats.get(THREE), foodCats.get(ONE)));
		createMeal("Carpaccio", "carpaccio.jpg", SEVEN, "easy",
				Arrays.<FoodCategory> asList(foodCats.get(THREE), foodCats.get(0)));
		createMeal("Ravioli", "ravioli.jpg", EIGHT, "easy",
				Arrays.<FoodCategory> asList(foodCats.get(THREE), foodCats.get(ONE), foodCats.get(TWO)));

		createMealOption("bell pepper", "pizza.jpg", TWO, "easy", Arrays.<FoodCategory> asList(foodCats.get(SEVEN)));
		createMealOption("mushrooms", "pizza.jpg", THREE, "easy", Arrays.<FoodCategory> asList(foodCats.get(SEVEN)));

		// create Drinks
		createDrink("Large beer", "beer.jpg", ONE, Drink.Size.LARGE, Arrays.<FoodCategory> asList(foodCats.get(FIVE)));
		createDrink("Medium beer", "beer.jpg", ONE, Drink.Size.MEDIUM,
				Arrays.<FoodCategory> asList(foodCats.get(FIVE)));
		createDrink("Small beer", "beer.jpg", ONE, Drink.Size.SMALL, Arrays.<FoodCategory> asList(foodCats.get(FIVE)));
		createDrink("Large Red Bull", "redbull.jpg", ONE, Drink.Size.LARGE,
				Arrays.<FoodCategory> asList(foodCats.get(SIX)));
		createDrink("Small Red Bull", "redbull.jpg", ONE, Drink.Size.SMALL,
				Arrays.<FoodCategory> asList(foodCats.get(SIX)));
		createDrink("Large coffee", "coffee.jpg", ONE, Drink.Size.LARGE,
				Arrays.<FoodCategory> asList(foodCats.get(SIX)));
		createDrink("Medium coffee", "coffee.jpg", ONE, Drink.Size.MEDIUM,
				Arrays.<FoodCategory> asList(foodCats.get(SIX)));

		// create Customers
		byte[] photo = new byte[] { HUNDEREDTWENTYSEVEN, NEGATIVEHUNDEREDTWENTYEIGHT, 0 };
		createCustomer("Peter", "Limonade", new DateTime(), ONE, "description", photo);
		createCustomer("Barry", "Batsbak", new DateTime(), ONE, "description", photo);
		createCustomer("Piet", "Bakker", new DateTime(), ONE, "description", photo);
		createCustomer("Piet", "Bakker", new DateTime(), ONE, "description", photo);
		createCustomer("Piet", "Bakker", new DateTime(), ONE, "description", photo);

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
			DiningTable diningTable = new DiningTable(i + ONE);
			diningTable.setRestaurant(restaurant);
			restaurant.getDiningTables().add(diningTable);
		}
	}

	private void createRoom(long roomNr, boolean occupied, int capacity, Restaurant restaurant) {
		Room room = new Room(roomNr, occupied, capacity);
		room.setRestaurant(restaurant);
		restaurant.getRooms().add(room);

		RoomOption roomOption = new RoomOption("Buffet", "Western Buffet", (long) 50, false, room);
		RoomOption roomOption1 = new RoomOption("Coffee", "Coffee and Tea", (long) 80, false, room);
		RoomOption roomOption2 = new RoomOption("Beamer", "Beamer and screen", (long) 120, false, room);
		RoomOption roomOption3 = new RoomOption("Microphone", "Set of 3 wireless microphones", (long) 50, false, room);
		RoomOption roomOption4 = new RoomOption("Champagne", "Martini proseco", (long) 50, false, room);

		roomRepository.save(room);
		roomOptionRepository.save(roomOption);
		roomOptionRepository.save(roomOption1);
		roomOptionRepository.save(roomOption2);
		roomOptionRepository.save(roomOption3);
		roomOptionRepository.save(roomOption4);

	}

	private void createQuotation(int number, DateTime eventDate, DateTime expirationDate, Status status,
			int amountOfPeople, Room room, Customer customer, Date endTime, TimeOfDayEnum endTimeOfDay, Date startTime,
			TimeOfDayEnum startTimeOfDay, Restaurant restaurant) {
		Quotation quotation = new Quotation.QuotationBuilder(1).status(status).eventDate(eventDate)
				.expirationDate(expirationDate).amountofPeople(amountOfPeople).room(room).customer(customer)
				.endTime(endTime).endTimeOfDay(endTimeOfDay).startTime(startTime).startTimeOfDay(startTimeOfDay)
				.build();
		quotation.setRestaurant(restaurant);
		restaurant.getQuotations().add(quotation);
	}

	private Restaurant populateRestaurant(Restaurant restaurant) {

		/*
		 * room options added
		 */

		// will save everything that is reachable by cascading
		// even if it is linked to the restaurant after the save
		// operation
		Restaurant restaurant2 = restaurantRepository.save(restaurant);

		// every restaurant has its own dining tables
		createDiningTables(FIVE, restaurant2);

		// create Rooms
		createRoom(1, false, 80, restaurant2);
		createRoom(2, true, 100, restaurant2);
		createRoom(3, false, 250, restaurant2);

		ArrayList<Room> rooms = new ArrayList<Room>();

		for (Room room : restaurant2.getRooms()) {
			rooms.add(room);
		}

		createQuotation(15000, new DateTime(2016, 6, 23, 0, 0), new DateTime(2016, 8, 23, 0, 0), Status.CONCEPT, 50,
				rooms.get(0), customers.get(0), new Date(2016, 6, 23, 19, 20), TimeOfDayEnum.EVENING,
				new Date(2016, 6, 23, 8, 5), TimeOfDayEnum.MORNING, restaurant2);
		createQuotation(15001, new DateTime(2016, 8, 26, 0, 0), new DateTime(2016, 10, 26, 0, 0), Status.CONCEPT, 80,
				rooms.get(1), customers.get(1), new Date(2016, 8, 26, 20, 18), TimeOfDayEnum.EVENING,
				new Date(2016, 8, 26, 12, 0), TimeOfDayEnum.NOON, restaurant2);

		// for the moment every restaurant has all available food categories
		for (FoodCategory foodCat : foodCats) {
			restaurant2.getMenu().getFoodCategories().add(foodCat);
		}

		// for the moment every restaurant has the same menu
		for (Meal meal : meals) {
			restaurant2.getMenu().getMeals().add(meal);
		}

		// for the moment every restaurant has the same menu
		for (Drink drink : drinks) {
			restaurant2.getMenu().getDrinks().add(drink);
		}

		// for the moment, every customer has dined in every restaurant
		// no cascading between customer and restaurant; therefore both
		// restaurant and customer
		// must have been saved before linking them ONE to another
		for (Customer customer : customers) {
			customer.getRestaurants().add(restaurant2);
			restaurant2.getCustomers().add(customer);
		}
		log.info("LOGGER TEST");

		return restaurant2;
	}

	public void createRestaurantsWithInventory() {

		createCommonEntities();

		Restaurant restaurant = new Restaurant(HARTIGEHAP_RESTAURANT_NAME, "deHartigeHap.jpg");
		restaurant = populateRestaurant(restaurant);

		restaurant = new Restaurant(PITTIGEPANNEKOEK_RESTAURANT_NAME, "dePittigePannekoek.jpg");
		restaurant = populateRestaurant(restaurant);

		restaurant = new Restaurant(HMMMBURGER_RESTAURANT_NAME, "deHmmmBurger.jpg");
		restaurant = populateRestaurant(restaurant);

		ConcreteOrderItem orderItem = new ConcreteOrderItem(meals.get(THREE), ONE); // pizza
		orderItemRepository.save(orderItem);
		OrderOption orderOption = new OrderOption(orderItem, mealOptions.get(ONE), ONE); // mushrooms
		orderItemRepository.save(orderOption);

		log.info("***************************** description: " + orderOption.description());
		log.info("***************************** price: " + orderOption.getPrice());

		// add the decorated pizza to the current order to table ONE of the hmmm
		// burger (to show it in the GUI)
		// dining tables of the hmmm burger
		Collection<DiningTable> diningTables = restaurant.getDiningTables();
		DiningTable t = null;
		Iterator<DiningTable> it = diningTables.iterator();
		if (it.hasNext()) {
			// this is dining table ONE
			t = it.next();
		}

		t.getCurrentBill().getCurrentOrder().getOrderItems().add(orderOption);

		/*
		 * Reservation test
		 */
		try {
			RestaurantLocationObject rlo = t;
			Reservation res = ReservationFactory.createReservation(21, customers.get(0),
					Reservation.TimeOfDayEnum.MORNING, new Date(), Reservation.TimeOfDayEnum.EVENING, new Date(), rlo);
			res = reservationRepository.save(res);
			res.getCurrentState().acceptReservation();
		} catch (MyException e) {
			// TODO Auto-generated catch block
			log.debug("" + e.getMessage());
		} catch (StateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
