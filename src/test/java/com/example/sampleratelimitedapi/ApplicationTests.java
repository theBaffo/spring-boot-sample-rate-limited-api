package com.example.sampleratelimitedapi;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.sampleratelimitedapi.models.Hotel;
import com.example.sampleratelimitedapi.utils.RateLimiter;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

/**
 *
 *
 * <h1>ApplicationTests</h1>
 *
 * <p>This class contains the Integration Tests for the application
 *
 * <p>To test the result, we use real data. (seeded automatically from test/resources/data.sql)
 *
 * @author Marco Giuliani
 * @version 1.0
 * @since 2020-02-23
 */
@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationTests {
  private static final String CITY = "Bangkok";
  private static final String DIFFERENT_CITY = "Amsterdam";

  private static final String ROOM = "Deluxe";
  private static final String DIFFERENT_ROOM = "Superior";

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  private static List<Hotel> sameCityHotels;
  private static List<Hotel> sameRoomHotels;

  private void sleep(Long ms) {
    try {
      Thread.sleep(ms);
    } catch (InterruptedException e) {

    }
  }

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

  // GET /city/{city} (Rate Limiter)

  /**
   * If we call the GET /city/{CITY} method, we expect the API to return a JSON object containing
   * the list of Hotel in {CITY}, ordered by price ASC.
   * We also test that the rate limiter (max 10 requests in 5 seconds) works as expected.
   */
  @Test
  public void testGetHotelsByCityWithRateLimiter() throws Exception {
    // Order sameCityHotels by price ASC
    this.sameCityHotels.sort(Comparator.comparing(Hotel::getPrice));

    for (int i = 0; i < 10; i++) {
      // Perform HTTP request
      this.mockMvc
          .perform(get("/city/" + CITY + "?sortByPrice=ASC"))
          .andExpect(status().isOk())
          .andExpect(content().json(objectMapper.writeValueAsString(this.sameCityHotels)));
    }

    // This request should fail
    this.mockMvc
        .perform(get("/city/" + CITY + "?sortByPrice=ASC"))
        .andExpect(status().isTooManyRequests());

    // Wait blocking time
    sleep(RateLimiter.API_BLOCKED_TIME_MS);

    // This request should pass
    this.mockMvc
        .perform(get("/city/" + CITY + "?sortByPrice=ASC"))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(this.sameCityHotels)));
  }

  // GET /room/{room} (Rate Limiter)

  /**
   * If we call the GET /room/{ROOM} method, we expect the API to return a JSON object containing
   * the list of Hotel with {ROOM}, ordered by price DESC.
   * We also test that the rate limiter (max 100 requests in 10 seconds) works as expected.
   */
  @Test
  public void testGetHotelsByRoomWithRateLimiter() throws Exception {
    // Order sameRoomHotels by price DESC
    this.sameRoomHotels.sort(Comparator.comparing(Hotel::getPrice).reversed());

    for (int i = 0; i < 100; i++) {
      // Perform HTTP request
      this.mockMvc
          .perform(get("/room/" + ROOM + "?sortByPrice=DESC"))
          .andExpect(status().isOk())
          .andExpect(content().json(objectMapper.writeValueAsString(this.sameRoomHotels)));
    }

    // This request should fail
    this.mockMvc
        .perform(get("/room/" + ROOM + "?sortByPrice=DESC"))
        .andExpect(status().isTooManyRequests());

    // Wait blocking time
    sleep(RateLimiter.API_BLOCKED_TIME_MS);

    // This request should pass
    this.mockMvc
        .perform(get("/room/" + ROOM + "?sortByPrice=DESC"))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(this.sameRoomHotels)));
  }
}
