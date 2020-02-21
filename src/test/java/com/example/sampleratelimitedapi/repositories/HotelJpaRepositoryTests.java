package com.example.sampleratelimitedapi.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.sampleratelimitedapi.models.Hotel;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.repository.Repository;

/**
 *
 *
 * <h1>HotelJpaRepositoryTests</h1>
 *
 * <p>This class contains the Unit Tests for the HotelJpaRepository class. (and HotelRepository
 * interface)
 *
 * <p>Given that HotelJpaRepository is an interface that uses Spring {@link Repository} interface to
 * retrieve data we can only test the correct behaviour of the class using real data. (seeded
 * automatically from test/resources/data.sql)
 *
 * @author Marco Giuliani
 * @version 1.0
 * @since 2020-02-23
 */
@DataJpaTest
public class HotelJpaRepositoryTests {
  private static final String CITY = "Bangkok";
  private static final String DIFFERENT_CITY = "Amsterdam";
  private static final String NOT_AVAILABLE_CITY = "Guadalupe";

  private static final String ROOM = "Deluxe";
  private static final String DIFFERENT_ROOM = "Superior";
  private static final String NOT_AVAILABLE_ROOM = "GigaDeluxe";

  @Autowired
  HotelJpaRepository hotelJpaRepository;

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

  // findByCity

  /** Calls the "findByCityIgnoreCase" method that should return a list of Hotel in {CITY}. */
  @Test
  public void findByExistingCity() {
    // Test
    List<Hotel> hotels = hotelJpaRepository.findByCityIgnoreCase(CITY);

    assertThat(hotels).isNotNull();
    assertThat(hotels.size()).isEqualTo(2);

    // Every hotel should be based in {CITY}
    for (Hotel hotel : hotels) {
      assertThat(hotel.getCity().equals(CITY));
    }
  }

  /**
   * Calls the "findByCityIgnoreCase" method that should return an <b>empty</b> list of Hotel in
   * {NOT_AVAILABLE_CITY}.
   */
  @Test
  public void findByNonAvailableCity() {
    // Test
    List<Hotel> hotels = hotelJpaRepository.findByCityIgnoreCase(NOT_AVAILABLE_CITY);

    assertThat(hotels).isNotNull();
    assertThat(hotels.size()).isEqualTo(0);
  }

  // findByRoom

  /** Calls the "findByRoomIgnoreCase" method that should return a list of Hotel with {ROOM}. */
  @Test
  public void findByExistingRoom() {
    // Test
    List<Hotel> hotels = hotelJpaRepository.findByRoomIgnoreCase(ROOM);

    assertThat(hotels).isNotNull();
    assertThat(hotels.size()).isEqualTo(2);

    // Every hotel should have the following {ROOM}
    for (Hotel hotel : hotels) {
      assertThat(hotel.getRoom().equals(ROOM));
    }
  }

  /**
   * Calls the "findByRoomIgnoreCase" method that should return an <b>empty</b> list of Hotel with
   * {NOT_AVAILABLE_ROOM}.
   */
  @Test
  public void findByNonAvailableRoom() {
    // Test
    List<Hotel> hotels = hotelJpaRepository.findByRoomIgnoreCase(NOT_AVAILABLE_ROOM);

    assertThat(hotels).isNotNull();
    assertThat(hotels.size()).isEqualTo(0);
  }
}
