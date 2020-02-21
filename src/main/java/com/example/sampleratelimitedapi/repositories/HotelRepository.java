package com.example.sampleratelimitedapi.repositories;

import com.example.sampleratelimitedapi.models.Hotel;
import java.util.List;

/**
 *
 *
 * <h1>HotelRepository</h1>
 *
 * <p>The HotelRepository interface defines the method that our Repository class should implement.
 *
 * @author Marco Giuliani
 * @version 1.0
 * @since 2020-02-23
 */
public interface HotelRepository {
  List<Hotel> findAllHotelsByCity(String city, String sortByPrice);

  List<Hotel> findAllHotelsByRoom(String room, String sortByPrice);
}
