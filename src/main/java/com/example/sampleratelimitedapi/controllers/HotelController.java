package com.example.sampleratelimitedapi.controllers;

import com.example.sampleratelimitedapi.models.Hotel;
import com.example.sampleratelimitedapi.services.HotelService;
import com.example.sampleratelimitedapi.utils.RateLimiter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 *
 * <h1>HotelController</h1>
 *
 * <p>The HotelController expose the {@link HotelService} methods as API endpoints.
 *
 * <p>The current endpoints are:
 *
 * <ul>
 *   <li>GET /city/{city} - Returns a List of Hotel instances filtered by {city} (case-insensitive)
 *   <li>GET /room/{room} - Returns a List of Hotel instances filtered by {room} (case-insensitive)
 * </ul>
 *
 * @author Marco Giuliani
 * @version 1.0
 * @since 2020-02-23
 */
@Controller
public class HotelController {
  private HotelService hotelService;

  private RateLimiter cityRateLimiter;

  private RateLimiter roomRateLimiter;

  @Autowired
  public HotelController(HotelService hotelService) {
    this.hotelService = hotelService;
    this.cityRateLimiter = new RateLimiter(10L, 5 * 1000L);
    this.roomRateLimiter = new RateLimiter(100L, 10 * 1000L);
  }

  public HotelController(
      HotelService hotelService, RateLimiter cityRateLimiter, RateLimiter roomRateLimiter) {
    this.hotelService = hotelService;
    this.cityRateLimiter = cityRateLimiter;
    this.roomRateLimiter = roomRateLimiter;
  }

  /**
   * Returns a List of Hotel instances filtered by {city} (case-insensitive)
   *
   * @param city The city used to filter the result
   * @param sortByPrice can be set as "ASC" or "DESC" to sort the result by the Hotel price.
   * @return a List of Hotel instances filtered by room (case-insensitive)
   */
  @RequestMapping(value = "/city/{city}", method = RequestMethod.GET)
  public ResponseEntity<List<Hotel>> findAllHotelsByCity(
      @PathVariable("city") String city,
      @RequestParam(value = "sortByPrice", required = false) String sortByPrice) {
    if (cityRateLimiter != null && !cityRateLimiter.isAllowed())
      return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(new ArrayList<>());

    return ResponseEntity.status(HttpStatus.OK)
        .body(hotelService.findAllHotelsByCity(city, sortByPrice));
  }

  /**
   * Returns a List of Hotel instances filtered by {room} (case-insensitive)
   *
   * @param room The room used to filter the result
   * @param sortByPrice can be set as "ASC" or "DESC" to sort the result by the Hotel price.
   * @return a List of Hotel instances filtered by room (case-insensitive)
   */
  @RequestMapping(value = "/room/{room}", method = RequestMethod.GET)
  public ResponseEntity<List<Hotel>> findAllHotelsByRoom(
      @PathVariable("room") String room,
      @RequestParam(value = "sortByPrice", required = false) String sortByPrice) {
    if (roomRateLimiter != null && !roomRateLimiter.isAllowed())
      return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(new ArrayList<>());

    return ResponseEntity.status(HttpStatus.OK)
        .body(hotelService.findAllHotelsByRoom(room, sortByPrice));
  }
}
