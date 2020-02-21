package com.example.sampleratelimitedapi.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.example.sampleratelimitedapi.models.Hotel;
import com.example.sampleratelimitedapi.services.HotelService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

/**
 *
 *
 * <h1>HotelControllerTests</h1>
 *
 * <p>This class contains the Unit Tests for the HotelController class
 *
 * <p>We use Mockito to generate a mocked instance based on the HotelService interface
 *
 * @author Marco Giuliani
 * @version 1.0
 * @since 2020-02-23
 */
@ExtendWith(MockitoExtension.class)
public class HotelControllerTests {
  private static final String CITY = "Bangkok";
  private static final String DIFFERENT_CITY = "Amsterdam";
  private static final String NOT_AVAILABLE_CITY = "Guadalupe";

  private static final String ROOM = "Deluxe";
  private static final String DIFFERENT_ROOM = "Superior";
  private static final String NOT_AVAILABLE_ROOM = "GigaDeluxe";

  @Mock private HotelService hotelService;

  private static List<Hotel> sameCityHotels;
  private static List<Hotel> sameRoomHotels;

  /**
   * setup() creates three Hotel instances, and adds them to sameCityHotels / sameRoomHotels List
   * objects
   */
  @BeforeAll
  public static void setup() {
    Hotel firstHotel = new Hotel(1L, CITY, DIFFERENT_ROOM, 1400);
    Hotel secondHotel = new Hotel(2L, DIFFERENT_CITY, ROOM, 2300);
    Hotel thirdHotel = new Hotel(3L, CITY, ROOM, 1900);

    sameCityHotels = Arrays.asList(firstHotel, thirdHotel);
    sameRoomHotels = Arrays.asList(secondHotel, thirdHotel);
  }

  // findAllHotelsByCity

  /**
   * Calls the "findAllHotelsByCity" method that should return a list of Hotel in {CITY} ordered by
   * price ASC.
   */
  @Test
  public void findByExistingCityAsc() {
    // Mockito configuration
    sameCityHotels.sort(Comparator.comparing(Hotel::getPrice));
    when(hotelService.findAllHotelsByCity(CITY, "ASC")).thenReturn(sameCityHotels);

    // Test (without Rate Limiter)
    HotelController hotelController = new HotelController(this.hotelService, null, null);
    ResponseEntity<List<Hotel>> hotels = hotelController.findAllHotelsByCity(CITY, "ASC");

    assertThat(hotels.getBody()).isNotNull();
    assertThat(hotels.getBody().size()).isEqualTo(2);

    // Every hotel should be based in {CITY}
    // The price should never decrease
    Integer previousPrice = null;

    for (Hotel hotel : hotels.getBody()) {
      assertThat(previousPrice == null || hotel.getPrice() > previousPrice);
      assertThat(hotel.getCity().equals(CITY));

      // Save reference to previous price
      previousPrice = hotel.getPrice();
    }
  }

  /**
   * Calls the "findAllHotelsByCity" method that should return a list of Hotel in {CITY} ordered by
   * price DESC.
   */
  @Test
  public void findByExistingCityDesc() {
    // Mockito configuration
    sameCityHotels.sort(Comparator.comparing(Hotel::getPrice).reversed());
    when(hotelService.findAllHotelsByCity(CITY, "DESC")).thenReturn(sameCityHotels);

    // Test (without Rate Limiter)
    HotelController hotelController = new HotelController(this.hotelService, null, null);
    ResponseEntity<List<Hotel>> hotels = hotelController.findAllHotelsByCity(CITY, "DESC");

    assertThat(hotels.getBody()).isNotNull();
    assertThat(hotels.getBody().size()).isEqualTo(2);

    // Every hotel should be based in {CITY}
    // The price should never decrease
    Integer previousPrice = null;

    for (Hotel hotel : hotels.getBody()) {
      assertThat(previousPrice == null || hotel.getPrice() < previousPrice);
      assertThat(hotel.getCity().equals(CITY));

      // Save reference to previous price
      previousPrice = hotel.getPrice();
    }
  }

