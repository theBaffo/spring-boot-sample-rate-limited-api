package com.example.sampleratelimitedapi.services;

import com.example.sampleratelimitedapi.models.Hotel;
import java.util.List;

/**
 *
 *
 * <h1>HotelService</h1>
 *
 * <p>The HotelService interfaces defines the method that our Service class should implement.
 *
 * @author Marco Giuliani
 * @version 1.0
 * @since 2020-02-23
 */
public interface HotelService {
  List<Hotel> findAllHotelsByCity(String city, String sortByPrice);

  List<Hotel> findAllHotelsByRoom(String room, String sortByPrice);
}
