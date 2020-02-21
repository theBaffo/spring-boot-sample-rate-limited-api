package com.example.sampleratelimitedapi.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.example.sampleratelimitedapi.models.Hotel;
import com.example.sampleratelimitedapi.repositories.HotelJpaRepository;
import com.example.sampleratelimitedapi.repositories.HotelRepository;
import com.example.sampleratelimitedapi.repositories.HotelRepositoryImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 *
 *
 * <h1>HotelRepositoryTests</h1>
 *
 * <p>This class contains the Unit Tests for the HotelRepositoryImpl class (and HotelRepository
 * interface)
 *
 * <p>We use Mockito to generate a mocked instance based on the HotelJpaRepository interface
 *
 * <p>
 *
 * @author Marco Giuliani
 * @version 1.0
 * @since 2020-02-23
 */
@ExtendWith(MockitoExtension.class)
public class HotelRepositoryTests {
  private static final String CITY = "Bangkok";
  private static final String DIFFERENT_CITY = "Amsterdam";
  private static final String NOT_AVAILABLE_CITY = "Guadalupe";

  private static final String ROOM = "Deluxe";
  private static final String DIFFERENT_ROOM = "Superior";
  private static final String NOT_AVAILABLE_ROOM = "GigaDeluxe";

  @Mock private HotelJpaRepository hotelJpaRepository;

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
    when(hotelJpaRepository.findByCityIgnoreCase(CITY)).thenReturn(sameCityHotels);

    // Test
    HotelRepository hotelRepository = new HotelRepositoryImpl(this.hotelJpaRepository);
    List<Hotel> hotels = hotelRepository.findAllHotelsByCity(CITY, "ASC");

    assertThat(hotels).isNotNull();
    assertThat(hotels.size()).isEqualTo(2);

    // Every hotel should be based in {CITY}
    // The price should never decrease
    Integer previousPrice = null;

    for (Hotel hotel : hotels) {
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
    when(hotelJpaRepository.findByCityIgnoreCase(CITY)).thenReturn(sameCityHotels);

    // Test
    HotelRepository hotelRepository = new HotelRepositoryImpl(this.hotelJpaRepository);
    List<Hotel> hotels = hotelRepository.findAllHotelsByCity(CITY, "DESC");

    assertThat(hotels).isNotNull();
    assertThat(hotels.size()).isEqualTo(2);

    // Every hotel should be based in {CITY}
    // The price should never decrease
    Integer previousPrice = null;

    for (Hotel hotel : hotels) {
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
    when(hotelJpaRepository.findByCityIgnoreCase(NOT_AVAILABLE_CITY)).thenReturn(new ArrayList<>());

    // Test
    HotelRepository hotelRepository = new HotelRepositoryImpl(this.hotelJpaRepository);
    List<Hotel> hotels = hotelRepository.findAllHotelsByCity(NOT_AVAILABLE_CITY, "ASC");

    assertThat(hotels).isNotNull();
    assertThat(hotels.size()).isEqualTo(0);
  }

  // findAllHotelsByRoom

  /**
   * Calls the "findAllHotelsByRoom" method that should return a list of Hotel with {ROOM} ordered
   * by price ASC.
   */
  @Test
  public void findByExistingRoomAsc() {
    // Mockito configuration
    when(hotelJpaRepository.findByRoomIgnoreCase(ROOM)).thenReturn(sameRoomHotels);

    // Test
    HotelRepository hotelRepository = new HotelRepositoryImpl(this.hotelJpaRepository);
    List<Hotel> hotels = hotelRepository.findAllHotelsByRoom(ROOM, "ASC");

    assertThat(hotels).isNotNull();
    assertThat(hotels.size()).isEqualTo(2);

    // Every hotel should have the following {ROOM}
    // The price should never decrease
    Integer previousPrice = null;

    for (Hotel hotel : hotels) {
      assertThat(previousPrice == null || hotel.getPrice() > previousPrice);
      assertThat(hotel.getRoom().equals(ROOM));

      // Save reference to previous price
      previousPrice = hotel.getPrice();
    }
  }

  /**
   * Calls the "findAllHotelsByRoom" method that should return a list of Hotel with {ROOM} ordered
   * by price DESC.
   */
  @Test
  public void findByExistingRoomDesc() {
    // Mockito configuration
    when(hotelJpaRepository.findByRoomIgnoreCase(ROOM)).thenReturn(sameRoomHotels);

    // Test
    HotelRepository hotelRepository = new HotelRepositoryImpl(this.hotelJpaRepository);
    List<Hotel> hotels = hotelRepository.findAllHotelsByRoom(ROOM, "DESC");

    assertThat(hotels).isNotNull();
    assertThat(hotels.size()).isEqualTo(2);

    // Every hotel should have the following {ROOM}
    // The price should never decrease
    Integer previousPrice = null;

    for (Hotel hotel : hotels) {
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
    when(hotelJpaRepository.findByRoomIgnoreCase(NOT_AVAILABLE_ROOM)).thenReturn(new ArrayList<>());

    // Test
    HotelRepository hotelRepository = new HotelRepositoryImpl(this.hotelJpaRepository);
    List<Hotel> hotels = hotelRepository.findAllHotelsByRoom(NOT_AVAILABLE_ROOM, "ASC");

    assertThat(hotels).isNotNull();
    assertThat(hotels.size()).isEqualTo(0);
  }
}