  /**
   * Calls the "findAllHotelsByCity" method that should return an <b>empty</b> list of Hotel in
   * {NOT_AVAILABLE_CITY}.
   */
  @Test
  public void findByNonAvailableCity() {
    // Mockito configuration
    sameCityHotels.sort(Comparator.comparing(Hotel::getPrice));
    when(hotelService.findAllHotelsByCity(NOT_AVAILABLE_CITY, "ASC")).thenReturn(new ArrayList<>());

    // Test (without Rate Limiter)
    HotelController hotelController = new HotelController(this.hotelService, null, null);
    ResponseEntity<List<Hotel>> hotels =
        hotelController.findAllHotelsByCity(NOT_AVAILABLE_CITY, "ASC");

    assertThat(hotels.getBody()).isNotNull();
    assertThat(hotels.getBody().size()).isEqualTo(0);
  }

  // findAllHotelsByRoom

  /**
   * Calls the "findAllHotelsByRoom" method that should return an <b>empty</b> list of Hotel with
   * {ROOM} ordered by price ASC.
   */
  @Test
  public void findByExistingRoomAsc() {
    // Mockito configuration
    sameRoomHotels.sort(Comparator.comparing(Hotel::getPrice));
    when(hotelService.findAllHotelsByRoom(ROOM, "ASC")).thenReturn(sameRoomHotels);

    // Test (without Rate Limiter)
    HotelController hotelController = new HotelController(this.hotelService, null, null);
    ResponseEntity<List<Hotel>> hotels = hotelController.findAllHotelsByRoom(ROOM, "ASC");

    assertThat(hotels.getBody()).isNotNull();
    assertThat(hotels.getBody().size()).isEqualTo(2);

    // Every hotel should have the following {ROOM}
    // The price should never decrease
    Integer previousPrice = null;

    for (Hotel hotel : hotels.getBody()) {
      assertThat(previousPrice == null || hotel.getPrice() > previousPrice);
      assertThat(hotel.getRoom().equals(ROOM));

      // Save reference to previous price
      previousPrice = hotel.getPrice();
    }
  }

  /**
   * Calls the "findAllHotelsByRoom" method that should return an <b>empty</b> list of Hotel with
   * {ROOM} ordered by price DESC.
   */
  @Test
  public void findByExistingRoomDesc() {
    // Mockito configuration
    sameRoomHotels.sort(Comparator.comparing(Hotel::getPrice).reversed());
    when(hotelService.findAllHotelsByRoom(ROOM, "DESC")).thenReturn(sameRoomHotels);

    // Test (without Rate Limiter)
    HotelController hotelController = new HotelController(this.hotelService, null, null);
    ResponseEntity<List<Hotel>> hotels = hotelController.findAllHotelsByRoom(ROOM, "DESC");

    assertThat(hotels.getBody()).isNotNull();
    assertThat(hotels.getBody().size()).isEqualTo(2);

    // Every hotel should have the following {ROOM}
    // The price should never decrease
    Integer previousPrice = null;

    for (Hotel hotel : hotels.getBody()) {
      assertThat(previousPrice == null || hotel.getPrice() < previousPrice);
      assertThat(hotel.getRoom().equals(ROOM));

      // Save reference to previous price
      previousPrice = hotel.getPrice();
    }
  }

  /**
   * Calls the "findAllHotelsByRoom" method that should return an <b>empty</b> list of Hotel with
   * {NOT_AVAILABLE_ROOM}.
   */
  @Test
  public void findByNonAvailableRoom() {
    // Mockito configuration
    sameRoomHotels.sort(Comparator.comparing(Hotel::getPrice));
    when(hotelService.findAllHotelsByRoom(NOT_AVAILABLE_ROOM, "ASC")).thenReturn(new ArrayList<>());

    // Test (without Rate Limiter)
    HotelController hotelController = new HotelController(this.hotelService, null, null);
    ResponseEntity<List<Hotel>> hotels =
        hotelController.findAllHotelsByRoom(NOT_AVAILABLE_ROOM, "ASC");

    assertThat(hotels.getBody()).isNotNull();
    assertThat(hotels.getBody().size()).isEqualTo(0);
  }
}
